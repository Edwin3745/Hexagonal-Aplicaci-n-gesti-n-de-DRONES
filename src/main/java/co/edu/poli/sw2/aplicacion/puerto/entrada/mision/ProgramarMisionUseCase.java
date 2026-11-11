package co.edu.poli.sw2.aplicacion.puerto.entrada.mision;

import co.edu.poli.sw2.dominio.command.DroneCommand;
import co.edu.poli.sw2.dominio.command.ResultadoTarea;

import java.util.List;

/**
 * Puerto de entrada: programar y ejecutar la secuencia de tareas de una
 * misión (patrón Command).
 *
 * <p>Cada tarea llega como un {@link DroneCommand}; quien implementa este
 * contrato actúa como Invoker: guarda los comandos y los ejecuta en orden sin
 * saber qué hace cada uno.</p>
 */
public interface ProgramarMisionUseCase {

    /**
     * Agrega un comando al final de la cola de tareas pendientes.
     *
     * @param comando tarea a programar.
     */
    void programar(DroneCommand comando);

    /**
     * Ejecuta todas las tareas en el orden registrado y vacía la cola.
     *
     * @return resultado de cada tarea, en el mismo orden de ejecución.
     * @throws co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException
     *         si no hay tareas programadas.
     */
    List<ResultadoTarea> ejecutar();

    /**
     * Consulta las tareas que esperan ser ejecutadas, sin ejecutarlas.
     *
     * @return nombres de las tareas pendientes, en orden.
     */
    List<String> listarPendientes();

    /** Descarta todas las tareas pendientes. */
    void limpiar();
}
