package co.edu.poli.sw2.aplicacion.puerto.entrada.mision;

import co.edu.poli.sw2.dominio.command.DroneCommand;
import co.edu.poli.sw2.dominio.command.ResultadoTarea;

import java.util.List;

/** Puerto de entrada: programar y ejecutar la secuencia de tareas de una misión. */
public interface ProgramarMisionUseCase {

    /** Agrega un comando al final de la cola. */
    void programar(DroneCommand comando);

    /**
     * Ejecuta todas las tareas en el orden registrado y vacía la cola.
     *
     * @throws co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException
     *         si no hay tareas programadas.
     */
    List<ResultadoTarea> ejecutar();

    /** @return nombres de las tareas pendientes, en orden. */
    List<String> listarPendientes();

    /** Descarta todas las tareas pendientes. */
    void limpiar();
}
