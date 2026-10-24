package co.edu.poli.sw2.infraestructura.weather;

import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException;
import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException.Reason;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Cliente de la API de Geocoding de OpenWeatherMap.
 *
 * <p>Hace la petición y devuelve el JSON sin interpretarlo; lo interpreta
 * {@link GeoApiAdapter}.</p>
 */
public class GeoApiClient {

    private static final String URL = "https://api.openweathermap.org/geo/1.0/direct";

    private final HttpClient http = HttpClient.newHttpClient();
    private final String apiKey;

    /**
     * @param apiKey API key de OpenWeatherMap.
     */
    public GeoApiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Consulta las coordenadas de una ciudad.
     *
     * @param city nombre de la ciudad.
     * @return JSON con un arreglo de coincidencias; vacío si la ciudad no existe.
     * @throws WeatherApiException si la clave es inválida o no hay conexión.
     */
    public String fetch(String city) {
        URI uri = URI.create(URL
                + "?q=" + URLEncoder.encode(city, StandardCharsets.UTF_8)
                + "&limit=1"
                + "&appid=" + apiKey);
        try {
            HttpResponse<String> respuesta = http.send(
                    HttpRequest.newBuilder(uri).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            if (respuesta.statusCode() == 401) {
                throw new WeatherApiException(Reason.INVALID_API_KEY, "Geocoding respondió 401");
            }
            if (respuesta.statusCode() != 200) {
                throw new WeatherApiException(Reason.UNAVAILABLE, "Geocoding respondió " + respuesta.statusCode());
            }
            return respuesta.body();
        } catch (IOException | InterruptedException e) {
            throw new WeatherApiException(Reason.UNAVAILABLE, "Sin conexión con Geocoding", e);
        }
    }
}
