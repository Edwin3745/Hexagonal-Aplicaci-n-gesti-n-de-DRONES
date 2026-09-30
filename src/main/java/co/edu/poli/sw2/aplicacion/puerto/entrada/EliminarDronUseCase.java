package co.edu.poli.sw2.aplicacion.puerto.entrada;

/**
 * Puerto de entrada: caso de uso de dar de baja un dron.
 */
public interface EliminarDronUseCase {

    /**
     * Elimina el dron con el identificador indicado.
     *
     * @param id identificador del dron.
     * @return {@code true} si el dron existía y se eliminó.
     * @throws OperacionFallidaException si no se puede eliminar.
     */
    boolean eliminar(int id);
}
