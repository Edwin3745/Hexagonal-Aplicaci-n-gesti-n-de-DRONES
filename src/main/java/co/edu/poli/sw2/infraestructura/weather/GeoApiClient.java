package co.edu.poli.sw2.infraestructura.weather;

import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;

/**
 * Cliente HTTP de la API de Geocoding de OpenWeatherMap; forma parte del
 * adaptador de salida {@link GeoApiAdapter}.
 *
 * <p>Hace la petición y devuelve el JSON sin interpretarlo; lo interpreta
 * {@link GeoApiAdapter}. Traduce los errores HTTP y de red a
 * {@link WeatherApiException}.</p>
 */
public class GeoApiClient extends AbstractOpenWeatherClient {
    private static final String URL = "https://api.openweathermap.org/geo/1.0/direct";
    private static final String SERVICIO = "Geocoding";

    /**
     * Crea el cliente con la clave que se enviará en cada petición.
     *
     * @param apiKey API key de OpenWeatherMap (la de {@code OPENWEATHER_API_KEY}).
     */
    public GeoApiClient(String apiKey) {
        super(apiKey, SERVICIO);
    }

    /**
     * Crea el cliente con un {@link HttpClient} dado (visible para pruebas).
     *
     * @param apiKey API key de OpenWeatherMap.
     * @param http   cliente HTTP a usar.
     */
    GeoApiClient(String apiKey, HttpClient http) {
        super(apiKey, SERVICIO, http);
    }

    /**
     * Consulta las coordenadas de una ciudad.
     *
     * @param city nombre de la ciudad.
     * @return JSON con un arreglo de coincidencias; vacío si la ciudad no existe.
     * @throws WeatherApiException si la clave es inválida (HTTP 401), si la API
     *                             responde con otro código distinto de 200 o si
     *                             no hay conexión.
     */
    public String fetch(String city) {
        URI uri = URI.create(URL
                + "?q=" + URLEncoder.encode(city, StandardCharsets.UTF_8)
                + "&limit=1"
                + "&appid=" + apiKey);
        return send(uri);
    }
}