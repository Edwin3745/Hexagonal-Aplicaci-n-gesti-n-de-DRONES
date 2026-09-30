package co.edu.poli.sw2.aplicacion.puerto.entrada;

import co.edu.poli.sw2.dominio.modelo.Dron;

/**
 * Puerto de entrada: caso de uso de registrar un dron nuevo.
 *
 * <p>Es el contrato que el adaptador de entrada invoca, sin conocer qué
 * servicio lo implementa ni cómo se guarda el dron.</p>
 */
public interface RegistrarDronUseCase {

    /**
     * Registra un dron nuevo.
     *
     * @param dron dron a registrar; al terminar, su identificador queda asignado.
     * @throws OperacionFallidaException si los datos no son válidos o no se
     *                                   puede guardar.
     */
    void registrar(Dron dron);
}
