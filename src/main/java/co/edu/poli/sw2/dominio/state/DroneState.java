package co.edu.poli.sw2.dominio.state;

/**
 * Contrato de los estados del dron (patrón State).
 */
public interface DroneState {

    void encender(DroneContext contexto);

    void apagar(DroneContext contexto);

    void despegar(DroneContext contexto);

    void aterrizar(DroneContext contexto);

    void finalizarAterrizaje(DroneContext contexto);

    String getNombre();
}