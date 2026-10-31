package co.edu.poli.sw2.dominio.command;

public class MedirTemperaturaCommand implements DroneCommand {

    private static final String NOMBRE = "Medir temperatura";

    private final OperacionesDron dron;

    public MedirTemperaturaCommand(OperacionesDron dron) {
        if (dron == null) {
            throw new IllegalArgumentException("El dron no puede ser nulo.");
        }
        this.dron = dron;
    }

    @Override
    public ResultadoTarea ejecutar() {
        try {
            double grados = dron.medirTemperatura();
            return new ResultadoTarea(getNombre(), true, String.format("%.1f °C", grados));
        } catch (RuntimeException e) {
            return new ResultadoTarea(getNombre(), false, e.getMessage());
        }
    }

    @Override
    public String getNombre() {
        return NOMBRE + " [" + dron.getSerial() + "]";
    }
}
