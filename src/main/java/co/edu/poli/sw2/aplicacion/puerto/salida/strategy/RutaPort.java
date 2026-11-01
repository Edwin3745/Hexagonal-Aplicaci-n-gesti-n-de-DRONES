package co.edu.poli.sw2.aplicacion.puerto.salida.strategy;

import co.edu.poli.sw2.dominio.strategy.PuntoGeografico;

/**
 * Puerto de salida: obtener los puntos de origen y destino de la ruta.
 */
public interface RutaPort {

    /** @return punto de origen de la ruta. */
    PuntoGeografico obtenerOrigen();

    /** @return punto de destino de la ruta. */
    PuntoGeografico obtenerDestino();
}
