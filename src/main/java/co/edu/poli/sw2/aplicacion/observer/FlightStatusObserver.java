package co.edu.poli.sw2.aplicacion.observer;

import co.edu.poli.sw2.aplicacion.puerto.entrada.weather.EvaluarViabilidadVueloUseCase;
import co.edu.poli.sw2.dominio.chain.ResultadoViabilidad;
import co.edu.poli.sw2.dominio.modelo.Dron;
import co.edu.poli.sw2.dominio.weather.Weather;
import co.edu.poli.sw2.dominio.weather.WeatherObserver;

import java.util.function.Consumer;

/**
 * Observador que representa a un dron suscrito al notificador del clima.
 *
 * <p>La clase {@link Dron} no se modifica: este observador la envuelve. Al
 * recibir el clima lo evalúa con la cadena de responsabilidad (a través del
 * caso de uso {@link EvaluarViabilidadVueloUseCase}) e informa si ese dron
 * puede operar.</p>
 */
public class FlightStatusObserver implements WeatherObserver {

    private final Dron dron;
    private final EvaluarViabilidadVueloUseCase evaluador;
    private final Consumer<String> salida;

    /**
     * @param dron      dron que se suscribe.
     * @param evaluador caso de uso que evalúa el clima con la cadena.
     * @param salida    acción de la interfaz que muestra el resultado.
     */
    public FlightStatusObserver(Dron dron, EvaluarViabilidadVueloUseCase evaluador,
                                Consumer<String> salida) {
        this.dron = dron;
        this.evaluador = evaluador;
        this.salida = salida;
    }

    /** {@inheritDoc} */
    @Override
    public void update(Weather weather) {
        ResultadoViabilidad resultado = evaluador.evaluar(weather);
        salida.accept("Dron " + dron.getSerial() + " → " + resultado.getMensaje());
    }
}
