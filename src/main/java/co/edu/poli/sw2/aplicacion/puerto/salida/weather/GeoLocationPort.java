package co.edu.poli.sw2.aplicacion.puerto.salida.weather;

/**
 * Puerto de salida: convertir el nombre de una ciudad en coordenadas.
 *
 * <p>Lo define la aplicación y lo implementa un adaptador de
 * infraestructura (el que consulta la API de Geocoding). La aplicación no
 * sabe qué servicio hay detrás; solo que recibe latitud y longitud.</p>
 */
public interface GeoLocationPort {

    /**
     * Busca las coordenadas de una ciudad.
     *
     * @param city nombre de la ciudad.
     * @return arreglo de dos posiciones {@code {latitud, longitud}}, o
     *         {@code null} si la ciudad no existe.
     * @throws WeatherApiException si el servicio externo no puede responder.
     */
    double[] findCoordinates(String city);
}
