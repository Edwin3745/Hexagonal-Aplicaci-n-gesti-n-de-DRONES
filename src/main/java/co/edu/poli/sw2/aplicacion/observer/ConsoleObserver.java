package co.edu.poli.sw2.aplicacion.observer;

import co.edu.poli.sw2.dominio.weather.Weather;
import co.edu.poli.sw2.dominio.weather.WeatherObserver;

/**
 * ConcreteObserver del patrón Observer: imprime en la consola cada clima
 * recibido.
 *
 * <p>Se suscribe en
 * {@link co.edu.poli.sw2.aplicacion.servicio.weather.WeatherNotificationService},
 * que le notifica cada clima consultado.</p>
 */
public class ConsoleObserver implements WeatherObserver {

    /**
     * {@inheritDoc}
     *
     * <p>Imprime el clima en la salida estándar con el prefijo
     * {@code [CLIMA]}.</p>
     */
    @Override
    public void update(Weather weather) {
        System.out.println("[CLIMA] " + weather);
    }
}