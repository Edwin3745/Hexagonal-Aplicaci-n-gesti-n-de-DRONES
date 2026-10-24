package co.edu.poli.sw2.aplicacion.observer;

import co.edu.poli.sw2.dominio.weather.Weather;
import co.edu.poli.sw2.dominio.weather.WeatherObserver;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Observador que registra cada clima recibido en {@code logs/clima.log},
 * agregando una línea con la fecha y la hora.
 */
public class LoggerObserver implements WeatherObserver {

    private static final Path ARCHIVO = Path.of("logs", "clima.log");
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** {@inheritDoc} */
    @Override
    public void update(Weather weather) {
        String linea = LocalDateTime.now().format(FORMATO) + " | " + weather + System.lineSeparator();
        try {
            Files.createDirectories(ARCHIVO.getParent());
            Files.writeString(ARCHIVO, linea, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo escribir el registro del clima.", e);
        }
    }
}