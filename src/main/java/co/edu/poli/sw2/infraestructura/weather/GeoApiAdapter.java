package co.edu.poli.sw2.infraestructura.weather;

import co.edu.poli.sw2.aplicacion.puerto.salida.weather.GeoLocationPort;
import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException;
import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException.Reason;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Adaptador de salida que implementa {@link GeoLocationPort} con la API de
 * Geocoding de OpenWeatherMap.
 */
public class GeoApiAdapter implements GeoLocationPort {

    private final GeoApiClient client;

    /**
     * @param client cliente de la API de Geocoding.
     */
    public GeoApiAdapter(GeoApiClient client) {
        this.client = client;
    }

    /** {@inheritDoc} */
    @Override
    public double[] findCoordinates(String city) {
        try {
            JSONArray resultados = new JSONArray(client.fetch(city));
            if (resultados.isEmpty()) {
                return null;
            }
            JSONObject ciudad = resultados.getJSONObject(0);
            return new double[]{ciudad.getDouble("lat"), ciudad.getDouble("lon")};
        } catch (JSONException e) {
            throw new WeatherApiException(Reason.INVALID_RESPONSE, "Respuesta de Geocoding no válida", e);
        }
    }
}
