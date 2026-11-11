package co.edu.poli.sw2.dominio.chain;

/**
 * Resultado que devuelve la cadena de responsabilidad al evaluar el clima.
 *
 * <p>Indica si el clima es apto para operar drones y, cuando no lo es, qué
 * regla se incumplió y por qué. Es inmutable y solo se crea con las fábricas
 * {@link #apto()} y {@link #noApto(String, String)}.</p>
 *
 * @see WeatherHandler#handle(co.edu.poli.sw2.dominio.weather.Weather)
 */
public final class ResultadoViabilidad {

    /** Mensaje que se muestra cuando todas las reglas se cumplen. */
    public static final String MENSAJE_APTO = "CLIMA APTO PARA OPERACIÓN DE DRONES";
    /** Mensaje base cuando alguna regla se incumple; {@link #getMensaje()} le añade el motivo. */
    public static final String MENSAJE_NO_APTO = "CLIMA NO APTO PARA OPERACIÓN DE DRONES";

    private final boolean apto;
    private final String reglaIncumplida;
    private final String motivo;

    private ResultadoViabilidad(boolean apto, String reglaIncumplida, String motivo) {
        this.apto = apto;
        this.reglaIncumplida = reglaIncumplida;
        this.motivo = motivo;
    }

    /**
     * Crea un resultado apto, sin regla incumplida ni motivo.
     *
     * @return resultado con {@link #isApto()} en {@code true}.
     */
    public static ResultadoViabilidad apto() {
        return new ResultadoViabilidad(true, null, null);
    }

    /**
     * Crea un resultado no apto.
     *
     * @param reglaIncumplida nombre de la regla que cortó la cadena.
     * @param motivo          explicación del incumplimiento, con el valor medido y el límite.
     * @return resultado con {@link #isApto()} en {@code false}.
     */
    public static ResultadoViabilidad noApto(String reglaIncumplida, String motivo) {
        return new ResultadoViabilidad(false, reglaIncumplida, motivo);
    }

    /**
     * Indica si el clima permite operar drones.
     *
     * @return {@code true} si todas las reglas se cumplieron.
     */
    public boolean isApto() { return apto; }
    /**
     * Nombre de la regla que se incumplió.
     *
     * @return nombre de la regla, o {@code null} si el resultado es apto.
     */
    public String getReglaIncumplida() { return reglaIncumplida; }
    /**
     * Explicación del incumplimiento.
     *
     * @return motivo del rechazo, o {@code null} si el resultado es apto.
     */
    public String getMotivo() { return motivo; }

    /**
     * Mensaje listo para mostrar al usuario.
     *
     * @return {@link #MENSAJE_APTO} si es apto; si no, {@link #MENSAJE_NO_APTO}
     *         seguido de {@code ": "} y el motivo.
     */
    public String getMensaje() {
        return apto ? MENSAJE_APTO : MENSAJE_NO_APTO + ": " + motivo;
    }

    /**
     * Representación textual del resultado.
     *
     * @return lo mismo que {@link #getMensaje()}.
     */
    @Override
    public String toString() {
        return getMensaje();
    }
}