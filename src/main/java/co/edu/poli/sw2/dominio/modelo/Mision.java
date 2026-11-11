package co.edu.poli.sw2.dominio.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Misión planificada en una ubicación y fecha, con los drones que participan
 * en ella.
 *
 * <p>Modela la composición Misión → Dron: la misión es el "todo" y los drones
 * las "partes". La lista de drones solo se modifica mediante
 * {@link #agregarDron(Dron)}, {@link #removerDron(Dron)} y
 * {@link #removerDronPorId(int)}.</p>
 */
public class Mision {

    private int id;
    private String nombre;
    private String ubicacion;
    private Date fecha;
    
    // Composición: la Misión es el "todo", los Drones son las "partes".
    // Se inicializa siempre (nunca null) para evitar NullPointerException.
    private final List<Dron> drones = new ArrayList<>();

    /**
     * Crea una misión vacía, sin drones.
     */
    public Mision() {
    }

    /**
     * Crea una misión con sus datos principales y sin drones asignados.
     *
     * @param id        identificador de la misión
     * @param nombre    nombre de la misión
     * @param ubicacion lugar donde se realiza la misión
     * @param fecha     fecha de la misión
     */
    public Mision(int id, String nombre, String ubicacion, Date fecha) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
    }

    /**
     * Devuelve el identificador de la misión.
     *
     * @return identificador de la misión
     */
    public int getId() { return id; }
    /**
     * Cambia el identificador de la misión.
     *
     * @param id nuevo identificador
     */
    public void setId(int id) { this.id = id; }

    /**
     * Devuelve el nombre de la misión.
     *
     * @return nombre de la misión
     */
    public String getNombre() { return nombre; }
    /**
     * Cambia el nombre de la misión.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Devuelve la ubicación de la misión.
     *
     * @return lugar donde se realiza la misión
     */
    public String getUbicacion() { return ubicacion; }
    /**
     * Cambia la ubicación de la misión.
     *
     * @param ubicacion nuevo lugar de la misión
     */
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    /**
     * Devuelve la fecha de la misión.
     *
     * @return fecha de la misión
     */
    public Date getFecha() { return fecha; }
    /**
     * Cambia la fecha de la misión.
     *
     * @param fecha nueva fecha
     */
    public void setFecha(Date fecha) { this.fecha = fecha; }

    // --- Gestión de la composición Mision -> Dron ---

    /**
     * Devuelve una vista no modificable de los drones.
     * Así se obliga a que la única forma de añadir/quitar sea
     * a través de agregarDron/removerDron (encapsula la composición).
     *
     * @return lista de solo lectura con los drones de la misión
     */
    public List<Dron> getDrones() {
        return Collections.unmodifiableList(drones);
    }

    /**
     * Agrega un dron a la misión.
     *
     * @param dron dron que participa en la misión
     * @throws IllegalArgumentException si el dron es nulo
     */
    public void agregarDron(Dron dron) {
        if (dron == null) {
            throw new IllegalArgumentException("El dron no puede ser nulo.");
        }
        drones.add(dron);
    }

    /**
     * Retira un dron de la misión. La comparación usa
     * {@link Dron#equals(Object)}, es decir, el identificador del dron.
     *
     * @param dron dron a retirar
     * @return {@code true} si el dron estaba en la misión y se retiró
     */
    public boolean removerDron(Dron dron) {
        return drones.remove(dron);
    }

    /**
     * Retira de la misión todos los drones con el identificador indicado.
     *
     * @param idDron identificador del dron a retirar
     * @return {@code true} si se retiró al menos un dron
     */
    public boolean removerDronPorId(int idDron) {
        return drones.removeIf(d -> d.getId() == idDron);
    }

    /**
     * Devuelve los datos de la misión en una línea.
     *
     * @return id, nombre, ubicación, fecha y cantidad de drones
     */
    @Override
    public String toString() {
        return "Mision{" + "id=" + id + ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' + ", fecha=" + fecha +
                ", drones=" + drones.size() + '}';
    }
}