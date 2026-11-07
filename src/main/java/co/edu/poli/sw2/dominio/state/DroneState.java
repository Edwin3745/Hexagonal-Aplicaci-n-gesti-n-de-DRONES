package co.edu.poli.sw2.dominio.state;

/**
 * Patrón State: comportamiento de un dron según su estado operativo.
 *
 * <p>Cada estado concreto implementa todas las operaciones: las permitidas
 * cambian el estado del contexto y las no permitidas se rechazan con
 * {@link IllegalStateException}. Así {@link DroneContext} no necesita if/switch.</p>
 */
public interface DroneState {

    void encender(DroneContext contexto);

    void apagar(DroneContext contexto);

    void despegar(DroneContext contexto);

    void aterrizar(DroneContext contexto);

    void finalizarAterrizaje(DroneContext contexto);

    /** @return nombre del estado, ej: "APAGADO". */
    String getNombre();
}
