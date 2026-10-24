package co.edu.poli.sw2.dominio.chain;

import co.edu.poli.sw2.dominio.weather.Weather;
import java.util.Optional;

public class PressureHandler extends WeatherHandler {

    public static final double MINIMA = 950.0;
    public static final double MAXIMA = 1050.0;

    @Override
    protected Optional<String> evaluar(Weather weather) {
        double p = weather.getPressure();
        if (!(p >= MINIMA && p <= MAXIMA)) {
            return Optional.of("La presión atmosférica (" + p + " hPa) está fuera del rango permitido de "
                    + MINIMA + " hPa a " + MAXIMA + " hPa.");
        }
        return Optional.empty();
    }

    @Override
    protected String nombreRegla() {
        return "Presión atmosférica";
    }
}