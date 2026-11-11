package co.edu.poli.sw2.aplicacion.mediator.components;

import co.edu.poli.sw2.aplicacion.mediator.DroneMediator;
import co.edu.poli.sw2.aplicacion.mediator.MissionEvent;

/**
 * Component (Colleague) del patrón Mediator: verifica la disponibilidad
 * energética del dron con un nivel de batería simulado.
 *
 * <p>Solo conoce a {@link DroneMediator}: no llama a ningún otro componente,
 * se limita a avisar el resultado con {@code notify(this, evento)}.</p>
 */
public class BatterySystem {

    /** Porcentaje mínimo de batería para poder iniciar una misión. */
    public static final int NIVEL_MINIMO = 30;

    private DroneMediator mediator;
    private int nivel;
    private String detalle = "";

    /** Crea el componente con una batería simulada al 85 %. */
    public BatterySystem() {
        this(85);
    }

    /**
     * Crea el componente con el nivel de batería simulado indicado.
     *
     * @param nivel porcentaje de batería simulado (0 a 100).
     * @throws IllegalArgumentException si el nivel está fuera del rango 0 a 100.
     */
    public BatterySystem(int nivel) {
        setNivel(nivel);
    }

    /**
     * Asigna el mediador al que este componente le notifica sus eventos. Lo
     * invoca el mediador concreto al construirse.
     *
     * @param mediator mediador de la misión.
     */
    public void setMediator(DroneMediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Cambia el nivel de batería simulado, por ejemplo para probar el aborto
     * de la misión por batería baja.
     *
     * @param nivel porcentaje de batería simulado (0 a 100).
     * @throws IllegalArgumentException si el nivel está fuera del rango 0 a 100.
     */
    public void setNivel(int nivel) {
        if (nivel < 0 || nivel > 100) {
            throw new IllegalArgumentException("El nivel de batería debe estar entre 0 y 100.");
        }
        this.nivel = nivel;
    }

    /**
     * Verifica la batería contra {@link #NIVEL_MINIMO} y avisa al mediador con
     * {@code BATERIA_OK} si alcanza o con {@code BATERIA_BAJA} si no.
     */
    public void verificarBateria() {
        if (nivel >= NIVEL_MINIMO) {
            detalle = "Batería al " + nivel + " % (mínimo " + NIVEL_MINIMO + " %).";
            mediator.notify(this, MissionEvent.BATERIA_OK);
        } else {
            detalle = "Batería baja: " + nivel + " % (mínimo " + NIVEL_MINIMO + " %).";
            mediator.notify(this, MissionEvent.BATERIA_BAJA);
        }
    }

    /**
     * Devuelve la descripción de la última verificación, que el mediador
     * registra en la bitácora.
     *
     * @return detalle de la verificación; cadena vacía si aún no se ha
     *         verificado la batería.
     */
    public String getDetalle() {
        return detalle;
    }
}
