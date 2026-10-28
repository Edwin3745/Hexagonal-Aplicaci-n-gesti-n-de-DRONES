package co.edu.poli.sw2.dominio.strategy;

/**
 * Modo Ahorro: minimiza el consumo de batería (distancia en km × 0.5).
 */
public class AhorroStrategy implements ConsumoStrategy {

    /** {@inheritDoc} */
    @Override
    public double calcular(double distanciaKm) {
        return distanciaKm * 0.5;
    }

    /** {@inheritDoc} */
    @Override
    public String getNombre() {
        return "Modo Ahorro";
    }
}
