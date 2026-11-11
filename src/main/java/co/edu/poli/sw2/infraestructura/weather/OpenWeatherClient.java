package co.edu.poli.sw2.infraestructura.weather;

import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException;

import java.net.URI;
import java.net.http.HttpClient;

/**
 * Cliente HTTP de la API de clima actual de OpenWeatherMap; forma parte del
 * adaptador de salida {@link OpenWeatherAdapter}.
 *
 * <p>Hace la petición con {@code units=metric} (temperatura en °C) y devuelve
 * el JSON sin interpretarlo; lo interpreta {@link OpenWeatherAdapter}. Traduce
 * los errores HTTP y de red a {@link WeatherApiException}.</p>
 */
public class OpenWeatherClient extends AbstractOpenWeatherClient {
    private static final String URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String SERVICIO = "Weather";

    /**
     * Crea el cliente con la clave que se enviará en cada petición.
     *
     * @param apiKey API key de OpenWeatherMap (la de {@code OPENWEATHER_API_KEY}).
     */
    public OpenWeatherClient(String apiKey) {
        super(apiKey, SERVICIO);
    }

    /**
     * Crea el cliente con un {@link HttpClient} dado (visible para pruebas).
     *
     * @param apiKey API key de OpenWeatherMap.
     * @param http   cliente HTTP a usar.
     */
    OpenWeatherClient(String apiKey, HttpClient http) {
        super(apiKey, SERVICIO, http);
    }

    /**
     * Consulta el clima actual en unas coordenadas.
     *
     * @param lat latitud.
     * @param lon longitud.
     * @return JSON con el clima actual.
     * @throws WeatherApiException si la clave es inválida (HTTP 401), si la API
     *                             responde con otro código distinto de 200 o si
     *                             no hay conexión.
     */
    public String fetch(double lat, double lon) {
        // String.valueOf usa siempre punto decimal; con la configuración de
        // Colombia otros formatos escribirían 4,6 y la API rechazaría la petición.
        URI uri = URI.create(URL
                + "?lat=" + String.valueOf(lat)
                + "&lon=" + String.valueOf(lon)
                + "&units=metric"
                + "&appid=" + apiKey);
        return send(uri);
    }
}