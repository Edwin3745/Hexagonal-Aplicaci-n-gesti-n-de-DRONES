package co.edu.poli.sw2.aplicacion.puerto.salida;

import co.edu.poli.sw2.dominio.modelo.Dron;

import java.util.List;

/**
 * Puerto de salida: contrato de persistencia que la aplicación necesita para
 * los drones.
 *
 * <p>Lo define la capa de aplicación y lo implementan los adaptadores de
 * salida de la infraestructura. Los servicios dependen de esta interfaz y
 * nunca de una implementación concreta, de modo que cambiar de base de datos
 * no obliga a modificar ni el dominio ni los casos de uso.</p>
 *
 * <p>Ningún método expone detalles técnicos: los fallos se comunican con
 * {@link RepositorioException}, que describe <em>qué</em> salió mal en
 * términos de negocio sin mencionar la tecnología de almacenamiento.</p>
 */
public interface DronRepository {

    /**
     * Guarda un dron nuevo y le asigna el identificador generado.
     *
     * @param dron dron a registrar; al terminar, su {@code id} queda asignado.
     * @throws RepositorioException si no se puede guardar.
     */
    void guardar(Dron dron);

    /**
     * Actualiza un dron existente.
     *
     * @param dron dron con los datos actualizados.
     * @return {@code true} si existía y se actualizó.
     * @throws RepositorioException si no se puede actualizar.
     */
    boolean actualizar(Dron dron);

    /**
     * Elimina un dron por su identificador.
     *
     * @param id identificador del dron.
     * @return {@code true} si existía y se eliminó.
     * @throws RepositorioException si no se puede eliminar.
     */
    boolean eliminar(int id);

    /**
     * Busca un dron por su identificador.
     *
     * @param id identificador del dron.
     * @return el dron encontrado, o {@code null} si no existe.
     * @throws RepositorioException si no se puede consultar.
     */
    Dron buscarPorId(int id);

    /**
     * Obtiene todos los drones registrados.
     *
     * @return lista de drones, vacía si no hay ninguno.
     * @throws RepositorioException si no se puede consultar.
     */
    List<Dron> listarTodos();
}