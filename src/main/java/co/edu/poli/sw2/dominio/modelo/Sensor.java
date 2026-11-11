package co.edu.poli.sw2.dominio.modelo;

/**
 * Sensor que puede montarse en un {@link Dron}.
 *
 * <p>Forma parte de la composición Dron → Sensor: el dron mantiene su lista
 * de sensores y solo se modifica mediante {@link Dron#agregarSensor(Sensor)}
 * y {@link Dron#removerSensor(Sensor)}.</p>
 */
public class Sensor {

    private int id;
    private String tipo;
    private String fabricante;

    /**
     * Crea un sensor vacío.
     */
    public Sensor() {
    }

    /**
     * Crea un sensor con todos sus datos.
     *
     * @param id         identificador del sensor
     * @param tipo       tipo de sensor
     * @param fabricante fabricante del sensor
     */
    public Sensor(int id, String tipo, String fabricante) {
        this.id = id;
        this.tipo = tipo;
        this.fabricante = fabricante;
    }

    /**
     * Devuelve el identificador del sensor.
     *
     * @return identificador del sensor
     */
    public int getId() { return id; }
    /**
     * Cambia el identificador del sensor.
     *
     * @param id nuevo identificador
     */
    public void setId(int id) { this.id = id; }

    /**
     * Devuelve el tipo de sensor.
     *
     * @return tipo de sensor
     */
    public String getTipo() { return tipo; }
    /**
     * Cambia el tipo de sensor.
     *
     * @param tipo nuevo tipo de sensor
     */
    public void setTipo(String tipo) { this.tipo = tipo; }

    /**
     * Devuelve el fabricante del sensor.
     *
     * @return fabricante del sensor
     */
    public String getFabricante() { return fabricante; }
    /**
     * Cambia el fabricante del sensor.
     *
     * @param fabricante nuevo fabricante
     */
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    /**
     * Devuelve los datos del sensor en una línea.
     *
     * @return id, tipo y fabricante del sensor
     */
    @Override
    public String toString() {
        return "Sensor{" + "id=" + id + ", tipo='" + tipo + '\'' +
                ", fabricante='" + fabricante + '\'' + '}';
    }
}