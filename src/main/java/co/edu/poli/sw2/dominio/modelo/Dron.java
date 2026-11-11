package co.edu.poli.sw2.dominio.modelo;

import co.edu.poli.sw2.dominio.enumeracion.TipoDron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa la entidad base del sistema de gestión de drones.
 *
 * <p>Dron es una clase abstracta que contiene los atributos comunes
 * de los diferentes tipos de drones y declara el comportamiento que
 * cada subtipo concreto debe definir ({@link #getTipo()} y
 * {@link #descripcionOperativa()}).</p>
 *
 * <p>Las clases {@link Agricultura} y {@link Vigilancia} heredan de esta
 * clase. Además, el dron mantiene una asociación bidireccional con
 * {@link Piloto} y una composición con sus {@link Sensor}es.</p>
 */
public abstract class Dron {

    /** Identificador único del dron. Clave técnica, corresponde a la columna id de la BD. */
    private int id;

    /** Número de serie del dron. Identificador del mundo real, ej: "AGR-001". */
    private String serial;

    /** Modelo del dron. */
    private String modelo;

    /** Fabricante del dron. */
    private String fabricante;

    /** Peso del dron en kilogramos. */
    private double peso;

    /** Piloto asignado al dron. */
    private Piloto piloto;

    /** Sensores montados en el dron. */
    private final List<Sensor> sensores = new ArrayList<>();

    /**
     * Constructor vacío para reconstrucción desde la capa de persistencia.
     *
     * <p>Es protected porque solo las subclases deben poder invocarlo:
     * en el dominio no existe un dron sin tipo.</p>
     */
    protected Dron() {
    }

    /**
     * Constructor con los atributos comunes.
     *
     * @param id identificador del dron
     * @param serial número de serie
     * @param modelo modelo del dron
     * @param fabricante fabricante del dron
     * @param peso peso del dron en kilogramos
     */
    protected Dron(int id, String serial, String modelo,
                   String fabricante, double peso) {
        this.id = id;
        this.serial = serial;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.peso = peso;
    }

    // ------------------------------------------------------------------
    // Comportamiento que cada subtipo debe definir
    // ------------------------------------------------------------------

    /**
     * Identifica el subtipo concreto del dron.
     *
     * <p>El adaptador de persistencia usa este valor para escribir la columna
     * discriminadora, por lo que cada subclase está obligada a declararlo.</p>
     *
     * @return tipo concreto de este dron
     */
    public abstract TipoDron getTipo();

    /**
     * Describe la función operativa del dron en lenguaje de negocio.
     *
     * <p>Permite recorrer una colección heterogénea de drones y obtener la
     * descripción correcta de cada uno sin inspeccionar su clase.</p>
     *
     * @return descripción legible de la misión que cumple el equipo
     */
    public abstract String descripcionOperativa();

    // ------------------------------------------------------------------
    // Atributos comunes
    // ------------------------------------------------------------------

    /**
     * Devuelve el identificador único del dron.
     *
     * @return identificador del dron
     */
    public int getId() {
        return id;
    }

    /**
     * Cambia el identificador del dron.
     *
     * @param id nuevo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el número de serie del dron.
     *
     * @return serial del dron
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Cambia el número de serie del dron.
     *
     * @param serial nuevo serial
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * Devuelve el modelo del dron.
     *
     * @return modelo del dron
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Cambia el modelo del dron.
     *
     * @param modelo nuevo modelo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Devuelve el fabricante del dron.
     *
     * @return fabricante del dron
     */
    public String getFabricante() {
        return fabricante;
    }

    /**
     * Cambia el fabricante del dron.
     *
     * @param fabricante nuevo fabricante
     */
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    /**
     * Devuelve el peso del dron.
     *
     * @return peso en kilogramos
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Cambia el peso del dron, rechazando valores negativos.
     *
     * @param peso peso en kilogramos
     * @throws IllegalArgumentException si el peso es negativo
     */
    public void setPeso(double peso) {
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo.");
        }
        this.peso = peso;
    }

    // ------------------------------------------------------------------
    // Reglas de negocio
    // ------------------------------------------------------------------

    /**
     * Comprueba que el dron cumple las reglas básicas del negocio: serial,
     * modelo y fabricante obligatorios (no vacíos) y peso no negativo.
     *
     * <p>Las reglas viven en el dominio y no en los servicios ni en la vista:
     * así se aplican igual sin importar desde dónde se registre o se
     * modifique un dron.</p>
     *
     * @throws IllegalArgumentException con un mensaje que indica la regla
     *                                  incumplida, si alguna no se cumple.
     */
    public void validar() {
        if (serial == null || serial.isBlank()) {
            throw new IllegalArgumentException("El serial es obligatorio.");
        }
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("El modelo es obligatorio.");
        }
        if (fabricante == null || fabricante.isBlank()) {
            throw new IllegalArgumentException("El fabricante es obligatorio.");
        }
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo.");
        }
    }

    // ------------------------------------------------------------------
    // Asociación bidireccional con Piloto
    // ------------------------------------------------------------------

    /**
     * Devuelve el piloto asignado al dron.
     *
     * @return piloto asignado, o {@code null} si no tiene ninguno
     */
    public Piloto getPiloto() {
        return piloto;
    }

    /**
     * Visibilidad de paquete a propósito: la relación solo puede modificarse
     * mediante {@link Piloto#asignarDron(Dron)} y {@link Piloto#liberarDron()},
     * que mantienen ambos extremos sincronizados.
     *
     * @param piloto piloto asignado, o {@code null} para dejarlo sin piloto
     */
    void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    /**
     * Indica si el dron tiene un piloto asignado.
     *
     * @return {@code true} si tiene piloto; {@code false} en caso contrario
     */
    public boolean tienePilotoAsignado() {
        return piloto != null;
    }

    // ------------------------------------------------------------------
    // Composición Dron -> Sensor
    // ------------------------------------------------------------------

    /**
     * Devuelve los sensores montados en el dron.
     *
     * <p>La lista es de solo lectura: los sensores solo se agregan o retiran
     * con {@link #agregarSensor(Sensor)} y {@link #removerSensor(Sensor)}.</p>
     *
     * @return vista no modificable de los sensores montados
     */
    public List<Sensor> getSensores() {
        return Collections.unmodifiableList(sensores);
    }

    /**
     * Monta un sensor en el dron.
     *
     * @param sensor sensor a montar
     * @throws IllegalArgumentException si el sensor es nulo
     */
    public void agregarSensor(Sensor sensor) {
        if (sensor == null) {
            throw new IllegalArgumentException("El sensor no puede ser nulo.");
        }
        sensores.add(sensor);
    }

    /**
     * Retira un sensor del dron.
     *
     * @param sensor sensor a retirar
     * @return {@code true} si el sensor estaba montado y se retiró
     */
    public boolean removerSensor(Sensor sensor) {
        return sensores.remove(sensor);
    }

    // ------------------------------------------------------------------
    // Identidad y representación
    // ------------------------------------------------------------------

    /**
     * Dos drones son el mismo si comparten identificador, sin importar si son
     * instancias distintas en memoria.
     *
     * @param obj objeto a comparar
     * @return {@code true} si {@code obj} es un dron con el mismo identificador
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Dron)) return false;
        return this.id == ((Dron) obj).id;
    }

    /**
     * Calcula el hash a partir del identificador, coherente con
     * {@link #equals(Object)}.
     *
     * @return código hash del dron
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Arma el bloque de texto con los datos comunes del dron: id, tipo,
     * serial, modelo, fabricante, peso, id del piloto y cantidad de sensores.
     *
     * <p>Lo usa {@link #toString()} y queda disponible para que las subclases
     * lo reutilicen en su propia representación.</p>
     *
     * @return datos comunes del dron separados por comas
     */
    protected String datosBase() {
        return "id=" + id +
                ", tipo=" + getTipo().getCodigo() +
                ", serial='" + serial + '\'' +
                ", modelo='" + modelo + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", peso=" + peso +
                ", piloto=" + (piloto != null ? piloto.getId() : "sin asignar") +
                ", sensores=" + sensores.size();
    }

    /**
     * Devuelve el nombre de la clase concreta seguido de los datos comunes.
     *
     * @return nombre simple de la clase y los datos de {@link #datosBase()} entre llaves
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + datosBase() + "}";
    }
}
