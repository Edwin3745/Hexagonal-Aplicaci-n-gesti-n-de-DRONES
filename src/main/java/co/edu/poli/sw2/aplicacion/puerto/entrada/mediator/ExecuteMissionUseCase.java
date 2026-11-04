package co.edu.poli.sw2.aplicacion.puerto.entrada.mediator;

import co.edu.poli.sw2.dominio.command.ResultadoTarea;
import co.edu.poli.sw2.dominio.modelo.Dron;

import java.util.List;

/** Puerto de entrada: ejecutar una misión de vigilancia coordinada por el Mediator. */
public interface ExecuteMissionUseCase {

    /**
     * Coordina navegación, sensores, batería y comunicación para iniciar la misión.
     *
     * @param dron dron seleccionado para la misión (obligatorio).
     * @return bitácora con un resultado por cada paso; si algún paso falla
     *         (p. ej. batería baja) el último resultado es de error y la misión se aborta.
     */
    List<ResultadoTarea> executeMission(Dron dron);
}
