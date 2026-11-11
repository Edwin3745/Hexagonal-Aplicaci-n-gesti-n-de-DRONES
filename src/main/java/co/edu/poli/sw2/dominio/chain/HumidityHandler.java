package co.edu.poli.sw2.dominio.chain;

import co.edu.poli.sw2.dominio.weather.Weather;
import java.util.Optional;

/**
 * ConcreteHandler del patrón Chain of Responsibility que valida la humedad
 * relativa.
 *
 * <p>Aprueba si la humedad no supera {@link #MAXIMA}. Es el segundo eslabón
 * de la cadena que arma {@link CadenaViabilidad}.</p>
 */
public class HumidityHandler extends WeatherHandler {

    /** Humedad relativa máxima permitida, en % (incluida). */
    public static final double MAXIMA = 85.0;

    /**
     * Verifica que la humedad no supere el máximo permitido.
     * Un valor no numérico ({@code NaN}) también se rechaza.
     *
     * @param weather condiciones climáticas a evaluar.
     * @return el motivo del rechazo, o vacío si la humedad es aceptable.
     */
    @Override
    protected Optional<String> evaluar(Weather weather) {
        double h = weather.getHumidity();
        if (!(h <= MAXIMA)) {
            return Optional.of("La humedad (" + h + " %) supera el máximo permitido de " + MAXIMA + " %.");
        }
        return Optional.empty();
    }

    /**
     * Nombre de la regla.
     *
     * @return {@code "Humedad"}.
     */
    @Override
    protected String nombreRegla() {
        return "Humedad";
    }
}