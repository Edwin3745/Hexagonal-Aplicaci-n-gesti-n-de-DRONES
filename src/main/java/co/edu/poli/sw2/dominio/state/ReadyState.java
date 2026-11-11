package co.edu.poli.sw2.dominio.state;

/**
 * Estado EN ESPERA: encendido, en tierra y listo para operar.
 *
 * <p>Permite: despegar → VOLANDO y apagar → APAGADO. Rechaza ({@link IllegalStateException}):
 * encender, aterrizar, finalizarAterrizaje.</p>
 *
 * <p>Estado concreto del patrón State: extiende {@link AbstractDroneState}
 * (que implementa {@link DroneState}) y es usado por {@link DroneContext}.</p>
 */
public class ReadyState extends AbstractDroneState {

    private static final String NOMBRE = "EN ESPERA";

    @Override
    public void apagar(DroneContext contexto) {
        contexto.setEstado(new OffState());
    }

    @Override
    public void despegar(DroneContext contexto) {
        contexto.setEstado(new FlyingState());
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}