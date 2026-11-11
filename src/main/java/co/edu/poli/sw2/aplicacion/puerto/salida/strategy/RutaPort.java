package co.edu.poli.sw2.aplicacion.puerto.salida.strategy;

import co.edu.poli.sw2.dominio.strategy.PuntoGeografico;

/**
 * Puerto de salida: obtener los puntos de origen y destino de la ruta.
 *
 * <p>Lo usa el Context del patrón Strategy; lo implementa un adaptador de
 * infraestructura que sabe de dónde se lee la ruta.</p>
 */
public interface RutaPort {

    /**
     * Obtiene el punto de partida de la ruta.
     *
     * @return punto de origen de la ruta.
     */
    PuntoGeografico obtenerOrigen();

    /**
     * Obtiene el punto de llegada de la ruta.
     *
     * @return punto de destino de la ruta.
     */
    PuntoGeografico obtenerDestino();
}
