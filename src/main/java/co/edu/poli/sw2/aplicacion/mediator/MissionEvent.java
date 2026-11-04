package co.edu.poli.sw2.aplicacion.mediator;

/**
 * Eventos que los componentes le comunican al mediador mediante
 * {@link DroneMediator#notify(Object, MissionEvent)}.
 *
 * <p>Son el único "idioma" compartido entre los componentes y el mediador:
 * un componente nunca llama a otro, solo avisa de lo que le ocurrió.</p>
 */
public enum MissionEvent {
    /** La navegación terminó de preparar la ruta. */
    RUTA_LISTA,
    /** Los sensores quedaron activados. */
    SENSORES_OK,
    /** La batería tiene energía suficiente. */
    BATERIA_OK,
    /** La batería no alcanza: la misión debe abortarse. */
    BATERIA_BAJA,
    /** El enlace de comunicación quedó establecido. */
    ENLACE_OK
}
