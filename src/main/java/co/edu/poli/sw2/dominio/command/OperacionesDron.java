package co.edu.poli.sw2.dominio.command;

/**
 * Operaciones que un dron sabe hacer. Los comandos dependen de esta
 * interfaz, no de una implementación concreta.
 */
public interface OperacionesDron {

    String getSerial();

    /** @return nombre del archivo generado. */
    String tomarFotografia();

    /** @return descripción del resultado del escaneo. */
    String escanearZona();

    /** @return temperatura medida en °C. */
    double medirTemperatura();
}
