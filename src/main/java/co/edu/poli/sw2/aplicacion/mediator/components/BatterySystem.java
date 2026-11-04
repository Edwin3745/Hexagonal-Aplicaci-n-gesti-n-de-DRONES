package co.edu.poli.sw2.aplicacion.mediator.components;

import co.edu.poli.sw2.aplicacion.mediator.DroneMediator;
import co.edu.poli.sw2.aplicacion.mediator.MissionEvent;

/**
 * Component del Mediator: verifica la disponibilidad energética.
 * Solo conoce a {@link DroneMediator}.
 */
public class BatterySystem {

    /** Porcentaje mínimo de batería para poder iniciar una misión. */
    public static final int NIVEL_MINIMO = 30;

    private DroneMediator mediator;
    private int nivel;
    private String detalle = "";

    /** Batería simulada al 85 %. */
    public BatterySystem() {
        this(85);
    }

    /** @param nivel porcentaje de batería simulado (0 a 100). */
    public BatterySystem(int nivel) {
        setNivel(nivel);
    }

    public void setMediator(DroneMediator mediator) {
        this.mediator = mediator;
    }

    public void setNivel(int nivel) {
        if (nivel < 0 || nivel > 100) {
            throw new IllegalArgumentException("El nivel de batería debe estar entre 0 y 100.");
        }
        this.nivel = nivel;
    }

    /** Verifica la batería y avisa con {@code BATERIA_OK} o {@code BATERIA_BAJA}. */
    public void verificarBateria() {
        if (nivel >= NIVEL_MINIMO) {
            detalle = "Batería al " + nivel + " % (mínimo " + NIVEL_MINIMO + " %).";
            mediator.notify(this, MissionEvent.BATERIA_OK);
        } else {
            detalle = "Batería baja: " + nivel + " % (mínimo " + NIVEL_MINIMO + " %).";
            mediator.notify(this, MissionEvent.BATERIA_BAJA);
        }
    }

    public String getDetalle() {
        return detalle;
    }
}
