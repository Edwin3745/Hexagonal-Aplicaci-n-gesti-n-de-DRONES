package co.edu.poli.sw2.infraestructura.weather;

import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException;
import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException.Reason;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Base de los clientes HTTP de OpenWeatherMap: concentra el envío de la
 * petición y la traducción de errores HTTP y de red a
 * {@link WeatherApiException}.
 */
abstract class AbstractOpenWeatherClient {

    private final HttpClient http;
    protected final String apiKey;
    private final String servicio;

    /**
     * @param apiKey   API key de OpenWeatherMap.
     * @param servicio nombre del servicio, usado en los mensajes de error.
     */
    protected AbstractOpenWeatherClient(String apiKey, String servicio) {
        this(apiKey, servicio, HttpClient.newHttpClient());
    }

    /**
     * Constructor que permite inyectar el {@link HttpClient} (útil en pruebas).
     *
     * @param apiKey   API key de OpenWeatherMap.
     * @param servicio nombre del servicio, usado en los mensajes de error.
     * @param http     cliente HTTP con el que se envían las peticiones.
     */
    protected AbstractOpenWeatherClient(String apiKey, String servicio, HttpClient http) {
        this.apiKey = apiKey;
        this.servicio = servicio;
        this.http = http;
    }

    /**
     * Envía un GET a la URI y devuelve el cuerpo si la respuesta es 200.
     *
     * @param uri dirección completa de la petición.
     * @return cuerpo de la respuesta.
     * @throws WeatherApiException si la clave es inválida (HTTP 401), si la API
     *                             responde con otro código distinto de 200 o si
     *                             no hay conexión.
     */
    protected String send(URI uri) {
        try {
            HttpResponse<String> respuesta = http.send(
                    HttpRequest.newBuilder(uri).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            int codigo = respuesta.statusCode();
            if (codigo == 401) {
                throw new WeatherApiException(Reason.INVALID_API_KEY, servicio + " respondió 401");
            }
            if (codigo != 200) {
                throw new WeatherApiException(Reason.UNAVAILABLE, servicio + " respondió " + codigo);
            }
            return respuesta.body();
        } catch (InterruptedException e) {
            // Se restaura la marca de interrupción para no ocultarla al resto del programa.
            Thread.currentThread().interrupt();
            throw sinConexion(e);
        } catch (IOException e) {
            throw sinConexion(e);
        }
    }

    private WeatherApiException sinConexion(Exception causa) {
        return new WeatherApiException(Reason.UNAVAILABLE, "Sin conexión con " + servicio, causa);
    }
}