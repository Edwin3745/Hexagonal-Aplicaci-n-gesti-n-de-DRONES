package co.edu.poli.sw2.aplicacion.observer;

import co.edu.poli.sw2.dominio.weather.Weather;
import co.edu.poli.sw2.dominio.weather.WeatherObserver;

import java.util.function.Consumer;

/**
 * Observador que lleva cada clima recibido al tablero de la interfaz.
 *
 * <p>Vive en la capa de aplicación, así que no puede conocer JavaFX, que es
 * infraestructura. Por eso no pinta nada por sí mismo: recibe en el
 * constructor un {@link Consumer} que la interfaz le entrega al arrancar y,
 * cada vez que llega un clima, se lo pasa. La interfaz decide cómo mostrarlo;
 * el observador solo garantiza que le llegue.</p>
 *
 * <p>Además recuerda el último clima recibido, para que la interfaz pueda
 * volver a mostrarlo sin consultar de nuevo la API.</p>
 */
public class DashboardObserver implements WeatherObserver {

    /** Destino al que se entrega cada clima; lo provee la interfaz. */
    private final Consumer<Weather> pantalla;

    /** Último clima recibido, o {@code null} si aún no ha llegado ninguno. */
    private Weather ultimoClima;

    /**
     * Crea el observador conectado a un destino de visualización.
     *
     * @param pantalla acción que muestra el clima; no puede ser nula.
     * @throws IllegalArgumentException si la pantalla es nula.
     */
    public DashboardObserver(Consumer<Weather> pantalla) {
        if (pantalla == null) {
            throw new IllegalArgumentException("El destino del tablero no puede ser nulo.");
        }
        this.pantalla = pantalla;
    }

    /**
     * Guarda el clima y lo entrega a la pantalla.
     *
     * @param weather clima recién consultado.
     */
    @Override
    public void update(Weather weather) {
        this.ultimoClima = weather;
        pantalla.accept(weather);
    }

    /**
     * @return el último clima recibido, o {@code null} si aún no ha llegado ninguno.
     */
    public Weather getUltimoClima() {
        return ultimoClima;
    }
}
