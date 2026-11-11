package co.edu.poli.sw2.dominio.state;

/**
 * Estado VOLANDO: el dron está en vuelo.
 *
 * <p>Permite: aterrizar → ATERRIZANDO.</p>
 *
 * <p>Estado concreto del patrón State: implementa {@link DroneState} y
 * es usado por {@link DroneContext}.</p>
 */
public class FlyingState implements DroneState {

    /**
     * Nombre legible de este estado.
     */
    private static final String NOMBRE = "VOLANDO";

    /**
     * Operación no permitida: un dron en estado VOLANDO no puede encenderse.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void encender(DroneContext contexto) {
        throw new IllegalStateException("No se puede encender un dron en estado " + NOMBRE + ".");
    }

    /**
     * Operación no permitida: un dron en estado VOLANDO no puede apagarse.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void apagar(DroneContext contexto) {
        throw new IllegalStateException("No se puede apagar un dron en estado " + NOMBRE + ".");
    }

    /**
     * Operación no permitida: un dron en estado VOLANDO no puede despegar.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void despegar(DroneContext contexto) {
        throw new IllegalStateException("No se puede despegar un dron en estado " + NOMBRE + ".");
    }

    /**
     * Inicia el aterrizaje: pasa a {@link LandingState} (ATERRIZANDO).
     *
     * @param contexto contexto cuyo estado se actualiza
     */
    @Override
    public void aterrizar(DroneContext contexto) {
        contexto.setEstado(new LandingState());
    }

    /**
     * Operación no permitida: un dron en estado VOLANDO no puede finalizar el aterrizaje.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void finalizarAterrizaje(DroneContext contexto) {
        throw new IllegalStateException("No se puede finalizar el aterrizaje de un dron en estado " + NOMBRE + ".");
    }

    /**
     * Devuelve el nombre de este estado.
     *
     * @return {@code "VOLANDO"}
     */
    @Override
    public String getNombre() {
        return NOMBRE;
    }
}
