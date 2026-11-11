package co.edu.poli.sw2.dominio.command;

/**
 * ConcreteCommand del patrón Command que ordena al dron medir la temperatura.
 *
 * <p>Delega en {@link OperacionesDron#medirTemperatura()} (Receiver) y
 * reporta el valor con un decimal, en °C.</p>
 */
public class MedirTemperaturaCommand implements DroneCommand {

    private static final String NOMBRE = "Medir temperatura";

    private final OperacionesDron dron;

    /**
     * Crea el comando asociado a un receptor.
     *
     * @param dron receptor que medirá la temperatura.
     * @throws IllegalArgumentException si {@code dron} es {@code null}.
     */
    public MedirTemperaturaCommand(OperacionesDron dron) {
        if (dron == null) {
            throw new IllegalArgumentException("El dron no puede ser nulo.");
        }
        this.dron = dron;
    }

    /**
     * Mide la temperatura con el receptor.
     *
     * <p>Si el receptor lanza una {@link RuntimeException}, no se propaga:
     * se devuelve un resultado fallido con el mensaje de la excepción.</p>
     *
     * @return resultado exitoso con la temperatura formateada (por ejemplo
     *         {@code "21,5 °C"} o {@code "21.5 °C"}, según el locale), o
     *         fallido con el motivo del error.
     */
    @Override
    public ResultadoTarea ejecutar() {
        try {
            double grados = dron.medirTemperatura();
            return new ResultadoTarea(getNombre(), true, String.format("%.1f °C", grados));
        } catch (RuntimeException e) {
            return new ResultadoTarea(getNombre(), false, e.getMessage());
        }
    }

    /**
     * Nombre de la tarea con el serial del dron.
     *
     * @return {@code "Medir temperatura [<serial>]"}.
     */
    @Override
    public String getNombre() {
        return NOMBRE + " [" + dron.getSerial() + "]";
    }
}
