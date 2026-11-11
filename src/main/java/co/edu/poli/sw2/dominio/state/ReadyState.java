package co.edu.poli.sw2.dominio.state;

/**
 * Estado EN ESPERA: encendido, en tierra y listo para operar.
 *
 * <p>Permite: despegar → VOLANDO y apagar → APAGADO.</p>
 *
 * <p>Estado concreto del patrón State: implementa {@link DroneState} y
 * es usado por {@link DroneContext}.</p>
 */
public class ReadyState implements DroneState {

    /**
     * Nombre legible de este estado.
     */
    private static final String NOMBRE = "EN ESPERA";

    /**
     * Operación no permitida: un dron en estado EN ESPERA no puede encenderse.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void encender(DroneContext contexto) {
        throw new IllegalStateException("No se puede encender un dron en estado " + NOMBRE + ".");
    }

    /**
     * Apaga el dron: pasa a {@link OffState} (APAGADO).
     *
     * @param contexto contexto cuyo estado se actualiza
     */
    @Override
    public void apagar(DroneContext contexto) {
        contexto.setEstado(new OffState());
    }

    /**
     * Despega el dron: pasa a {@link FlyingState} (VOLANDO).
     *
     * @param contexto contexto cuyo estado se actualiza
     */
    @Override
    public void despegar(DroneContext contexto) {
        contexto.setEstado(new FlyingState());
    }

    /**
     * Operación no permitida: un dron en estado EN ESPERA no puede aterrizar.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void aterrizar(DroneContext contexto) {
        throw new IllegalStateException("No se puede aterrizar un dron en estado " + NOMBRE + ".");
    }

    /**
     * Operación no permitida: un dron en estado EN ESPERA no puede finalizar el aterrizaje.
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
     * @return {@code "EN ESPERA"}
     */
    @Override
    public String getNombre() {
        return NOMBRE;
    }
}
