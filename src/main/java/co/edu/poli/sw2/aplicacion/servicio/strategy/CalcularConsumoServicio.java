package co.edu.poli.sw2.aplicacion.servicio.strategy;

import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.entrada.strategy.CalcularConsumoUseCase;
import co.edu.poli.sw2.aplicacion.puerto.salida.strategy.DistanciaPort;
import co.edu.poli.sw2.aplicacion.puerto.salida.strategy.RutaPort;
import co.edu.poli.sw2.dominio.strategy.ConsumoStrategy;

/**
 * Context del patrón Strategy: guarda la estrategia de consumo elegida y la
 * aplica a la distancia de la ruta, sin saber cuál estrategia concreta es.
 *
 * <p>Es el servicio de aplicación que implementa
 * {@link CalcularConsumoUseCase}. La ruta y la distancia las obtiene por los
 * puertos de salida {@link RutaPort} y {@link DistanciaPort}.</p>
 */
public class CalcularConsumoServicio implements CalcularConsumoUseCase {

    private final RutaPort ruta;
    private final DistanciaPort distancia;
    private ConsumoStrategy estrategia;

    /**
     * Crea el servicio con los puertos de salida que necesita.
     *
     * @param ruta      puerto que entrega el origen y el destino.
     * @param distancia puerto que calcula la distancia entre dos puntos.
     */
    public CalcularConsumoServicio(RutaPort ruta, DistanciaPort distancia) {
        this.ruta = ruta;
        this.distancia = distancia;
    }

    /** {@inheritDoc} */
    @Override
    public void setEstrategia(ConsumoStrategy estrategia) {
        this.estrategia = estrategia;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Cualquier fallo al obtener los puntos o calcular la distancia se
     * traduce a {@link OperacionFallidaException}.</p>
     */
    @Override
    public double calcularDistanciaKm() {
        try {
            return distancia.calcularKm(ruta.obtenerOrigen(), ruta.obtenerDestino());
        } catch (RuntimeException e) {
            throw new OperacionFallidaException("No se pudo leer la ruta del archivo JSON.", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public double calcularConsumo() {
        return estrategia.calcular(calcularDistanciaKm());
    }
}
