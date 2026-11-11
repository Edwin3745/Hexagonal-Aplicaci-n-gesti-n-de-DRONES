package co.edu.poli.sw2.aplicacion.puerto.entrada.state;

import co.edu.poli.sw2.dominio.modelo.Dron;

/**
 * Puerto de entrada: gestionar el estado operativo de un dron (patrón State).
 *
 * <p>Cada operación devuelve el nombre del nuevo estado. Si la operación no
 * está permitida en el estado actual se lanza
 * {@link co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException}.</p>
 *
 * <p>Ciclo operativo: APAGADO → (encender) → EN ESPERA → (despegar) →
 * VOLANDO → (aterrizar) → ATERRIZANDO → (finalizar aterrizaje) → EN ESPERA;
 * desde EN ESPERA también se puede apagar.</p>
 */
public interface ManageDroneStateUseCase {

    /**
     * Enciende el dron: pasa de APAGADO a EN ESPERA.
     *
     * @param dron dron sobre el que se aplica la operación.
     * @return nombre del nuevo estado del dron.
     * @throws co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException
     *         si el dron es nulo o la operación no está permitida en su
     *         estado actual.
     */
    String encender(Dron dron);

    /**
     * Apaga el dron: pasa de EN ESPERA a APAGADO.
     *
     * @param dron dron sobre el que se aplica la operación.
     * @return nombre del nuevo estado del dron.
     * @throws co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException
     *         si el dron es nulo o la operación no está permitida en su
     *         estado actual.
     */
    String apagar(Dron dron);

    /**
     * Hace despegar el dron: pasa de EN ESPERA a VOLANDO.
     *
     * @param dron dron sobre el que se aplica la operación.
     * @return nombre del nuevo estado del dron.
     * @throws co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException
     *         si el dron es nulo o la operación no está permitida en su
     *         estado actual.
     */
    String despegar(Dron dron);

    /**
     * Inicia el aterrizaje: pasa de VOLANDO a ATERRIZANDO.
     *
     * @param dron dron sobre el que se aplica la operación.
     * @return nombre del nuevo estado del dron.
     * @throws co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException
     *         si el dron es nulo o la operación no está permitida en su
     *         estado actual.
     */
    String aterrizar(Dron dron);

    /**
     * Completa el aterrizaje: pasa de ATERRIZANDO a EN ESPERA.
     *
     * @param dron dron sobre el que se aplica la operación.
     * @return nombre del nuevo estado del dron.
     * @throws co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException
     *         si el dron es nulo o la operación no está permitida en su
     *         estado actual.
     */
    String finalizarAterrizaje(Dron dron);

    /**
     * Consulta el estado operativo actual del dron sin modificarlo.
     *
     * @param dron dron a consultar.
     * @return nombre del estado actual del dron (APAGADO si nunca se ha operado).
     * @throws co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException
     *         si el dron es nulo.
     */
    String consultarEstado(Dron dron);
}
