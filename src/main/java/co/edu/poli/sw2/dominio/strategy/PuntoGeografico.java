package co.edu.poli.sw2.dominio.strategy;

/**
 * Punto geográfico de una ruta, identificado por un nombre y sus
 * coordenadas en grados.
 *
 * <p>Objeto de valor inmutable: con el origen y el destino de la ruta se
 * calcula la distancia en km a la que luego se aplica la
 * {@link ConsumoStrategy} elegida.</p>
 */
public final class PuntoGeografico {

    private final String nombre;
    private final double latitud;
    private final double longitud;

    /**
     * Crea un punto con su nombre y coordenadas.
     *
     * @param nombre   nombre del punto.
     * @param latitud  latitud en grados.
     * @param longitud longitud en grados.
     */
    public PuntoGeografico(String nombre, double latitud, double longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /**
     * Devuelve el nombre del punto.
     *
     * @return nombre del punto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la latitud del punto.
     *
     * @return latitud en grados.
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Devuelve la longitud del punto.
     *
     * @return longitud en grados.
     */
    public double getLongitud() {
        return longitud;
    }
}
