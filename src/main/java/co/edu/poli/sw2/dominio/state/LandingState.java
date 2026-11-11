package co.edu.poli.sw2.dominio.state;

/**
 * Estado ATERRIZANDO: no puede iniciar un nuevo vuelo hasta finalizar.
 *
 * <p>Permite: finalizarAterrizaje → EN ESPERA.</p>
 *
 * <p>Estado concreto del patrón State: implementa {@link DroneState} y
 * es usado por {@link DroneContext}.</p>
 */
public class LandingState implements DroneState {

    /**
     * Nombre legible de este estado.
     */
    private static final String NOMBRE = "ATERRIZANDO";

    /**
     * Operación no permitida: un dron en estado ATERRIZANDO no puede encenderse.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void encender(DroneContext contexto) {
        throw new IllegalStateException("No se puede encender un dron en estado " + NOMBRE + ".");
    }

    /**
     * Operación no permitida: un dron en estado ATERRIZANDO no puede apagarse.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void apagar(DroneContext contexto) {
        throw new IllegalStateException("No se puede apagar un dron en estado " + NOMBRE + ".");
    }

    /**
     * Operación no permitida: un dron en estado ATERRIZANDO no puede despegar.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void despegar(DroneContext contexto) {
        throw new IllegalStateException("No se puede despegar un dron en estado " + NOMBRE + ".");
    }

    /**
     * Operación no permitida: un dron en estado ATERRIZANDO no puede aterrizar.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void aterrizar(DroneContext contexto) {
        throw new IllegalStateException("No se puede aterrizar un dron en estado " + NOMBRE + ".");
    }

    /**
     * Termina el aterrizaje: pasa a {@link ReadyState} (EN ESPERA).
     *
     * @param contexto contexto cuyo estado se actualiza
     */
    @Override
    public void finalizarAterrizaje(DroneContext contexto) {
        contexto.setEstado(new ReadyState());
    }

    /**
     * Devuelve el nombre de este estado.
     *
     * @return {@code "ATERRIZANDO"}
     */
    @Override
    public String getNombre() {
        return NOMBRE;
    }
}
