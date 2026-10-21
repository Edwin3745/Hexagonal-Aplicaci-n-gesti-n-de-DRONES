package co.edu.poli.sw2.aplicacion.observer;

import co.edu.poli.sw2.dominio.weather.Weather;
import co.edu.poli.sw2.dominio.weather.WeatherObserver;

import java.io.PrintStream;

/**
 * Observador que imprime cada clima recibido en la consola.
 *
 * <p>Sirve para seguir las notificaciones mientras la aplicación se ejecuta
 * desde la terminal ({@code mvn javafx:run}). El flujo de salida se recibe en
 * el constructor para poder verificarlo en las pruebas; en la aplicación se
 * usa {@code System.out}.</p>
 */
public class ConsoleObserver implements WeatherObserver {

    /** Flujo donde se imprime. */
    private final PrintStream salida;

    /**
     * Crea el observador imprimiendo en {@code System.out}.
     */
    public ConsoleObserver() {
        this(System.out);
    }

    /**
     * Crea el observador imprimiendo en el flujo indicado.
     *
     * @param salida flujo de salida; no puede ser nulo.
     * @throws IllegalArgumentException si el flujo es nulo.
     */
    public ConsoleObserver(PrintStream salida) {
        if (salida == null) {
            throw new IllegalArgumentException("El flujo de salida no puede ser nulo.");
        }
        this.salida = salida;
    }

    /**
     * Imprime una línea con la ciudad y los cinco aspectos del clima.
     *
     * @param weather clima recién consultado.
     */
    @Override
    public void update(Weather weather) {
        salida.println("[CLIMA] " + weather);
    }
}
