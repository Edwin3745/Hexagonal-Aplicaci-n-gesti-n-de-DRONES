package co.edu.poli.sw2.aplicacion.observer;

import co.edu.poli.sw2.dominio.weather.Weather;
import co.edu.poli.sw2.dominio.weather.WeatherObserver;

import java.util.function.Consumer;

/**
 * Observador que lleva cada clima recibido al tablero de la interfaz.
 *
 * <p>Está en la capa de aplicación y no puede conocer JavaFX; por eso recibe
 * un {@link Consumer} que le entrega la interfaz y le pasa el clima.</p>
 */
public class DashboardObserver implements WeatherObserver {

    /** Acción de la interfaz que muestra el clima. */
    private final Consumer<Weather> pantalla;

    /**
     * @param pantalla acción que muestra el clima en la interfaz.
     */
    public DashboardObserver(Consumer<Weather> pantalla) {
        this.pantalla = pantalla;
    }

    /** {@inheritDoc} */
    @Override
    public void update(Weather weather) {
        pantalla.accept(weather);
    }
}