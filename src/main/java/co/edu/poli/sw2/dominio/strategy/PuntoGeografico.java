package co.edu.poli.sw2.dominio.strategy;

/**
 * Punto geográfico de una ruta, identificado por un nombre y sus
 * coordenadas en grados.
 */
public final class PuntoGeografico {

    private final String nombre;
    private final double latitud;
    private final double longitud;

    /**
     * @param nombre   nombre del punto.
     * @param latitud  latitud en grados.
     * @param longitud longitud en grados.
     */
    public PuntoGeografico(String nombre, double latitud, double longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /** @return nombre del punto. */
    public String getNombre() {
        return nombre;
    }

    /** @return latitud en grados. */
    public double getLatitud() {
        return latitud;
    }

    /** @return longitud en grados. */
    public double getLongitud() {
        return longitud;
    }
}
