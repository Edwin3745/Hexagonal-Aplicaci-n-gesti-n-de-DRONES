package co.edu.poli.sw2.dominio.strategy;

/**
 * Estrategia de consumo energético del patrón Strategy.
 *
 * <p>Es la interfaz Strategy: cada implementación estima de forma distinta
 * el porcentaje de batería que un dron necesita para recorrer una distancia.
 * Las estrategias concretas son {@link AhorroStrategy} (× 0.5),
 * {@link NormalStrategy} (× 1.0) y {@link AltoRendimientoStrategy} (× 1.5).</p>
 *
 * <p>El contexto ({@code CalcularConsumoServicio}, en la capa de aplicación)
 * guarda la estrategia elegida y delega en ella el cálculo, sin saber cuál
 * estrategia concreta es; por eso se pueden agregar modos nuevos sin
 * modificarlo.</p>
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
     * Devuelve el nombre legible de la estrategia.
     *
     * @return nombre de la estrategia, para mostrarlo en pantalla.
     */
    String getNombre();
}
