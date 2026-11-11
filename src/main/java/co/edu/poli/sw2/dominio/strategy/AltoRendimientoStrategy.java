package co.edu.poli.sw2.dominio.strategy;

/**
 * Modo Alto Rendimiento: aumenta la velocidad sacrificando batería (distancia en km × 1.5).
 *
 * <p>Estrategia concreta del patrón Strategy que implementa
 * {@link ConsumoStrategy}.</p>
 */
public class AltoRendimientoStrategy implements ConsumoStrategy {

    /** {@inheritDoc} */
    @Override
    public double calcular(double distanciaKm) {
        return distanciaKm * 1.5;
    }

    /** {@inheritDoc} */
    @Override
    public String getNombre() {
        return "Modo Alto Rendimiento";
    }
}
