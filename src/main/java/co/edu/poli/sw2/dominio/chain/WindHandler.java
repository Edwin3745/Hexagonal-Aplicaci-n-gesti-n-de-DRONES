package co.edu.poli.sw2.dominio.chain;

import co.edu.poli.sw2.dominio.weather.Weather;
import java.util.Optional;

/**
 * ConcreteHandler del patrón Chain of Responsibility que valida la velocidad
 * del viento.
 *
 * <p>Aprueba si la velocidad del viento no supera {@link #MAXIMA}. Es el
 * tercer eslabón de la cadena que arma {@link CadenaViabilidad}.</p>
 */
public class WindHandler extends WeatherHandler {

    /** Velocidad máxima del viento permitida, en km/h (incluida). */
    public static final double MAXIMA = 40.0;

    /**
     * Verifica que la velocidad del viento no supere el máximo permitido.
     * Un valor no numérico ({@code NaN}) también se rechaza.
     *
     * @param weather condiciones climáticas a evaluar.
     * @return el motivo del rechazo, o vacío si el viento es aceptable.
     */
    @Override
    protected Optional<String> evaluar(Weather weather) {
        double v = weather.getWindSpeed();
        if (!(v <= MAXIMA)) {
            return Optional.of("La velocidad del viento (" + v + " km/h) supera el máximo permitido de "
                    + MAXIMA + " km/h.");
        }
        return Optional.empty();
    }

    /**
     * Nombre de la regla.
     *
     * @return {@code "Velocidad del viento"}.
     */
    @Override
    protected String nombreRegla() {
        return "Velocidad del viento";
    }
}