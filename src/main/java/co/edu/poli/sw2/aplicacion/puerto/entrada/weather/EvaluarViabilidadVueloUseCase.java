package co.edu.poli.sw2.aplicacion.puerto.entrada.weather;

import co.edu.poli.sw2.dominio.chain.ResultadoViabilidad;
import co.edu.poli.sw2.dominio.weather.Weather;

/**
 * Puerto de entrada: caso de uso de evaluar si el clima permite operar drones.
 */
public interface EvaluarViabilidadVueloUseCase {

    /**
     * Evalúa un clima ya obtenido contra las reglas de viabilidad de vuelo.
     *
     * @param weather clima a evaluar; no puede ser nulo.
     * @return resultado con el veredicto (apto o no apto) y, si no es apto,
     *         la regla incumplida y su motivo.
     * @throws IllegalArgumentException si el clima es nulo.
     */
    ResultadoViabilidad evaluar(Weather weather);
}