package co.edu.poli.sw2.aplicacion.mediator.components;

import co.edu.poli.sw2.aplicacion.mediator.DroneMediator;
import co.edu.poli.sw2.aplicacion.mediator.MissionEvent;

/**
 * Component (Colleague) del patrón Mediator: prepara (de forma simulada) la ruta de
 * vigilancia del dron.
 *
 * <p>Solo conoce a {@link DroneMediator}: no llama a ningún otro componente,
 * se limita a avisar con {@code notify(this, evento)}.</p>
 */
public class NavigationSystem {

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

    /** Prepara la ruta y avisa al mediador con {@code RUTA_LISTA}. */
    public void prepararRuta() {
        detalle = "Ruta de vigilancia calculada y cargada.";
        mediator.notify(this, MissionEvent.RUTA_LISTA);
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
