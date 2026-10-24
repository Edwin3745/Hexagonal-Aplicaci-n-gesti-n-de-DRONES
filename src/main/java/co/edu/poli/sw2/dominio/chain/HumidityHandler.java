package co.edu.poli.sw2.dominio.chain;

import co.edu.poli.sw2.dominio.weather.Weather;
import java.util.Optional;

public class HumidityHandler extends WeatherHandler {

    public static final double MAXIMA = 85.0;

    @Override
    protected Optional<String> evaluar(Weather weather) {
        double h = weather.getHumidity();
        if (!(h <= MAXIMA)) {
            return Optional.of("La humedad (" + h + " %) supera el máximo permitido de " + MAXIMA + " %.");
        }
        return Optional.empty();
    }

    @Override
    protected String nombreRegla() {
        return "Humedad";
    }
}