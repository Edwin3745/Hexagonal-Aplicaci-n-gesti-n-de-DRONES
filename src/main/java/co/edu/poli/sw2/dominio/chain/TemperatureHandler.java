package co.edu.poli.sw2.dominio.chain;

import co.edu.poli.sw2.dominio.weather.Weather;
import java.util.Optional;

public class TemperatureHandler extends WeatherHandler {

    public static final double MINIMA = 0.0;
    public static final double MAXIMA = 40.0;

    @Override
    protected Optional<String> evaluar(Weather weather) {
        double t = weather.getTemperature();
        if (!(t >= MINIMA && t <= MAXIMA)) {
            return Optional.of("La temperatura (" + t + " °C) está fuera del rango permitido de "
                    + MINIMA + " °C a " + MAXIMA + " °C.");
        }
        return Optional.empty();
    }

    @Override
    protected String nombreRegla() {
        return "Temperatura";
    }
}