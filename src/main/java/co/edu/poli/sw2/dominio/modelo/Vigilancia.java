package co.edu.poli.sw2.dominio.modelo;

import co.edu.poli.sw2.dominio.enumeracion.TipoDron;

/**
 * Representa un dron destinado a actividades de vigilancia.
 *
 * <p>Es una especialización concreta de {@link Dron}: agrega si el equipo
 * cuenta con detección térmica y se identifica con
 * {@link TipoDron#VIGILANCIA}.</p>
 */
public class Vigilancia extends Dron {

    /**
     * Indica si el dron posee detección térmica.
     */
    private boolean deteccionTermica;

    /**
     * Constructor vacío, usado al reconstruir el dron desde la persistencia
     * o desde la vista.
     */
    public Vigilancia() {
        super();
    }

    /**
     * Constructor completo.
     *
     * @param id               identificador del dron
     * @param serial           número de serie
     * @param modelo           modelo del dron
     * @param fabricante       fabricante del dron
     * @param peso             peso del dron en kilogramos
     * @param deteccionTermica {@code true} si el dron tiene cámara térmica
     */
      public Vigilancia(int id, String serial, String modelo,
                      String fabricante, double peso,
                      boolean deteccionTermica) {
        super(id, serial, modelo, fabricante, peso);
        this.deteccionTermica = deteccionTermica;
    }

    /**
     * Indica si el dron cuenta con detección térmica.
     *
     * @return {@code true} si tiene cámara térmica; {@code false} si usa cámara RGB
     */
    public boolean isDeteccionTermica() {
        return deteccionTermica;
    }

    /**
     * Activa o desactiva la detección térmica del dron.
     *
     * @param deteccionTermica {@code true} si el dron tiene cámara térmica
     */
    public void setDeteccionTermica(boolean deteccionTermica) {
        this.deteccionTermica = deteccionTermica;
    }

    /**
     * Devuelve los datos básicos del dron de vigilancia en una línea.
     *
     * @return id, serial, modelo, fabricante, peso y detección térmica
     */
    @Override
    public String toString() {
        return "Vigilancia{" +
                "id='" + getId() + '\'' +
                ", serial='" + getSerial() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", fabricante='" + getFabricante() + '\'' +
                ", peso=" + getPeso() +
                ", deteccionTermica=" + deteccionTermica +
                '}';
    }
        /**
         * Identifica este dron como de vigilancia.
         *
         * @return siempre {@link TipoDron#VIGILANCIA}
         */
        @Override
    public TipoDron getTipo() {
        return TipoDron.VIGILANCIA;
    }

    /**
     * Describe la labor de patrullaje del dron según su cámara: con
     * detección térmica puede operar de noche; sin ella requiere luz diurna.
     *
     * @return descripción operativa según el tipo de cámara
     */
    @Override
    public String descripcionOperativa() {
        return deteccionTermica
                ? "Patrullaje y monitoreo con cámara térmica (opera de noche)"
                : "Patrullaje y monitoreo con cámara RGB (requiere luz diurna)";
    }
}
