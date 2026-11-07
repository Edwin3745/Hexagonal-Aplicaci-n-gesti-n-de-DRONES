package co.edu.poli.sw2.dominio.state;

/**
 * Estado APAGADO: el dron no está operativo y solo puede encenderse.
 *
 * <p>Permite: encender → EN ESPERA.</p>
 */
public class OffState implements DroneState {

    private static final String NOMBRE = "APAGADO";

    @Override
    public void encender(DroneContext contexto) {
        contexto.setEstado(new ReadyState());
    }

    @Override
    public void apagar(DroneContext contexto) {
        throw new IllegalStateException("No se puede apagar un dron en estado " + NOMBRE + ".");
    }

    @Override
    public void despegar(DroneContext contexto) {
        throw new IllegalStateException("No se puede despegar un dron en estado " + NOMBRE + ".");
    }

    @Override
    public void aterrizar(DroneContext contexto) {
        throw new IllegalStateException("No se puede aterrizar un dron en estado " + NOMBRE + ".");
    }

    @Override
    public void finalizarAterrizaje(DroneContext contexto) {
        throw new IllegalStateException("No se puede finalizar el aterrizaje de un dron en estado " + NOMBRE + ".");
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}
