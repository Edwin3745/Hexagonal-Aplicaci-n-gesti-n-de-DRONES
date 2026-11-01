package co.edu.poli.sw2.aplicacion.puerto.salida.strategy;

import co.edu.poli.sw2.dominio.strategy.PuntoGeografico;

/**
 * Puerto de salida: calcular la distancia entre dos puntos geográficos.
 */
public interface DistanciaPort {

    /**
     * @param origen  punto de origen.
     * @param destino punto de destino.
     * @return distancia entre los dos puntos, en km.
     */
    double calcularKm(PuntoGeografico origen, PuntoGeografico destino);
}
