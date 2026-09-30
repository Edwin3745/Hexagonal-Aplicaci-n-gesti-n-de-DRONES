package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.ActualizarDronUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.salida.DronRepository;
import co.edu.poli.sw2.aplicacion.puerto.salida.RepositorioException;
import co.edu.poli.sw2.dominio.modelo.Dron;

/**
 * Servicio de aplicación que implementa el caso de uso de actualizar un dron.
 *
 * <p>Aplica las mismas reglas del dominio que el registro: un dron no puede
 * quedar con datos inválidos por el hecho de haberse modificado.</p>
 */
public class ActualizarDronServicio implements ActualizarDronUseCase {

    /** Puerto de salida por el que se actualiza el dron. */
    private final DronRepository repositorio;

    /**
     * @param repositorio puerto de salida a utilizar.
     */
    public ActualizarDronServicio(DronRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean actualizar(Dron dron) {
        if (dron == null) {
            throw new OperacionFallidaException("No hay ningún dron seleccionado.");
        }
        try {
            dron.validar();
        } catch (IllegalArgumentException e) {
            throw new OperacionFallidaException(e.getMessage(), e);
        }
        try {
            return repositorio.actualizar(dron);
        } catch (RepositorioException e) {
            throw OperacionFallidaException.porFalloDeRepositorio(e, "actualizar", dron.getSerial());
        }
    }
}
