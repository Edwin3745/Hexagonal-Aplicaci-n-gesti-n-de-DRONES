package co.edu.poli.sw2.dominio.chain;

import co.edu.poli.sw2.dominio.weather.Weather;
import java.util.Optional;

public class WindHandler extends WeatherHandler {

    public static final double MAXIMA = 40.0;

    @Override
    protected Optional<String> evaluar(Weather weather) {
        double v = weather.getWindSpeed();
        if (!(v <= MAXIMA)) {
            return Optional.of("La velocidad del viento (" + v + " km/h) supera el máximo permitido de "
                    + MAXIMA + " km/h.");
        }
        return Optional.empty();
    }

    @Override
    protected String nombreRegla() {
        return "Velocidad del viento";
    }
}