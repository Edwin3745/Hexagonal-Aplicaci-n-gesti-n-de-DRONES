package co.edu.poli.sw2.aplicacion.puerto.entrada.strategy;

import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.dominio.strategy.ConsumoStrategy;

/**
 * Puerto de entrada: estimar la batería que un dron necesita para recorrer
 * la ruta entre el origen y el destino.
 */
public interface CalcularConsumoUseCase {

    /**
     * Define la estrategia de consumo que se aplicará en el cálculo.
     *
     * @param estrategia estrategia de consumo elegida.
     */
    void setEstrategia(ConsumoStrategy estrategia);

    /**
     * @return distancia entre el origen y el destino de la ruta, en km.
     * @throws OperacionFallidaException si no se puede leer la ruta.
     */
    double calcularDistanciaKm();

    /**
     * Aplica la estrategia definida a la distancia de la ruta.
     *
     * @return batería requerida en porcentaje.
     * @throws OperacionFallidaException si no se puede leer la ruta.
     */
    double calcularConsumo();
}
