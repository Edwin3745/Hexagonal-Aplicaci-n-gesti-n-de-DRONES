package co.edu.poli.sw2.dominio.state;

/**
 * Estado VOLANDO: el dron está en vuelo.
 *
 * <p>Permite: aterrizar → ATERRIZANDO. Rechaza ({@link IllegalStateException}):
 * encender, apagar, despegar, finalizarAterrizaje.</p>
 *
 * <p>Estado concreto del patrón State: extiende {@link AbstractDroneState}
 * (que implementa {@link DroneState}) y es usado por {@link DroneContext}.</p>
 */
public class FlyingState extends AbstractDroneState {
    private static final String NOMBRE = "VOLANDO";

    @Override
    public void aterrizar(DroneContext contexto) {
        contexto.setEstado(new LandingState());
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}