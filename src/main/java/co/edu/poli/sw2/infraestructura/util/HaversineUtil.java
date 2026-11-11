package co.edu.poli.sw2.infraestructura.util;

import co.edu.poli.sw2.aplicacion.puerto.salida.strategy.DistanciaPort;
import co.edu.poli.sw2.dominio.strategy.PuntoGeografico;

/**
 * Adaptador de salida que implementa {@link DistanciaPort} con la fórmula de
 * Haversine, que calcula la distancia sobre la superficie de la Tierra entre
 * dos puntos dados por su latitud y longitud.
 *
 * <p>Toma la Tierra como una esfera de radio medio 6371 km, así que el
 * resultado es una distancia en línea recta (ortodrómica), no por carretera.</p>
 */
public class HaversineUtil implements DistanciaPort {

    /** Radio medio de la Tierra en kilómetros. */
    private static final double RADIO_TIERRA_KM = 6371.0;

    /** {@inheritDoc} */
    @Override
    public double calcularKm(PuntoGeografico origen, PuntoGeografico destino) {
        double lat1 = Math.toRadians(origen.getLatitud());
        double lat2 = Math.toRadians(destino.getLatitud());
        double deltaLat = Math.toRadians(destino.getLatitud() - origen.getLatitud());
        double deltaLon = Math.toRadians(destino.getLongitud() - origen.getLongitud());

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA_KM * c;
    }
}
