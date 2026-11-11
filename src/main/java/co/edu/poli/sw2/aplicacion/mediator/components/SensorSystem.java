package co.edu.poli.sw2.aplicacion.mediator.components;

import co.edu.poli.sw2.aplicacion.mediator.DroneMediator;
import co.edu.poli.sw2.aplicacion.mediator.MissionEvent;

/**
 * Component (Colleague) del patrón Mediator: activa (de forma simulada) los sensores
 * necesarios para la misión.
 *
 * <p>Solo conoce a {@link DroneMediator}: no llama a ningún otro componente,
 * se limita a avisar con {@code notify(this, evento)}.</p>
 */
public class SensorSystem {

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

    /** Activa los sensores y avisa al mediador con {@code SENSORES_OK}. */
    public void activarSensores() {
        detalle = "Sensores activados (cámara, GPS, térmico).";
        mediator.notify(this, MissionEvent.SENSORES_OK);
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
