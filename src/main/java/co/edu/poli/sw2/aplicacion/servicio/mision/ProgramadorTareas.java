package co.edu.poli.sw2.aplicacion.servicio.mision;

import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.entrada.mision.ProgramarMisionUseCase;
import co.edu.poli.sw2.dominio.command.DroneCommand;
import co.edu.poli.sw2.dominio.command.ResultadoTarea;

import java.util.ArrayList;
import java.util.List;

/**
 * Invoker del patrón Command. Guarda los comandos en una cola y los ejecuta
 * en el orden registrado. No sabe qué hace cada comando: solo llama a ejecutar().
 *
 * <p>Es el servicio de aplicación que implementa
 * {@link ProgramarMisionUseCase}.</p>
 */
public class ProgramadorTareas implements ProgramarMisionUseCase {

    private final List<DroneCommand> cola = new ArrayList<>();

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException si {@code comando} es nulo.
     */
    @Override
    public void programar(DroneCommand comando) {
        if (comando == null) {
            throw new IllegalArgumentException("El comando no puede ser nulo.");
        }
        cola.add(comando);
    }

    /**
     * {@inheritDoc}
     *
     * <p>La cola se vacía antes de ejecutar, así que las tareas no se
     * repiten en la siguiente ejecución.</p>
     *
     * @throws OperacionFallidaException si no hay tareas programadas.
     */
    @Override
    public List<ResultadoTarea> ejecutar() {
        if (cola.isEmpty()) {
            throw new OperacionFallidaException("No hay tareas programadas para ejecutar.");
        }
        List<DroneCommand> pendientes = new ArrayList<>(cola);
        cola.clear();

        List<ResultadoTarea> resultados = new ArrayList<>();
        for (DroneCommand comando : pendientes) {
            resultados.add(comando.ejecutar());
        }
        return resultados;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> listarPendientes() {
        List<String> nombres = new ArrayList<>();
        for (DroneCommand comando : cola) {
            nombres.add(comando.getNombre());
        }
        return nombres;
    }

    /** {@inheritDoc} */
    @Override
    public void limpiar() {
        cola.clear();
    }
}
