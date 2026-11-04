package co.edu.poli.sw2.aplicacion.mediator.components;

import co.edu.poli.sw2.aplicacion.mediator.DroneMediator;
import co.edu.poli.sw2.aplicacion.mediator.MissionEvent;

/** Component del Mediator: prepara la ruta del dron. Solo conoce a {@link DroneMediator}. */
public class NavigationSystem {

    private DroneMediator mediator;
    private String detalle = "";

    public void setMediator(DroneMediator mediator) {
        this.mediator = mediator;
    }

    /** Prepara la ruta y avisa al mediador con {@code RUTA_LISTA}. */
    public void prepararRuta() {
        detalle = "Ruta de vigilancia calculada y cargada.";
        mediator.notify(this, MissionEvent.RUTA_LISTA);
    }

    public String getDetalle() {
        return detalle;
    }
}
