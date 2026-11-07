package co.edu.poli.sw2.aplicacion.puerto.entrada.state;

import co.edu.poli.sw2.dominio.modelo.Dron;

/**
 * Puerto de entrada: gestionar el estado operativo de un dron (patrón State).
 *
 * <p>Cada operación devuelve el nombre del nuevo estado. Si la operación no
 * está permitida en el estado actual se lanza
 * {@link co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException}.</p>
 */
public interface ManageDroneStateUseCase {

    String encender(Dron dron);

    String apagar(Dron dron);

    String despegar(Dron dron);

    String aterrizar(Dron dron);

    String finalizarAterrizaje(Dron dron);

    /** @return nombre del estado actual del dron (APAGADO si nunca se ha operado). */
    String consultarEstado(Dron dron);
}
