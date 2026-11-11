package co.edu.poli.sw2.dominio.strategy;

/**
 * Modo Normal: consumo estándar (distancia en km × 1).
 *
 * <p>Estrategia concreta del patrón Strategy que implementa
 * {@link ConsumoStrategy}.</p>
 */
public class NormalStrategy implements ConsumoStrategy {

    /** {@inheritDoc} */
    @Override
    public double calcular(double distanciaKm) {
        return distanciaKm * 1.0;
    }

    /** {@inheritDoc} */
    @Override
    public String getNombre() {
        return "Modo Normal";
    }
}
