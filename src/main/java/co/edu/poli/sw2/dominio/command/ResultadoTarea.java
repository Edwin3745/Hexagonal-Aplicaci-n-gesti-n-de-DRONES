package co.edu.poli.sw2.dominio.command;

/**
 * Resultado de ejecutar un comando.
 *
 * <p>Lo devuelve {@link DroneCommand#ejecutar()}; el Invoker reúne uno por
 * cada tarea de la misión.</p>
 *
 * @param tarea   nombre de la tarea ejecutada (incluye el serial del dron).
 * @param exitosa si la tarea terminó bien.
 * @param detalle dato obtenido o motivo del fallo.
 */
public record ResultadoTarea(String tarea, boolean exitosa, String detalle) {

    /**
     * Línea de texto con el estado, la tarea y el detalle.
     *
     * @return {@code "[OK] tarea -> detalle"} si fue exitosa, o
     *         {@code "[ERROR] tarea -> detalle"} si falló.
     */
    @Override
    public String toString() {
        return (exitosa ? "[OK] " : "[ERROR] ") + tarea + " -> " + detalle;
    }
}
