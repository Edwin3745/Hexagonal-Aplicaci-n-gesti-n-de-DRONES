package co.edu.poli.sw2.dominio.chain;

import co.edu.poli.sw2.dominio.weather.Weather;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Handler abstracto del patrón Chain of Responsibility para decidir si el
 * clima permite operar drones.
 *
 * <p>Cada eslabón evalúa una sola regla climática sobre un {@link Weather}.
 * Si la regla se incumple, la cadena se corta y se devuelve un resultado
 * {@linkplain ResultadoViabilidad#noApto(String, String) no apto}; si se
 * cumple, la petición pasa al siguiente eslabón. Cuando el último eslabón
 * aprueba, el resultado es {@linkplain ResultadoViabilidad#apto() apto}.</p>
 *
 * <p>Las subclases (ConcreteHandler) solo implementan
 * {@link #evaluar(Weather)} y {@link #nombreRegla()}; el recorrido de la
 * cadena lo controla {@link #handle(Weather)}, que es {@code final}.</p>
 *
 * @see CadenaViabilidad
 */
public abstract class WeatherHandler {

    private static final Logger LOG = Logger.getLogger(WeatherHandler.class.getName());

    private WeatherHandler siguiente;

    /**
     * Enlaza el siguiente eslabón de la cadena.
     *
     * <p>Devuelve el handler recibido (no {@code this}) para poder encadenar
     * llamadas: {@code a.setNext(b).setNext(c)} deja la cadena
     * {@code a -> b -> c}.</p>
     *
     * @param siguiente handler que recibirá la petición si este eslabón la aprueba.
     * @return el mismo {@code siguiente} recibido.
     * @throws NullPointerException si {@code siguiente} es {@code null}.
     */
    public WeatherHandler setNext(WeatherHandler siguiente) {
        this.siguiente = Objects.requireNonNull(siguiente, "El siguiente handler no puede ser nulo.");
        return siguiente;
    }

    /**
     * Procesa la petición en este eslabón y, si procede, la delega al siguiente.
     *
     * <p>Si {@link #evaluar(Weather)} reporta un incumplimiento, registra una
     * advertencia en el log y devuelve un resultado no apto con el nombre de
     * la regla y el motivo, sin consultar a los eslabones siguientes. Si la
     * regla se cumple y no hay siguiente eslabón, devuelve un resultado apto.</p>
     *
     * @param weather condiciones climáticas a evaluar.
     * @return el resultado de la primera regla incumplida, o apto si todas se cumplen.
     * @throws NullPointerException si {@code weather} es {@code null}.
     */
    public final ResultadoViabilidad handle(Weather weather) {
        Objects.requireNonNull(weather, "Weather no puede ser nulo.");

        Optional<String> incumplimiento = evaluar(weather);
        if (incumplimiento.isPresent()) {
            LOG.warning(() -> "Rechazo por " + nombreRegla() + ": " + incumplimiento.get());
            return ResultadoViabilidad.noApto(nombreRegla(), incumplimiento.get());
        }
        if (siguiente == null) {
            return ResultadoViabilidad.apto();
        }
        return siguiente.handle(weather);
    }

    /**
     * Evalúa la regla climática propia de este eslabón.
     *
     * @param weather condiciones climáticas a evaluar (nunca {@code null}).
     * @return el motivo del incumplimiento, o {@link Optional#empty()} si la regla se cumple.
     */
    protected abstract Optional<String> evaluar(Weather weather);

    /**
     * Nombre legible de la regla, usado en el resultado y en el log.
     *
     * @return nombre de la regla que evalúa este eslabón.
     */
    protected abstract String nombreRegla();
}