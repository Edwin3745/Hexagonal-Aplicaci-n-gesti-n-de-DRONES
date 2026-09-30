package co.edu.poli.sw2.infraestructura.persistencia;

import co.edu.poli.sw2.aplicacion.puerto.salida.DronRepository;
import co.edu.poli.sw2.aplicacion.puerto.salida.RepositorioException;
import co.edu.poli.sw2.aplicacion.puerto.salida.RepositorioException.Motivo;
import co.edu.poli.sw2.dominio.modelo.Agricultura;
import co.edu.poli.sw2.dominio.modelo.Dron;
import co.edu.poli.sw2.dominio.enumeracion.TipoDron;
import co.edu.poli.sw2.dominio.modelo.Vigilancia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador de salida: implementación PostgreSQL del puerto
 * {@link DronRepository}.
 *
 * <p>Es la única clase del sistema que sabe que los drones se guardan en una
 * base de datos relacional. Traduce en ambos sentidos: de objetos del dominio a
 * filas de la tabla {@code dron}, y de los errores de JDBC a
 * {@link RepositorioException}, para que ninguna capa interior conozca
 * {@link SQLException}.</p>
 *
 * <p>La herencia se persiste con la estrategia de tabla única con columna
 * discriminadora: todos los subtipos comparten la tabla {@code dron} y la
 * columna {@code tipo} indica de qué subclase es cada fila. Los atributos
 * propios de un subtipo quedan en NULL en las filas de los demás.</p>
 *
 * <p>Todos los métodos trabajan sobre la conexión compartida que entrega
 * {@link ConexionBD}. El repositorio cierra únicamente lo que él mismo abre
 * ({@link PreparedStatement} y {@link ResultSet}); la conexión no se cierra
 * nunca aquí, porque es propiedad del Singleton y las operaciones posteriores
 * la siguen necesitando.</p>
 */
public class PostgresDronRepository implements DronRepository {

    /** Columnas comunes a todos los subtipos, en el orden en que se leen y escriben. */
    private static final String COLUMNAS =
            "id, tipo, serial, modelo, fabricante, peso, capacidad_tanque, deteccion_termica";

    /**
     * Guarda un nuevo dron.
     *
     * <p>El identificador lo genera la base de datos (columna SERIAL), por lo
     * que no se envía en la sentencia y se recupera después para asignarlo al
     * objeto recibido.</p>
     *
     * @param dron dron a registrar.
     */
    @Override
    public void guardar(Dron dron) {
        String sql = "INSERT INTO dron (tipo, serial, modelo, fabricante, peso, "
                   + "capacidad_tanque, deteccion_termica) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // La conexión no entra en el try-with-resources: pertenece al Singleton
        // y cerrarla dejaría inservibles las operaciones siguientes.
        try (PreparedStatement ps = ConexionBD.obtenerConexion()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // El tipo lo declara el propio objeto: no hace falta inspeccionar su clase.
            ps.setString(1, dron.getTipo().getCodigo());
            ps.setString(2, dron.getSerial());
            ps.setString(3, dron.getModelo());
            ps.setString(4, dron.getFabricante());
            ps.setDouble(5, dron.getPeso());

            asignarAtributosEspecificos(ps, dron, 6, 7);

            ps.executeUpdate();

            // Se recupera el id generado por la base y se refleja en el objeto.
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    dron.setId(claves.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw traducir("Error al guardar el dron", e);
        }
    }

    /**
     * Elimina un dron por su identificador.
     *
     * @param id identificador del dron.
     * @return {@code true} si se eliminó alguna fila.
     */
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM dron WHERE id = ?";

        // La conexión no entra en el try-with-resources: pertenece al Singleton
        // y cerrarla dejaría inservibles las operaciones siguientes.
        try (PreparedStatement ps = ConexionBD.obtenerConexion().prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw traducir("Error al eliminar el dron", e);
        }
    }

