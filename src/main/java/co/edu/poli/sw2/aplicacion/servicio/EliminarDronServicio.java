package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.EliminarDronUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.salida.DronRepository;
import co.edu.poli.sw2.aplicacion.puerto.salida.RepositorioException;

/**
 * Servicio de aplicación que implementa el caso de uso de eliminar un dron.
 */
public class EliminarDronServicio implements EliminarDronUseCase {

    /** Puerto de salida por el que se elimina el dron. */
    private final DronRepository repositorio;

    /**
     * @param repositorio puerto de salida a utilizar.
     */
    public EliminarDronServicio(DronRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eliminar(int id) {
        try {
            return repositorio.eliminar(id);
        } catch (RepositorioException e) {
            throw OperacionFallidaException.porFalloDeRepositorio(e, "eliminar", String.valueOf(id));
        }
    }
}
