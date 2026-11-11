package co.edu.poli.sw2.dominio.state;

/**
 * Estado EN ESPERA: encendido, en tierra y listo para operar.
 *
 * <p>Permite: despegar → VOLANDO y apagar → APAGADO. Rechaza ({@link IllegalStateException}):
 * encender, aterrizar, finalizarAterrizaje.</p>
 *
 * <p>Estado concreto del patrón State: implementa {@link DroneState} y
 * es usado por {@link DroneContext}.</p>
 */
public class ReadyState implements DroneState {

    private static final String NOMBRE = "EN ESPERA";

    @Override
    public void encender(DroneContext contexto) {
        throw new IllegalStateException("No se puede encender un dron en estado " + NOMBRE + ".");
    }

    @Override
    public void apagar(DroneContext contexto) {
        contexto.setEstado(new OffState());
    }

    @Override
    public void despegar(DroneContext contexto) {
        contexto.setEstado(new FlyingState());
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
