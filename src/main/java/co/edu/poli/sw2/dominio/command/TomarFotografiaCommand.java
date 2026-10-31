package co.edu.poli.sw2.dominio.command;

public class TomarFotografiaCommand implements DroneCommand {

    private static final String NOMBRE = "Tomar fotografía";

    private final OperacionesDron dron;

    public TomarFotografiaCommand(OperacionesDron dron) {
        if (dron == null) {
            throw new IllegalArgumentException("El dron no puede ser nulo.");
        }
        this.dron = dron;
    }

    @Override
    public ResultadoTarea ejecutar() {
        try {
            String archivo = dron.tomarFotografia();
            return new ResultadoTarea(getNombre(), true, "Fotografía guardada: " + archivo);
        } catch (RuntimeException e) {
            return new ResultadoTarea(getNombre(), false, e.getMessage());
        }
    }

    @Override
    public String getNombre() {
        return NOMBRE + " [" + dron.getSerial() + "]";
    }
}
