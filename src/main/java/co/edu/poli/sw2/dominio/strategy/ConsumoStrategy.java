package co.edu.poli.sw2.dominio.strategy;

/**
 * Estrategia de consumo energético del patrón Strategy.
 *
 * <p>Cada implementación estima de forma distinta el porcentaje de batería
 * que un dron necesita para recorrer una distancia.</p>
 */
public interface ConsumoStrategy {

    /**
     * Calcula la batería requerida para recorrer la distancia.
     *
     * @param distanciaKm distancia del recorrido en kilómetros.
     * @return batería requerida en porcentaje.
     */
    double calcular(double distanciaKm);

    /**
     * @return nombre de la estrategia, para mostrarlo en pantalla.
     */
    String getNombre();
}
