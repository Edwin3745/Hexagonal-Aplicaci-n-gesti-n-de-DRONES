package co.edu.poli.sw2.aplicacion.puerto.entrada;

import co.edu.poli.sw2.dominio.modelo.Dron;

/**
 * Puerto de entrada: caso de uso de modificar los datos de un dron existente.
 */
public interface ActualizarDronUseCase {

    /**
     * Actualiza un dron existente.
     *
     * @param dron dron con los datos actualizados.
     * @return {@code true} si el dron existía y se actualizó.
     * @throws OperacionFallidaException si los datos no son válidos o no se
     *                                   puede actualizar.
     */
    boolean actualizar(Dron dron);
}
