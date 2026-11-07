package co.edu.poli.sw2.aplicacion.servicio.state;

import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.entrada.state.ManageDroneStateUseCase;
import co.edu.poli.sw2.dominio.modelo.Dron;
import co.edu.poli.sw2.dominio.state.DroneContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Servicio de aplicación del patrón State.
 *
 * <p>Guarda en memoria un {@link DroneContext} por id de dron, para que el
 * estado no se pierda cuando la tabla se recarga desde la BD. El primer uso
 * de un dron crea su contexto, que inicia en APAGADO.</p>
 */
public class DroneStateService implements ManageDroneStateUseCase {

    private final Map<Integer, DroneContext> contextos = new HashMap<>();

    @Override
    public String encender(Dron dron) {
        return ejecutar(dron, DroneContext::encender);
    }

    @Override
    public String apagar(Dron dron) {
        return ejecutar(dron, DroneContext::apagar);
    }

    @Override
    public String despegar(Dron dron) {
        return ejecutar(dron, DroneContext::despegar);
    }

    @Override
    public String aterrizar(Dron dron) {
        return ejecutar(dron, DroneContext::aterrizar);
    }

    @Override
    public String finalizarAterrizaje(Dron dron) {
        return ejecutar(dron, DroneContext::finalizarAterrizaje);
    }

    @Override
    public String consultarEstado(Dron dron) {
        return contextoDe(dron).getEstado().getNombre();
    }

    /**
     * Delega la operación en el contexto y traduce el rechazo del estado
     * (IllegalStateException del dominio) a una excepción de aplicación.
     */
    private String ejecutar(Dron dron, Consumer<DroneContext> operacion) {
        DroneContext contexto = contextoDe(dron);
        try {
            operacion.accept(contexto);
        } catch (IllegalStateException e) {
            throw new OperacionFallidaException(e.getMessage(), e);
        }
        return contexto.getEstado().getNombre();
    }

    private DroneContext contextoDe(Dron dron) {
        if (dron == null) {
            throw new OperacionFallidaException("Selecciona un dron de la tabla.");
        }
        return contextos.computeIfAbsent(dron.getId(), id -> new DroneContext(dron));
    }
}
