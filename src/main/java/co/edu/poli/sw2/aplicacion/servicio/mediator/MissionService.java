package co.edu.poli.sw2.aplicacion.servicio.mediator;

import co.edu.poli.sw2.aplicacion.mediator.DroneMediator;
import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.entrada.mediator.ExecuteMissionUseCase;
import co.edu.poli.sw2.dominio.command.ResultadoTarea;
import co.edu.poli.sw2.dominio.modelo.Dron;

import java.util.List;

/**
 * Servicio de aplicación y Client del patrón Mediator: implementa el caso de
 * uso {@link ExecuteMissionUseCase} y le delega al mediador toda la
 * coordinación. No usa el repositorio de drones (no hay CRUD).
 */
public class MissionService implements ExecuteMissionUseCase {

    private final DroneMediator mediator;

    /**
     * Crea el servicio con el mediador que coordinará las misiones.
     *
     * @param mediator mediador que coordina la misión.
     * @throws IllegalArgumentException si {@code mediator} es nulo.
     */
    public MissionService(DroneMediator mediator) {
        if (mediator == null) {
            throw new IllegalArgumentException("El mediador es obligatorio.");
        }
        this.mediator = mediator;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Comprueba que haya un dron seleccionado y delega la misión en
     * {@link DroneMediator#iniciarMision(Dron)}.</p>
     *
     * @throws OperacionFallidaException si {@code dron} es nulo.
     */
    @Override
    public List<ResultadoTarea> executeMission(Dron dron) {
        if (dron == null) {
            throw new OperacionFallidaException("Selecciona un dron de la tabla para iniciar la misión.");
        }
        return mediator.iniciarMision(dron);
    }
}
