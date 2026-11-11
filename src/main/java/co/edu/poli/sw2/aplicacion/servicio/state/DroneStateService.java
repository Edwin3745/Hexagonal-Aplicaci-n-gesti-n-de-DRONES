package co.edu.poli.sw2.aplicacion.servicio.state;

import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.entrada.state.ManageDroneStateUseCase;
import co.edu.poli.sw2.dominio.modelo.Dron;
import co.edu.poli.sw2.dominio.state.DroneContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Servicio de aplicación del patrón State: implementa
 * {@link ManageDroneStateUseCase} delegando cada operación en el
 * {@link DroneContext} del dron.
 *
 * <p>Guarda en memoria un {@link DroneContext} por id de dron, para que el
 * estado no se pierda cuando la tabla se recarga desde la BD. El primer uso
 * de un dron crea su contexto, que inicia en APAGADO.</p>
 *
 * <p>Cuando el estado actual rechaza una operación, el dominio lanza
 * {@link IllegalStateException}; este servicio la traduce a
 * {@link OperacionFallidaException} para que la interfaz muestre el
 * mensaje.</p>
 */
public class DroneStateService implements ManageDroneStateUseCase {

    private final Map<Integer, DroneContext> contextos = new HashMap<>();

    /** {@inheritDoc} */
    @Override
    public String encender(Dron dron) {
        return ejecutar(dron, DroneContext::encender);
    }

    /** {@inheritDoc} */
    @Override
    public String apagar(Dron dron) {
        return ejecutar(dron, DroneContext::apagar);
    }

    /** {@inheritDoc} */
    @Override
    public String despegar(Dron dron) {
        return ejecutar(dron, DroneContext::despegar);
    }

    /** {@inheritDoc} */
    @Override
    public String aterrizar(Dron dron) {
        return ejecutar(dron, DroneContext::aterrizar);
    }

    /** {@inheritDoc} */
    @Override
    public String finalizarAterrizaje(Dron dron) {
        return ejecutar(dron, DroneContext::finalizarAterrizaje);
    }

    /** {@inheritDoc} */
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
