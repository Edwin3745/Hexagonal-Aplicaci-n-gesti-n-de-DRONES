package co.edu.poli.sw2.dominio.command;

/** Interfaz Command: encapsula una tarea del dron en un objeto. */
public interface DroneCommand {

    /** Ejecuta la tarea sobre su receptor. */
    ResultadoTarea ejecutar();

    /** @return nombre legible de la tarea, con el serial del dron. */
    String getNombre();
}
