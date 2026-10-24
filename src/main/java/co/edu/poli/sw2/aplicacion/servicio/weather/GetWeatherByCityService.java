package co.edu.poli.sw2.aplicacion.servicio.weather;

import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.entrada.weather.GetWeatherByCityUseCase;
import co.edu.poli.sw2.aplicacion.puerto.salida.weather.GeoLocationPort;
import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherApiException;
import co.edu.poli.sw2.aplicacion.puerto.salida.weather.WeatherPort;
import co.edu.poli.sw2.dominio.weather.Weather;

/**
 * Implementa la consulta del clima por ciudad: obtiene las coordenadas,
 * consulta el clima y lo notifica a los observadores suscritos.
 */
public class GetWeatherByCityService implements GetWeatherByCityUseCase {

    private final GeoLocationPort geoLocation;
    private final WeatherPort weatherPort;
    private final WeatherNotificationService notifier;

    /**
     * @param geoLocation puerto que ubica la ciudad.
     * @param weatherPort puerto que entrega el clima.
     * @param notifier    notificador de los observadores.
     */
    public GetWeatherByCityService(GeoLocationPort geoLocation, WeatherPort weatherPort,
                                   WeatherNotificationService notifier) {
        this.geoLocation = geoLocation;
        this.weatherPort = weatherPort;
        this.notifier = notifier;
    }

    /** {@inheritDoc} */
    @Override
    public Weather execute(String city) {
        try {
            double[] coordenadas = geoLocation.findCoordinates(city);
            if (coordenadas == null) {
                throw new OperacionFallidaException("No se encontró la ciudad \"" + city + "\".");
            }
            Weather weather = weatherPort.getWeather(city, coordenadas[0], coordenadas[1]);
            notifier.notifyObservers(weather);
            return weather;
        } catch (WeatherApiException e) {
            throw new OperacionFallidaException(mensaje(e), e);
        }
    }

    /**
     * @param e fallo comunicado por un adaptador.
     * @return mensaje para el usuario según el motivo del fallo.
     */
    private static String mensaje(WeatherApiException e) {
        return switch (e.getReason()) {
            case INVALID_API_KEY -> "La API del clima rechazó la clave. Revisa OPENWEATHER_API_KEY en el archivo .env.";
            case UNAVAILABLE -> "No se pudo conectar con el servicio del clima. Verifica tu conexión a internet.";
            case INVALID_RESPONSE -> "El servicio del clima respondió con datos que no se pudieron leer.";
        };
    }
}