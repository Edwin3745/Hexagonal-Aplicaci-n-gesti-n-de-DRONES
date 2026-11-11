package co.edu.poli.sw2.dominio.command;

/**
 * Operaciones que un dron sabe hacer. Los comandos dependen de esta
 * interfaz, no de una implementación concreta.
 *
 * <p>Es el Receiver del patrón Command visto desde el dominio. La
 * implementación vive en la capa de aplicación ({@code DroneOperacionService}),
 * que envuelve un dron para no tener que modificar la clase {@code Dron}.</p>
 */
public interface OperacionesDron {

    /**
     * Serial del dron que realiza las operaciones.
     *
     * @return serial del dron.
     */
    String getSerial();

    /**
     * Toma una fotografía con el dron.
     *
     * @return nombre del archivo generado.
     */
    String tomarFotografia();

    /**
     * Escanea la zona en la que se encuentra el dron.
     *
     * @return descripción del resultado del escaneo.
     */
    String escanearZona();

    /**
     * Mide la temperatura ambiente con el sensor del dron.
     *
     * @return temperatura medida en °C.
     */
    double medirTemperatura();
}
