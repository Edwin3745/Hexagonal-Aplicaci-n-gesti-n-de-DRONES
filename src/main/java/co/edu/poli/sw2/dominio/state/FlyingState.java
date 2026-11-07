package co.edu.poli.sw2.dominio.state;

/**
 * Estado VOLANDO: el dron está en vuelo.
 *
 * <p>Permite: aterrizar → ATERRIZANDO.</p>
 */
public class FlyingState implements DroneState {

    private static final String NOMBRE = "VOLANDO";

    @Override
    public void encender(DroneContext contexto) {
        throw new IllegalStateException("No se puede encender un dron en estado " + NOMBRE + ".");
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
        contexto.setEstado(new LandingState());
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
