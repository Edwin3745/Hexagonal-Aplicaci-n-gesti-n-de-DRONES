package co.edu.poli.sw2.dominio.command;

/**
 * ConcreteCommand del patrón Command que ordena al dron escanear la zona.
 *
 * <p>Delega en {@link OperacionesDron#escanearZona()} (Receiver) y reporta
 * la descripción que este devuelve.</p>
 */
public class EscanearZonaCommand implements DroneCommand {

    private static final String NOMBRE = "Escanear zona";

    private final OperacionesDron dron;

    /**
     * Crea el comando asociado a un receptor.
     *
     * @param dron receptor que escaneará la zona.
     * @throws IllegalArgumentException si {@code dron} es {@code null}.
     */
    public EscanearZonaCommand(OperacionesDron dron) {
        if (dron == null) {
            throw new IllegalArgumentException("El dron no puede ser nulo.");
        }
        this.dron = dron;
    }

    /**
     * Escanea la zona con el receptor.
     *
     * <p>Si el receptor lanza una {@link RuntimeException}, no se propaga:
     * se devuelve un resultado fallido con el mensaje de la excepción.</p>
     *
     * @return resultado exitoso con la descripción del escaneo, o fallido
     *         con el motivo del error.
     */
    @Override
    public ResultadoTarea ejecutar() {
        try {
            return new ResultadoTarea(getNombre(), true, dron.escanearZona());
        } catch (RuntimeException e) {
            return new ResultadoTarea(getNombre(), false, e.getMessage());
        }
    }

    /**
     * Nombre de la tarea con el serial del dron.
     *
     * @return {@code "Escanear zona [<serial>]"}.
     */
    @Override
    public String getNombre() {
        return NOMBRE + " [" + dron.getSerial() + "]";
    }
}
