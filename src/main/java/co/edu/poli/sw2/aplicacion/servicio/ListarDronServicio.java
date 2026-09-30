package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.ListarDronUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.salida.DronRepository;
import co.edu.poli.sw2.aplicacion.puerto.salida.RepositorioException;
import co.edu.poli.sw2.dominio.modelo.Dron;

/**
 * Servicio de aplicación que implementa el caso de uso de consultar un único
 * dron por su identificador.
 */
public class ListarDronServicio implements ListarDronUseCase {

    /** Puerto de salida del que se lee el dron. */
    private final DronRepository repositorio;

    /**
     * @param repositorio puerto de salida a utilizar.
     */
    public ListarDronServicio(DronRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dron listarPorId(int id) {
        try {
            return repositorio.buscarPorId(id);
        } catch (RepositorioException e) {
            throw OperacionFallidaException.porFalloDeRepositorio(e, "consultar", String.valueOf(id));
        }
    }
}
