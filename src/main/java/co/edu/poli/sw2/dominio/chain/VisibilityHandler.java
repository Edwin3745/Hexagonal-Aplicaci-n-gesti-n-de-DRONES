package co.edu.poli.sw2.dominio.chain;

import co.edu.poli.sw2.dominio.weather.Weather;
import java.util.Optional;

/**
 * ConcreteHandler del patrón Chain of Responsibility que valida la
 * visibilidad.
 *
 * <p>Aprueba si la visibilidad es al menos {@link #MINIMA}. Es el último
 * eslabón de la cadena que arma {@link CadenaViabilidad}; si también aprueba,
 * el clima se considera apto.</p>
 */
public class VisibilityHandler extends WeatherHandler {

    /** Visibilidad mínima requerida, en km (incluida). */
    public static final double MINIMA = 5.0;

    /**
     * Verifica que la visibilidad alcance el mínimo requerido.
     * Un valor no numérico ({@code NaN}) también se rechaza.
     *
     * @param weather condiciones climáticas a evaluar.
     * @return el motivo del rechazo, o vacío si la visibilidad es suficiente.
     */
    @Override
    protected Optional<String> evaluar(Weather weather) {
        double v = weather.getVisibility();
        if (!(v >= MINIMA)) {
            return Optional.of("La visibilidad (" + v + " km) es inferior al mínimo requerido de "
                    + MINIMA + " km.");
        }
        return Optional.empty();
    }

    /**
     * Nombre de la regla.
     *
     * @return {@code "Visibilidad"}.
     */
    @Override
    protected String nombreRegla() {
        return "Visibilidad";
    }
}