package co.edu.poli.sw2.infraestructura.weather;

import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException;
import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException.Reason;
import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherPort;
import co.edu.poli.sw2.dominio.weather.Weather;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Adaptador de salida que implementa {@link WeatherPort} con la API de clima
 * actual de OpenWeatherMap.
 *
 * <p>Toma los cinco aspectos del JSON y los convierte a las unidades del
 * dominio: el viento llega en m/s y se pasa a km/h; la visibilidad llega en
 * metros y se pasa a km. La presión se toma de {@code main.pressure}.</p>
 */
public class OpenWeatherAdapter implements WeatherPort {

    private final OpenWeatherClient client;

    /**
     * @param client cliente de la API de clima actual.
     */
    public OpenWeatherAdapter(OpenWeatherClient client) {
        this.client = client;
    }

    /** {@inheritDoc} */
    @Override
    public Weather getWeather(String city, double latitude, double longitude) {
        try {
            JSONObject json = new JSONObject(client.fetch(latitude, longitude));
            JSONObject main = json.getJSONObject("main");
            return new Weather(
                    city,
                    main.getDouble("temp"),
                    main.getDouble("humidity"),
                    json.getJSONObject("wind").getDouble("speed") * 3.6,
                    main.getDouble("pressure"),
                    json.getDouble("visibility") / 1000.0);
        } catch (JSONException e) {
            throw new WeatherApiException(Reason.INVALID_RESPONSE, "Respuesta del clima no válida", e);
        }
    }
}
