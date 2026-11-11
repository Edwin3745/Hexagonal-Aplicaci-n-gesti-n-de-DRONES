package co.edu.poli.sw2.dominio.modelo;

import co.edu.poli.sw2.dominio.enumeracion.TipoDron;

/**
 * Representa un dron destinado a actividades agrícolas.
 *
 * <p>Es una especialización concreta de {@link Dron}: agrega la capacidad
 * del tanque de insumos y se identifica con {@link TipoDron#AGRICULTURA}.</p>
 */
public class Agricultura extends Dron {

    /**
     * Capacidad del tanque del dron, en litros.
     */
    private double capacidadTanque;

    /**
     * Constructor vacío, usado al reconstruir el dron desde la persistencia
     * o desde la vista.
     */
    public Agricultura() {
        super();
    }

    /**
     * Constructor completo.
     *
     * @param id              identificador del dron
     * @param serial          número de serie
     * @param modelo          modelo del dron
     * @param fabricante      fabricante del dron
     * @param peso            peso del dron en kilogramos
     * @param capacidadTanque capacidad del tanque en litros
     */
    public Agricultura(int id, String serial, String modelo,
                       String fabricante, double peso,
                       double capacidadTanque) {
        super(id, serial, modelo, fabricante, peso);
        this.capacidadTanque = capacidadTanque;
    }

    /**
     * Devuelve la capacidad del tanque de insumos.
     *
     * @return capacidad del tanque en litros
     */
    public double getCapacidadTanque() {
        return capacidadTanque;
    }

    /**
     * Cambia la capacidad del tanque de insumos.
     *
     * @param capacidadTanque nueva capacidad del tanque en litros
     */
    public void setCapacidadTanque(double capacidadTanque) {
        this.capacidadTanque = capacidadTanque;
    }

    /**
     * Devuelve los datos básicos del dron agrícola en una línea.
     *
     * @return id, serial, modelo, fabricante, peso y capacidad del tanque
     */
    @Override
    public String toString() {
        return "Agricultura{" +
                "id='" + getId() + '\'' +
                ", serial='" + getSerial() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", fabricante='" + getFabricante() + '\'' +
                ", peso=" + getPeso() +
                ", capacidadTanque=" + capacidadTanque +
                '}';
    }
    /**
     * Identifica este dron como agrícola.
     *
     * @return siempre {@link TipoDron#AGRICULTURA}
     */
    @Override
    public TipoDron getTipo() {
        return TipoDron.AGRICULTURA;
    }

    /**
     * Describe la labor de fumigación del dron, incluyendo la capacidad
     * del tanque.
     *
     * @return descripción operativa con la capacidad del tanque en litros
     */
    @Override
    public String descripcionOperativa() {
        return "Fumigación y dispersión de insumos con tanque de " + capacidadTanque + " L";
    }
}