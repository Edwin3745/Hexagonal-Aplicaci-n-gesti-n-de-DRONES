package co.edu.poli.sw2.dominio.state;

/**
 * Estado APAGADO: el dron no está operativo y solo puede encenderse.
 *
 * <p>Permite: encender → EN ESPERA.</p>
 *
 * <p>Estado concreto del patrón State: implementa {@link DroneState} y
 * es usado por {@link DroneContext}.</p>
 */
public class OffState implements DroneState {

    /**
     * Nombre legible de este estado.
     */
    private static final String NOMBRE = "APAGADO";

    /**
     * Enciende el dron: pasa a {@link ReadyState} (EN ESPERA).
     *
     * @param contexto contexto cuyo estado se actualiza
     */
    @Override
    public void encender(DroneContext contexto) {
        contexto.setEstado(new ReadyState());
    }

    /**
     * Operación no permitida: un dron en estado APAGADO no puede apagarse.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void apagar(DroneContext contexto) {
        throw new IllegalStateException("No se puede apagar un dron en estado " + NOMBRE + ".");
    }

    /**
     * Operación no permitida: un dron en estado APAGADO no puede despegar.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void despegar(DroneContext contexto) {
        throw new IllegalStateException("No se puede despegar un dron en estado " + NOMBRE + ".");
    }

    /**
     * Operación no permitida: un dron en estado APAGADO no puede aterrizar.
     *
     * @param contexto contexto del dron; no se modifica
     * @throws IllegalStateException siempre, porque la operación no está permitida
     */
    @Override
    public void aterrizar(DroneContext contexto) {
        throw new IllegalStateException("No se puede aterrizar un dron en estado " + NOMBRE + ".");
    }

    /**
     * Operación no permitida: un dron en estado APAGADO no puede finalizar el aterrizaje.
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
     * @return {@code "APAGADO"}
     */
    @Override
    public String getNombre() {
        return NOMBRE;
    }
}
