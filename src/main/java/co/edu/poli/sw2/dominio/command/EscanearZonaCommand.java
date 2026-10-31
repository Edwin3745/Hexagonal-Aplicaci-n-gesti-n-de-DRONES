package co.edu.poli.sw2.dominio.command;

public class EscanearZonaCommand implements DroneCommand {

    private static final String NOMBRE = "Escanear zona";

    private final OperacionesDron dron;

    public EscanearZonaCommand(OperacionesDron dron) {
        if (dron == null) {
            throw new IllegalArgumentException("El dron no puede ser nulo.");
        }
        this.dron = dron;
    }

    @Override
    public ResultadoTarea ejecutar() {
        try {
            return new ResultadoTarea(getNombre(), true, dron.escanearZona());
        } catch (RuntimeException e) {
            return new ResultadoTarea(getNombre(), false, e.getMessage());
        }
    }

    @Override
    public String getNombre() {
        return NOMBRE + " [" + dron.getSerial() + "]";
    }
}
