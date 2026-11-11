package co.edu.poli.sw2.aplicacion.mediator;

import co.edu.poli.sw2.dominio.command.ResultadoTarea;
import co.edu.poli.sw2.dominio.modelo.Dron;

import java.util.List;

/**
 * Mediator del patrón GoF: define cómo se comunican los componentes de una
 * operación de drones sin que se conozcan entre sí.
 *
 * <p>Los componentes (Colleagues) de {@code mediator.components} solo
 * conocen esta interfaz: cuando terminan su tarea llaman a
 * {@link #notify(Object, MissionEvent)} y es el mediador quien decide el
 * siguiente paso. La implementación concreta es {@link MissionCoordinator}.</p>
 *
 * <p>Vive en la capa de aplicación: no depende de JavaFX ni de la
 * infraestructura.</p>
 */
public interface DroneMediator {

    /**
     * Un componente avisa al mediador de que ocurrió algo. El mediador decide
     * qué componente debe actuar a continuación.
     *
     * @param sender componente que notifica.
     * @param event  lo que ocurrió.
     */
    void notify(Object sender, MissionEvent event);

    /**
     * Inicia la operación de vigilancia del dron indicado coordinando todos
     * los componentes. El dron solo identifica a quién pertenece la operación:
     * no se consulta ni se modifica en el repositorio (sin CRUD).
     *
     * @param dron dron seleccionado para la misión.
     * @return bitácora de la operación: un resultado por cada paso, en orden.
     */
    List<ResultadoTarea> iniciarMision(Dron dron);
}
