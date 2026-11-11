package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.EliminarDronUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.salida.DronRepository;
import co.edu.poli.sw2.aplicacion.puerto.salida.RepositorioException;

/**
 * Servicio de aplicación que implementa el caso de uso de eliminar un dron.
 *
 * <p>Solo conoce el puerto {@link DronRepository}; los fallos del repositorio
 * se traducen a {@link OperacionFallidaException}.</p>
 */
public class EliminarDronServicio implements EliminarDronUseCase {

    /** Puerto de salida por el que se elimina el dron. */
    private final DronRepository repositorio;

    /**
     * Crea el servicio con el puerto de persistencia que debe usar.
     *
     * @param repositorio puerto de salida a utilizar.
     */
    public EliminarDronServicio(DronRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Delega en el repositorio y traduce sus fallos a un mensaje para el
     * usuario.</p>
     *
     * @throws OperacionFallidaException si el repositorio falla.
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