    /**
     * Busca un dron por su identificador.
     *
     * @param id identificador del dron.
     * @return el dron encontrado, o {@code null} si no existe.
     */
    @Override
    public Dron buscarPorId(int id) {
        String sql = "SELECT " + COLUMNAS + " FROM dron WHERE id = ?";

        // La conexión no entra en el try-with-resources: pertenece al Singleton
        // y cerrarla dejaría inservibles las operaciones siguientes.
        try (PreparedStatement ps = ConexionBD.obtenerConexion().prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearDron(rs);
                }
            }

        } catch (SQLException e) {
            throw traducir("Error al buscar el dron", e);
        }

        return null;
    }

    /**
     * Obtiene todos los drones registrados.
     *
     * @return lista con todos los drones, vacía si no hay ninguno.
     */
    @Override
    public List<Dron> listarTodos() {
        List<Dron> drones = new ArrayList<>();
        String sql = "SELECT " + COLUMNAS + " FROM dron ORDER BY id";

        // La conexión no entra en el try-with-resources: pertenece al Singleton
        // y cerrarla dejaría inservibles las operaciones siguientes.
        try (PreparedStatement ps = ConexionBD.obtenerConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                drones.add(mapearDron(rs));
            }

        } catch (SQLException e) {
            throw traducir("Error al listar drones", e);
        }

        return drones;
    }

    /**
     * Actualiza un dron existente.
     *
     * <p>El tipo también se actualiza: si el dron cambió de subtipo, la fila
     * debe reflejarlo junto con sus atributos específicos.</p>
     *
     * @param dron dron con los datos actualizados.
     * @return {@code true} si se actualizó alguna fila.
     */
    @Override
    public boolean actualizar(Dron dron) {
        String sql = "UPDATE dron SET tipo = ?, serial = ?, modelo = ?, fabricante = ?, "
                   + "peso = ?, capacidad_tanque = ?, deteccion_termica = ? WHERE id = ?";

        // La conexión no entra en el try-with-resources: pertenece al Singleton
        // y cerrarla dejaría inservibles las operaciones siguientes.
        try (PreparedStatement ps = ConexionBD.obtenerConexion().prepareStatement(sql)) {

            ps.setString(1, dron.getTipo().getCodigo());
            ps.setString(2, dron.getSerial());
            ps.setString(3, dron.getModelo());
            ps.setString(4, dron.getFabricante());
            ps.setDouble(5, dron.getPeso());

            asignarAtributosEspecificos(ps, dron, 6, 7);

            ps.setInt(8, dron.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw traducir("Error al actualizar el dron", e);
        }
    }

    // ------------------------------------------------------------------
    // Traducción entre la fila y el objeto
    // ------------------------------------------------------------------

    /**
     * Escribe en la sentencia los atributos que solo existen en un subtipo.
     *
     * <p>Es el único punto del repositorio que necesita distinguir la subclase concreta.
     * La columna que no corresponde al subtipo se marca explícitamente como NULL,
     * tal como exige la restricción de coherencia del esquema.</p>
     *
     * @param ps            sentencia en preparación.
     * @param dron          dron cuyos datos se están escribiendo.
     * @param posTanque     posición del parámetro de capacidad_tanque.
     * @param posTermica    posición del parámetro de deteccion_termica.
     * @throws SQLException si falla la asignación de parámetros.
     */
    private void asignarAtributosEspecificos(PreparedStatement ps, Dron dron,
                                             int posTanque, int posTermica) throws SQLException {

        if (dron instanceof Agricultura agricultura) {
            ps.setDouble(posTanque, agricultura.getCapacidadTanque());
            ps.setNull(posTermica, Types.BOOLEAN);

        } else if (dron instanceof Vigilancia vigilancia) {
            ps.setNull(posTanque, Types.DOUBLE);
            ps.setBoolean(posTermica, vigilancia.isDeteccionTermica());

        } else {
            throw new IllegalStateException(
                    "Subtipo de dron no soportado por la persistencia: "
                            + dron.getClass().getSimpleName());
        }
    }

    /**
     * Convierte la fila actual del {@link ResultSet} en la subclase de
     * {@link Dron} que corresponda.
     *
     * <p>La columna discriminadora {@code tipo} decide qué subclase construir.
     * Solo se lee la columna específica del subtipo elegido: la del otro subtipo
     * es NULL en esa fila.</p>
     *
     * @param rs resultado posicionado en la fila a convertir.
     * @return instancia de {@link Agricultura} o {@link Vigilancia}.
     * @throws SQLException si falla la lectura de la fila.
     */
    private Dron mapearDron(ResultSet rs) throws SQLException {

        TipoDron tipo = TipoDron.desdeCodigo(rs.getString("tipo"));

        int id = rs.getInt("id");
        String serial = rs.getString("serial");
        String modelo = rs.getString("modelo");
        String fabricante = rs.getString("fabricante");
        double peso = rs.getDouble("peso");

        return switch (tipo) {
            case AGRICULTURA -> new Agricultura(id, serial, modelo, fabricante, peso,
                                                rs.getDouble("capacidad_tanque"));
            case VIGILANCIA -> new Vigilancia(id, serial, modelo, fabricante, peso,
                                              rs.getBoolean("deteccion_termica"));
        };
    }

    /**
     * Traduce un error de JDBC al lenguaje del puerto de salida.
     *
     * <p>Aquí, y solo aquí, se interpretan los códigos SQLSTATE de PostgreSQL.
     * La aplicación recibe un {@link Motivo} de negocio y nunca ve el código
     * técnico ni la {@link SQLException}, que se conserva únicamente como
     * causa.</p>
     *
     * @param mensaje descripción de la operación que falló.
     * @param e       error original de JDBC.
     * @return excepción del puerto con el motivo correspondiente.
     */
    private static RepositorioException traducir(String mensaje, SQLException e) {
        String codigo = e.getSQLState();
        Motivo motivo;
        if ("23505".equals(codigo)) {
            motivo = Motivo.DUPLICADO;
        } else if ("23514".equals(codigo) || "23502".equals(codigo)) {
            motivo = Motivo.REGLA_INCUMPLIDA;
        } else if ("23503".equals(codigo)) {
            motivo = Motivo.REFERENCIA_INVALIDA;
        } else {
            motivo = Motivo.FALLO_TECNICO;
        }
        return new RepositorioException(motivo, mensaje, e);
    }
}