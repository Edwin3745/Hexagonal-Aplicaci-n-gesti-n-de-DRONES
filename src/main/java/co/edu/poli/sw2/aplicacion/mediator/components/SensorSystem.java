package co.edu.poli.sw2.aplicacion.mediator.components;

import co.edu.poli.sw2.aplicacion.mediator.DroneMediator;
import co.edu.poli.sw2.aplicacion.mediator.MissionEvent;

/** Component del Mediator: activa los sensores necesarios. Solo conoce a {@link DroneMediator}. */
public class SensorSystem {

    private DroneMediator mediator;
    private String detalle = "";

    public void setMediator(DroneMediator mediator) {
        this.mediator = mediator;
    }

    /** Activa los sensores y avisa al mediador con {@code SENSORES_OK}. */
    public void activarSensores() {
        detalle = "Sensores activados (cámara, GPS, térmico).";
        mediator.notify(this, MissionEvent.SENSORES_OK);
    }

    public String getDetalle() {
        return detalle;
    }
}
