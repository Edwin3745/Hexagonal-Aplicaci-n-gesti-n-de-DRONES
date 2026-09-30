package co.edu.poli.sw2.aplicacion.puerto.salida;

/**
 * Error que un adaptador de salida comunica a la aplicación.
 *
 * <p>Forma parte del contrato del puerto {@link DronRepository}. Describe el
 * fallo con un {@link Motivo} de negocio —un duplicado, una regla incumplida—
 * y no con códigos técnicos de la base de datos. Es el adaptador quien conoce
 * la tecnología y traduce sus errores a estos motivos; la aplicación solo ve
 * el resultado de esa traducción.</p>
 *
 * <p>La excepción técnica original se conserva como causa, para que siga
 * disponible al depurar, pero ninguna capa interior necesita conocer su
 * tipo.</p>
 */
public class RepositorioException extends RuntimeException {

    /**
     * Motivos de fallo que el puerto de salida puede comunicar.
     */
    public enum Motivo {
        /** Ya existe un registro con un dato que debe ser único. */
        DUPLICADO,
        /** Los datos no cumplen una regla del almacenamiento. */
        REGLA_INCUMPLIDA,
        /** El registro está vinculado a otros y no puede modificarse. */
        REFERENCIA_INVALIDA,
        /** Fallo técnico sin una causa de negocio identificable. */
        FALLO_TECNICO
    }

    /** Motivo del fallo, expresado en términos de negocio. */
    private final Motivo motivo;

    /**
     * Crea la excepción conservando la causa técnica.
     *
     * @param motivo  motivo del fallo.
     * @param mensaje descripción del fallo.
     * @param causa   excepción técnica original.
     */
    public RepositorioException(Motivo motivo, String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.motivo = motivo;
    }

    /**
     * Crea la excepción sin causa técnica.
     *
     * @param motivo  motivo del fallo.
     * @param mensaje descripción del fallo.
     */
    public RepositorioException(Motivo motivo, String mensaje) {
        super(mensaje);
        this.motivo = motivo;
    }

    /**
     * @return motivo del fallo.
     */
    public Motivo getMotivo() {
        return motivo;
    }

    /**
     * @return {@code true} si el fallo se debe a un dato duplicado.
     */
    public boolean esDuplicado() {
        return motivo == Motivo.DUPLICADO;
    }

    /**
     * @return {@code true} si los datos incumplen una regla del almacenamiento.
     */
    public boolean esViolacionDeRegla() {
        return motivo == Motivo.REGLA_INCUMPLIDA;
    }

    /**
     * @return {@code true} si el registro está vinculado a otros.
     */
    public boolean esReferenciaInvalida() {
        return motivo == Motivo.REFERENCIA_INVALIDA;
    }
}