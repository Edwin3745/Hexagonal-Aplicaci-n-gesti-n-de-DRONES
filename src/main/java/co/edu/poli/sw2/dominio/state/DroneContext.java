package co.edu.poli.sw2.dominio.state;

import co.edu.poli.sw2.dominio.modelo.Dron;

/**
 * Contexto del patrón State: envuelve un {@link Dron} (sin modificarlo) y
 * guarda su estado operativo actual, en el que delega cada operación.
 *
 * <p>Todo contexto nuevo inicia en {@link OffState} (APAGADO).</p>
 */
public class DroneContext {

    private final Dron dron;

    private DroneState estado = new OffState();

    public DroneContext(Dron dron) {
        if (dron == null) {
            throw new IllegalArgumentException("El dron no puede ser nulo.");
        }
        this.dron = dron;
    }

    public Dron getDron() {
        return dron;
    }

    public DroneState getEstado() {
        return estado;
    }

    /**
     * Visibilidad de paquete a propósito: solo los estados (mismo paquete)
     * pueden hacer la transición, así nadie se salta el ciclo operativo.
     */
    void setEstado(DroneState estado) {
        this.estado = estado;
    }

    public void encender() {
        estado.encender(this);
    }

    public void apagar() {
        estado.apagar(this);
    }

    public void despegar() {
        estado.despegar(this);
    }

    public void aterrizar() {
        estado.aterrizar(this);
    }

    public void finalizarAterrizaje() {
        estado.finalizarAterrizaje(this);
    }
}
