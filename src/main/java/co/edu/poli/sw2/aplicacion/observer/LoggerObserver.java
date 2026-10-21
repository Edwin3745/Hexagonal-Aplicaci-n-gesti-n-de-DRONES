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
 * Observador que deja constancia de cada clima recibido en un archivo de
 * registro.
 *
 * <p>Cada notificación agrega una línea con la fecha, la hora y los cinco
 * aspectos del clima. El archivo nunca se sobrescribe: crece con cada
 * consulta, de modo que queda un historial de todas las condiciones con las
 * que se evaluó la operación de los drones.</p>
 *
 * <p>Si la carpeta del archivo no existe, se crea en la primera escritura.</p>
 */
public class LoggerObserver implements WeatherObserver {

    /** Ruta usada cuando no se indica otra. */
    public static final Path RUTA_POR_DEFECTO = Path.of("logs", "clima.log");

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Archivo donde se escribe el registro. */
    private final Path archivo;

    /**
     * Crea el observador escribiendo en {@link #RUTA_POR_DEFECTO}.
     */
    public LoggerObserver() {
        this(RUTA_POR_DEFECTO);
    }

    /**
     * Crea el observador escribiendo en el archivo indicado.
     *
     * @param archivo ruta del archivo de registro; no puede ser nula.
     * @throws IllegalArgumentException si la ruta es nula.
     */
    public LoggerObserver(Path archivo) {
        if (archivo == null) {
            throw new IllegalArgumentException("La ruta del registro no puede ser nula.");
        }
        this.archivo = archivo;
    }

    /**
     * Agrega una línea al registro con la fecha y el clima recibido.
     *
     * @param weather clima recién consultado.
     * @throws UncheckedIOException si no se puede escribir el archivo.
     */
    @Override
    public void update(Weather weather) {
        String linea = LocalDateTime.now().format(FORMATO_FECHA) + " | " + weather
                + System.lineSeparator();
        try {
            Path carpeta = archivo.toAbsolutePath().getParent();
            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }
            Files.writeString(archivo, linea, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo escribir el registro del clima en " + archivo, e);
        }
    }

    /**
     * @return ruta del archivo de registro.
     */
    public Path getArchivo() {
        return archivo;
    }
}
