package co.edu.poli.sw2.dominio.chain;

import co.edu.poli.sw2.dominio.weather.Weather;
import java.util.Optional;

/**
 * ConcreteHandler del patrón Chain of Responsibility que valida la temperatura.
 *
 * <p>Aprueba si la temperatura está entre {@link #MINIMA} y {@link #MAXIMA}
 * (ambos incluidos). Es el primer eslabón de la cadena que arma
 * {@link CadenaViabilidad}.</p>
 */
public class TemperatureHandler extends WeatherHandler {

    /** Temperatura mínima permitida, en °C (incluida). */
    public static final double MINIMA = 0.0;
    /** Temperatura máxima permitida, en °C (incluida). */
    public static final double MAXIMA = 40.0;

    /**
     * Verifica que la temperatura esté dentro del rango permitido.
     * Un valor no numérico ({@code NaN}) también se rechaza.
     *
     * @param weather condiciones climáticas a evaluar.
     * @return el motivo del rechazo, o vacío si la temperatura está en rango.
     */
    @Override
    protected Optional<String> evaluar(Weather weather) {
        double t = weather.getTemperature();
        if (!(t >= MINIMA && t <= MAXIMA)) {
            return Optional.of("La temperatura (" + t + " °C) está fuera del rango permitido de "
                    + MINIMA + " °C a " + MAXIMA + " °C.");
        }
        return Optional.empty();
    }

    /**
     * Nombre de la regla.
     *
     * @return {@code "Temperatura"}.
     */
    @Override
    protected String nombreRegla() {
        return "Temperatura";
    }
}