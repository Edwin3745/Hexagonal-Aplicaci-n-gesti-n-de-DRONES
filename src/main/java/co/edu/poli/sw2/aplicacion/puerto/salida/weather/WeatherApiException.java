package co.edu.poli.sw2.aplicacion.puerto.salida.weather;

/**
 * Error que los adaptadores del clima comunican a la aplicación.
 *
 * <p>Cumple el mismo papel que {@code RepositorioException} para la base de
 * datos: describe el fallo con un {@link Reason} entendible por la
 * aplicación, sin códigos HTTP ni detalles de la API. El adaptador es quien
 * traduce la respuesta técnica a uno de estos motivos.</p>
 */
public class WeatherApiException extends RuntimeException {

    /** Motivos de fallo que pueden comunicar los puertos del clima. */
    public enum Reason {
        /** La API rechazó la clave (HTTP 401). */
        INVALID_API_KEY,
        /** No hubo conexión o la API no respondió a tiempo. */
        UNAVAILABLE,
        /** La API respondió, pero con un contenido que no se pudo interpretar. */
        INVALID_RESPONSE
    }

    /** Motivo del fallo. */
    private final Reason reason;

    /**
     * @param reason  motivo del fallo.
     * @param message descripción técnica del fallo.
     * @param cause   excepción original, puede ser nula.
     */
    public WeatherApiException(Reason reason, String message, Throwable cause) {
        super(message, cause);
        this.reason = reason;
    }

    /**
     * @param reason  motivo del fallo.
     * @param message descripción técnica del fallo.
     */
    public WeatherApiException(Reason reason, String message) {
        this(reason, message, null);
    }

    /** @return motivo del fallo. */
    public Reason getReason() {
        return reason;
    }
}
