package co.edu.poli.sw2.aplicacion.puerto.entrada;

import co.edu.poli.sw2.dominio.modelo.Dron;

import java.util.List;

/**
 * Puerto de entrada: caso de uso de consultar todos los drones registrados.
 */
public interface ListarDronesUseCase {

    /**
     * Obtiene todos los drones registrados.
     *
     * @return lista de drones, vacía si no hay ninguno.
     * @throws OperacionFallidaException si la consulta no puede completarse.
     */
    List<Dron> listar();
}
