package co.edu.poli.sw2.dominio.chain;

import co.edu.poli.sw2.dominio.weather.Weather;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

public abstract class WeatherHandler {

    private static final Logger LOG = Logger.getLogger(WeatherHandler.class.getName());

    private WeatherHandler siguiente;

    public WeatherHandler setNext(WeatherHandler siguiente) {
        this.siguiente = Objects.requireNonNull(siguiente, "El siguiente handler no puede ser nulo.");
        return siguiente;
    }

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

    protected abstract Optional<String> evaluar(Weather weather);

    protected abstract String nombreRegla();
}