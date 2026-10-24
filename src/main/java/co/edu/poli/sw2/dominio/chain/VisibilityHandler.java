package co.edu.poli.sw2.dominio.chain;

import co.edu.poli.sw2.dominio.weather.Weather;
import java.util.Optional;

public class VisibilityHandler extends WeatherHandler {

    public static final double MINIMA = 5.0;

    @Override
    protected Optional<String> evaluar(Weather weather) {
        double v = weather.getVisibility();
        if (!(v >= MINIMA)) {
            return Optional.of("La visibilidad (" + v + " km) es inferior al mínimo requerido de "
                    + MINIMA + " km.");
        }
        return Optional.empty();
    }

    @Override
    protected String nombreRegla() {
        return "Visibilidad";
    }
}