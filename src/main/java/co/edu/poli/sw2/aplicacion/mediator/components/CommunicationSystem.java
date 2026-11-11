package co.edu.poli.sw2.aplicacion.mediator.components;

import co.edu.poli.sw2.aplicacion.mediator.DroneMediator;
import co.edu.poli.sw2.aplicacion.mediator.MissionEvent;

/**
 * Component (Colleague) del patrón Mediator: establece (de forma simulada) el enlace de
 * comunicación con la estación.
 *
 * <p>Solo conoce a {@link DroneMediator}: no llama a ningún otro componente,
 * se limita a avisar con {@code notify(this, evento)}.</p>
 */
public class CommunicationSystem {

    private DroneMediator mediator;
    private String detalle = "";

    /**
     * Asigna el mediador al que este componente le notifica sus eventos. Lo
     * invoca el mediador concreto al construirse.
     *
     * @param mediator mediador de la misión.
     */
    public void setMediator(DroneMediator mediator) {
        this.mediator = mediator;
    }

    /** Establece el enlace y avisa al mediador con {@code ENLACE_OK}. */
    public void establecerEnlace() {
        detalle = "Enlace de comunicación con la estación establecido.";
        mediator.notify(this, MissionEvent.ENLACE_OK);
    }

    /**
     * Devuelve la descripción del último paso realizado, que el mediador
     * registra en la bitácora.
     *
     * @return detalle del paso; cadena vacía si aún no se ha ejecutado.
     */
    public String getDetalle() {
        return detalle;
    }
}
