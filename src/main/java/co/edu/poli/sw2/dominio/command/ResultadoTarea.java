package co.edu.poli.sw2.dominio.command;

/**
 * Resultado de ejecutar un comando.
 *
 * @param tarea   nombre de la tarea ejecutada (incluye el serial del dron).
 * @param exitosa si la tarea terminó bien.
 * @param detalle dato obtenido o motivo del fallo.
 */
public record ResultadoTarea(String tarea, boolean exitosa, String detalle) {

    @Override
    public String toString() {
        return (exitosa ? "[OK] " : "[ERROR] ") + tarea + " -> " + detalle;
    }
}
