package co.edu.poli.sw2.dominio.state;

/**
 * Estado ATERRIZANDO: no puede iniciar un nuevo vuelo hasta finalizar.
 *
 * <p>Permite: finalizarAterrizaje → EN ESPERA. Rechaza ({@link IllegalStateException}):
 * encender, apagar, despegar, aterrizar.</p>
 *
 * <p>Estado concreto del patrón State: implementa {@link DroneState} y
 * es usado por {@link DroneContext}.</p>
 */
public class LandingState implements DroneState {

    private static final String NOMBRE = "ATERRIZANDO";

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
        throw new IllegalStateException("No se puede aterrizar un dron en estado " + NOMBRE + ".");
    }

    @Override
    public void finalizarAterrizaje(DroneContext contexto) {
        contexto.setEstado(new ReadyState());
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}
