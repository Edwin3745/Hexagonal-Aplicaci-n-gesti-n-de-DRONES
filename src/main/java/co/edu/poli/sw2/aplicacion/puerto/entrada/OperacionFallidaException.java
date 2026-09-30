package co.edu.poli.sw2.aplicacion.puerto.entrada;

import co.edu.poli.sw2.aplicacion.puerto.salida.RepositorioException;

/**
 * Error que los casos de uso comunican a quien los invoca, con un mensaje
 * redactado para mostrarse directamente al usuario.
 *
 * <p>Forma parte del contrato de los puertos de entrada: el adaptador de
 * entrada solo necesita capturar este tipo y presentar {@link #getMessage()}.
 * No tiene que conocer los errores del repositorio ni, menos aún, los de la
 * base de datos.</p>
 */
public class OperacionFallidaException extends RuntimeException {

    /**
     * @param mensajeParaElUsuario mensaje listo para mostrarse.
     */
    public OperacionFallidaException(String mensajeParaElUsuario) {
        super(mensajeParaElUsuario);
    }

    /**
     * @param mensajeParaElUsuario mensaje listo para mostrarse.
     * @param causa                error original, conservado para depurar.
     */
    public OperacionFallidaException(String mensajeParaElUsuario, Throwable causa) {
        super(mensajeParaElUsuario, causa);
    }

    /**
     * Traduce un fallo del repositorio a un mensaje comprensible.
     *
     * <p>Centraliza la traducción para que todos los servicios respondan igual
     * ante el mismo motivo de fallo. El error original se conserva como causa,
     * pero su texto técnico nunca llega a la interfaz.</p>
     *
     * @param e          fallo comunicado por el puerto de salida.
     * @param operacion  verbo de la operación que se intentaba, para el mensaje.
     * @param referencia dato que identifica el registro implicado; puede ser nulo.
     * @return excepción con un mensaje apto para el usuario.
     */
    public static OperacionFallidaException porFalloDeRepositorio(
            RepositorioException e, String operacion, String referencia) {

        if (e.esDuplicado()) {
            return new OperacionFallidaException(
                    "Ya existe un dron con el serial " + referencia + ".", e);
        }
        if (e.esViolacionDeRegla()) {
            return new OperacionFallidaException(
                    "Los datos del dron no cumplen las reglas del sistema. "
                    + "Revisa que el tipo y sus atributos correspondan.", e);
        }
        if (e.esReferenciaInvalida()) {
            return new OperacionFallidaException(
                    "El dron está asociado a otros registros y no puede modificarse.", e);
        }
        return new OperacionFallidaException(
                "No se pudo " + operacion + " el dron. "
                + "Verifica la conexión con la base de datos.", e);
    }
}
