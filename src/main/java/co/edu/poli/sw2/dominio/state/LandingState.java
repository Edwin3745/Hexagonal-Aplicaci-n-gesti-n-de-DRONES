package co.edu.poli.sw2.dominio.state;

/**
 * Estado ATERRIZANDO: no puede iniciar un nuevo vuelo hasta finalizar.
 *
 * <p>Permite: finalizarAterrizaje → EN ESPERA. Rechaza ({@link IllegalStateException}):
 * encender, apagar, despegar, aterrizar.</p>
 *
 * <p>Estado concreto del patrón State: extiende {@link AbstractDroneState}
 * (que implementa {@link DroneState}) y es usado por {@link DroneContext}.</p>
 */
public class LandingState extends AbstractDroneState {
    private static final String NOMBRE = "ATERRIZANDO";

    @Override
    public void finalizarAterrizaje(DroneContext contexto) {
        contexto.setEstado(new ReadyState());
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}