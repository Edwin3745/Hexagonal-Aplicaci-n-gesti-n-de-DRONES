package co.edu.poli.sw2.aplicacion.puerto.entrada;

import co.edu.poli.sw2.dominio.modelo.Dron;

/**
 * Puerto de entrada: caso de uso de consultar un único dron por su
 * identificador.
 */
public interface ListarDronUseCase {

    /**
     * Obtiene el dron con el identificador indicado.
     *
     * @param id identificador del dron.
     * @return el dron encontrado, o {@code null} si no existe.
     * @throws OperacionFallidaException si la consulta no puede completarse.
     */
    Dron listarPorId(int id);
}
