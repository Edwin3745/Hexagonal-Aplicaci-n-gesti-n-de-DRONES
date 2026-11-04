package co.edu.poli.sw2.aplicacion.mediator.components;

import co.edu.poli.sw2.aplicacion.mediator.DroneMediator;
import co.edu.poli.sw2.aplicacion.mediator.MissionEvent;

/** Component del Mediator: establece el enlace de comunicación. Solo conoce a {@link DroneMediator}. */
public class CommunicationSystem {

    private DroneMediator mediator;
    private String detalle = "";

    public void setMediator(DroneMediator mediator) {
        this.mediator = mediator;
    }

    /** Establece el enlace y avisa al mediador con {@code ENLACE_OK}. */
    public void establecerEnlace() {
        detalle = "Enlace de comunicación con la estación establecido.";
        mediator.notify(this, MissionEvent.ENLACE_OK);
    }

    public String getDetalle() {
        return detalle;
    }
}
