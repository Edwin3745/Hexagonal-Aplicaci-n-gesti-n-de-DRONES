package co.edu.poli.sw2.aplicacion.puerto.salida.weather;

import co.edu.poli.sw2.dominio.weather.Weather;

/**
 * Puerto de salida: obtener el clima actual en unas coordenadas.
 *
 * <p>Lo implementa el adaptador de infraestructura que consulta la API del
 * clima. Ese adaptador es el responsable de entregar el {@link Weather} ya
 * convertido a las unidades del dominio (°C, %, km/h, hPa, km).</p>
 */
public interface WeatherPort {

    /**
     * Consulta el clima actual.
     *
     * @param city      nombre de la ciudad, para identificar el resultado.
     * @param latitude  latitud en grados.
     * @param longitude longitud en grados.
     * @return el clima en las unidades del dominio.
     * @throws WeatherApiException si el servicio externo no puede responder.
     */
    Weather getWeather(String city, double latitude, double longitude);
}
