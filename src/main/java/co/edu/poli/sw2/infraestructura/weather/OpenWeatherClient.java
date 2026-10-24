package co.edu.poli.sw2.infraestructura.weather;

import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException;
import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException.Reason;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Cliente de la API de clima actual de OpenWeatherMap.
 *
 * <p>Hace la petición con {@code units=metric} (temperatura en °C) y devuelve
 * el JSON sin interpretarlo; lo interpreta {@link OpenWeatherAdapter}.</p>
 */
public class OpenWeatherClient {

    private static final String URL = "https://api.openweathermap.org/data/2.5/weather";

    private final HttpClient http = HttpClient.newHttpClient();
    private final String apiKey;

    /**
     * @param apiKey API key de OpenWeatherMap.
     */
    public OpenWeatherClient(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Consulta el clima actual en unas coordenadas.
     *
     * @param lat latitud.
     * @param lon longitud.
     * @return JSON con el clima actual.
     * @throws WeatherApiException si la clave es inválida o no hay conexión.
     */
    public String fetch(double lat, double lon) {
        // String.valueOf usa siempre punto decimal; con la configuración de
        // Colombia otros formatos escribirían 4,6 y la API rechazaría la petición.
        URI uri = URI.create(URL
                + "?lat=" + String.valueOf(lat)
                + "&lon=" + String.valueOf(lon)
                + "&units=metric"
                + "&appid=" + apiKey);
        try {
            HttpResponse<String> respuesta = http.send(
                    HttpRequest.newBuilder(uri).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            if (respuesta.statusCode() == 401) {
                throw new WeatherApiException(Reason.INVALID_API_KEY, "Weather respondió 401");
            }
            if (respuesta.statusCode() != 200) {
                throw new WeatherApiException(Reason.UNAVAILABLE, "Weather respondió " + respuesta.statusCode());
            }
            return respuesta.body();
        } catch (IOException | InterruptedException e) {
            throw new WeatherApiException(Reason.UNAVAILABLE, "Sin conexión con Weather", e);
        }
    }
}
