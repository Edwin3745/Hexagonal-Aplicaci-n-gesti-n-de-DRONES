package co.edu.poli.sw2.aplicacion.observer;

import co.edu.poli.sw2.dominio.weather.Weather;
import co.edu.poli.sw2.dominio.weather.WeatherObserver;

/**
 * Observador que imprime cada clima recibido en la consola.
 */
public class ConsoleObserver implements WeatherObserver {

    /** {@inheritDoc} */
    @Override
    public void update(Weather weather) {
        System.out.println("[CLIMA] " + weather);
    }
}