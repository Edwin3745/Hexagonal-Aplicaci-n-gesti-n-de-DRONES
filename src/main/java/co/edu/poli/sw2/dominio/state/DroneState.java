package co.edu.poli.sw2.dominio.state;

/**
 * Patrón State: comportamiento de un dron según su estado operativo.
 *
 * <p>Es la interfaz State. Los estados concretos son {@link OffState}
 * (APAGADO), {@link ReadyState} (EN ESPERA), {@link FlyingState} (VOLANDO)
 * y {@link LandingState} (ATERRIZANDO).</p>
 *
 * <p>Cada estado concreto implementa todas las operaciones: las permitidas
 * cambian el estado del contexto y las no permitidas se rechazan con
 * {@link IllegalStateException}. Así {@link DroneContext} no necesita if/switch.</p>
 */
public interface DroneState {

    /**
     * Enciende el dron, si el estado actual lo permite.
     *
     * @param contexto contexto cuyo estado se actualiza
     * @throws IllegalStateException si no se puede encender en este estado
     */
    void encender(DroneContext contexto);

    /**
     * Apaga el dron, si el estado actual lo permite.
     *
     * @param contexto contexto cuyo estado se actualiza
     * @throws IllegalStateException si no se puede apagar en este estado
     */
    void apagar(DroneContext contexto);

    /**
     * Hace despegar el dron, si el estado actual lo permite.
     *
     * @param contexto contexto cuyo estado se actualiza
     * @throws IllegalStateException si no se puede despegar en este estado
     */
    void despegar(DroneContext contexto);

    /**
     * Inicia el aterrizaje del dron, si el estado actual lo permite.
     *
     * @param contexto contexto cuyo estado se actualiza
     * @throws IllegalStateException si no se puede aterrizar en este estado
     */
    void aterrizar(DroneContext contexto);

    /**
     * Termina el aterrizaje del dron, si el estado actual lo permite.
     *
     * @param contexto contexto cuyo estado se actualiza
     * @throws IllegalStateException si no se puede finalizar el aterrizaje en este estado
     */
    void finalizarAterrizaje(DroneContext contexto);

    /**
     * Devuelve el nombre legible del estado.
     *
     * @return nombre del estado, ej: "APAGADO".
     */
    String getNombre();
}
