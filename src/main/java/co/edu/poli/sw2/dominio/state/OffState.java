package co.edu.poli.sw2.dominio.state;

/**
 * Estado APAGADO: el dron no está operativo y solo puede encenderse.
 *
 * <p>Permite: encender → EN ESPERA. Rechaza ({@link IllegalStateException}):
 * apagar, despegar, aterrizar, finalizarAterrizaje.</p>
 *
 * <p>Estado concreto del patrón State: extiende {@link AbstractDroneState}
 * (que implementa {@link DroneState}) y es usado por {@link DroneContext}.</p>
 */
public class OffState extends AbstractDroneState {
    private static final String NOMBRE = "APAGADO";

    @Override
    public void encender(DroneContext contexto) {
        contexto.setEstado(new ReadyState());
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}