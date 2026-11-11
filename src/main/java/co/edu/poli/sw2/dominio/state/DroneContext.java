package co.edu.poli.sw2.dominio.state;

import co.edu.poli.sw2.dominio.modelo.Dron;

/**
 * Contexto del patrón State: envuelve un {@link Dron} (sin modificarlo) y
 * guarda su estado operativo actual, en el que delega cada operación.
 *
 * <p>Todo contexto nuevo inicia en {@link OffState} (APAGADO). El ciclo
 * operativo es: APAGADO → encender → EN ESPERA → despegar → VOLANDO →
 * aterrizar → ATERRIZANDO → finalizarAterrizaje → EN ESPERA → apagar →
 * APAGADO.</p>
 */
public class DroneContext {

    private final Dron dron;

    private DroneState estado = new OffState();

    /**
     * Crea el contexto de un dron, en estado APAGADO.
     *
     * @param dron dron cuyo estado operativo se controla
     * @throws IllegalArgumentException si el dron es nulo
     */
    public DroneContext(Dron dron) {
        if (dron == null) {
            throw new IllegalArgumentException("El dron no puede ser nulo.");
        }
        this.dron = dron;
    }

    /**
     * Devuelve el dron envuelto por este contexto.
     *
     * @return dron asociado
     */
    public Dron getDron() {
        return dron;
    }

    /**
     * Devuelve el estado operativo actual.
     *
     * @return estado actual del dron
     */
    public DroneState getEstado() {
        return estado;
    }

    /**
     * Visibilidad de paquete a propósito: solo los estados (mismo paquete)
     * pueden hacer la transición, así nadie se salta el ciclo operativo.
     *
     * @param estado nuevo estado operativo
     */
    void setEstado(DroneState estado) {
        this.estado = estado;
    }

    /**
     * Enciende el dron delegando en el estado actual.
     *
     * @throws IllegalStateException si el estado actual no permite encender
     */
    public void encender() {
        estado.encender(this);
    }

    /**
     * Apaga el dron delegando en el estado actual.
     *
     * @throws IllegalStateException si el estado actual no permite apagar
     */
    public void apagar() {
        estado.apagar(this);
    }

    /**
     * Hace despegar el dron delegando en el estado actual.
     *
     * @throws IllegalStateException si el estado actual no permite despegar
     */
    public void despegar() {
        estado.despegar(this);
    }

    /**
     * Inicia el aterrizaje delegando en el estado actual.
     *
     * @throws IllegalStateException si el estado actual no permite aterrizar
     */
    public void aterrizar() {
        estado.aterrizar(this);
    }

    /**
     * Termina el aterrizaje delegando en el estado actual.
     *
     * @throws IllegalStateException si el estado actual no permite finalizar
     *                               el aterrizaje
     */
    public void finalizarAterrizaje() {
        estado.finalizarAterrizaje(this);
    }
}
