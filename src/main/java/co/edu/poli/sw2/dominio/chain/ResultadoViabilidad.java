package co.edu.poli.sw2.dominio.chain;

public final class ResultadoViabilidad {

    public static final String MENSAJE_APTO = "CLIMA APTO PARA OPERACIÓN DE DRONES";
    public static final String MENSAJE_NO_APTO = "CLIMA NO APTO PARA OPERACIÓN DE DRONES";

    private final boolean apto;
    private final String reglaIncumplida;
    private final String motivo;

    private ResultadoViabilidad(boolean apto, String reglaIncumplida, String motivo) {
        this.apto = apto;
        this.reglaIncumplida = reglaIncumplida;
        this.motivo = motivo;
    }

    public static ResultadoViabilidad apto() {
        return new ResultadoViabilidad(true, null, null);
    }

    public static ResultadoViabilidad noApto(String reglaIncumplida, String motivo) {
        return new ResultadoViabilidad(false, reglaIncumplida, motivo);
    }

    public boolean isApto() { return apto; }
    public String getReglaIncumplida() { return reglaIncumplida; }
    public String getMotivo() { return motivo; }

    public String getMensaje() {
        return apto ? MENSAJE_APTO : MENSAJE_NO_APTO + ": " + motivo;
    }

    @Override
    public String toString() {
        return getMensaje();
    }
}