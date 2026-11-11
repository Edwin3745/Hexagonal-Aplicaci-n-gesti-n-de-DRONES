package co.edu.poli.sw2.dominio.command;

/**
 * ConcreteCommand del patrón Command que ordena al dron tomar una fotografía.
 *
 * <p>Delega en {@link OperacionesDron#tomarFotografia()} (Receiver) y reporta
 * el nombre del archivo generado.</p>
 */
public class TomarFotografiaCommand implements DroneCommand {

    private static final String NOMBRE = "Tomar fotografía";

    private final OperacionesDron dron;

    /**
     * Crea el comando asociado a un receptor.
     *
     * @param dron receptor que tomará la fotografía.
     * @throws IllegalArgumentException si {@code dron} es {@code null}.
     */
    public TomarFotografiaCommand(OperacionesDron dron) {
        if (dron == null) {
            throw new IllegalArgumentException("El dron no puede ser nulo.");
        }
        this.dron = dron;
    }

    /**
     * Toma la fotografía con el receptor.
     *
     * <p>Si el receptor lanza una {@link RuntimeException}, no se propaga:
     * se devuelve un resultado fallido con el mensaje de la excepción.</p>
     *
     * @return resultado exitoso con {@code "Fotografía guardada: <archivo>"},
     *         o fallido con el motivo del error.
     */
    @Override
    public ResultadoTarea ejecutar() {
        try {
            String archivo = dron.tomarFotografia();
            return new ResultadoTarea(getNombre(), true, "Fotografía guardada: " + archivo);
        } catch (RuntimeException e) {
            return new ResultadoTarea(getNombre(), false, e.getMessage());
        }
    }

    /**
     * Nombre de la tarea con el serial del dron.
     *
     * @return {@code "Tomar fotografía [<serial>]"}.
     */
    @Override
    public String getNombre() {
        return NOMBRE + " [" + dron.getSerial() + "]";
    }
}
