package co.edu.poli.sw2.dominio.command;

/**
 * Interfaz Command: encapsula una tarea del dron en un objeto.
 *
 * <p>Permite programar tareas y ejecutarlas después, en orden, sin que quien
 * las ejecuta (el Invoker, {@code ProgramadorTareas} en la capa de
 * aplicación) sepa qué hace cada una. Cada comando concreto delega el
 * trabajo real en un {@link OperacionesDron} (Receiver).</p>
 */
public interface DroneCommand {

    /**
     * Ejecuta la tarea sobre su receptor.
     *
     * @return el resultado de la tarea, exitoso o fallido.
     */
    ResultadoTarea ejecutar();

    /**
     * Nombre de la tarea para mostrarla en la lista de pendientes y en los resultados.
     *
     * @return nombre legible de la tarea, con el serial del dron.
     */
    String getNombre();
}
