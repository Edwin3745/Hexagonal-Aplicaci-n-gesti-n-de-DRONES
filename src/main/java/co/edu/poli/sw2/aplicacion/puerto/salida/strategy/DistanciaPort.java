package co.edu.poli.sw2.aplicacion.puerto.salida.strategy;

import co.edu.poli.sw2.dominio.strategy.PuntoGeografico;

/**
 * Puerto de salida: calcular la distancia entre dos puntos geográficos.
 *
 * <p>Lo usa el Context del patrón Strategy para obtener la distancia de la
 * ruta; lo implementa un adaptador de infraestructura.</p>
 */
public interface DistanciaPort {

    /**
     * Calcula la distancia entre dos puntos.
     *
     * @param origen  punto de origen.
     * @param destino punto de destino.
     * @return distancia entre los dos puntos, en km.
     */
    double calcularKm(PuntoGeografico origen, PuntoGeografico destino);
}
