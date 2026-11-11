package co.edu.poli.sw2.dominio.chain;

import co.edu.poli.sw2.dominio.weather.Weather;
import java.util.Optional;

/**
 * ConcreteHandler del patrón Chain of Responsibility que valida la presión
 * atmosférica.
 *
 * <p>Aprueba si la presión está entre {@link #MINIMA} y {@link #MAXIMA}
 * (ambos incluidos). Es el cuarto eslabón de la cadena que arma
 * {@link CadenaViabilidad}.</p>
 */
public class PressureHandler extends WeatherHandler {

    /** Presión atmosférica mínima permitida, en hPa (incluida). */
    public static final double MINIMA = 950.0;
    /** Presión atmosférica máxima permitida, en hPa (incluida). */
    public static final double MAXIMA = 1050.0;

    /**
     * Verifica que la presión atmosférica esté dentro del rango permitido.
     * Un valor no numérico ({@code NaN}) también se rechaza.
     *
     * @param weather condiciones climáticas a evaluar.
     * @return el motivo del rechazo, o vacío si la presión está en rango.
     */
    @Override
    protected Optional<String> evaluar(Weather weather) {
        double p = weather.getPressure();
        if (!(p >= MINIMA && p <= MAXIMA)) {
            return Optional.of("La presión atmosférica (" + p + " hPa) está fuera del rango permitido de "
                    + MINIMA + " hPa a " + MAXIMA + " hPa.");
        }
        return Optional.empty();
    }

    /**
     * Nombre de la regla.
     *
     * @return {@code "Presión atmosférica"}.
     */
    @Override
    protected String nombreRegla() {
        return "Presión atmosférica";
    }
}