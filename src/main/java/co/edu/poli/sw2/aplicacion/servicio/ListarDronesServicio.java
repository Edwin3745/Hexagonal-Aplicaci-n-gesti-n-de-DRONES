package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.ListarDronesUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.salida.DronRepository;
import co.edu.poli.sw2.aplicacion.puerto.salida.RepositorioException;
import co.edu.poli.sw2.dominio.modelo.Dron;

import java.util.List;

/**
 * Servicio de aplicación que implementa el caso de uso de listar todos los
 * drones.
 */
public class ListarDronesServicio implements ListarDronesUseCase {

    /** Puerto de salida del que se leen los drones. */
    private final DronRepository repositorio;

    /**
     * @param repositorio puerto de salida a utilizar.
     */
    public ListarDronesServicio(DronRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Dron> listar() {
        try {
            return repositorio.listarTodos();
        } catch (RepositorioException e) {
            throw OperacionFallidaException.porFalloDeRepositorio(e, "listar", null);
        }
    }
}
