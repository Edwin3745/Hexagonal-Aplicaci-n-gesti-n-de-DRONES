package co.edu.poli.sw2.aplicacion.servicio;

import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.entrada.RegistrarDronUseCase;
import co.edu.poli.sw2.aplicacion.puerto.salida.DronRepository;
import co.edu.poli.sw2.aplicacion.puerto.salida.RepositorioException;
import co.edu.poli.sw2.dominio.modelo.Dron;

/**
 * Servicio de aplicación que implementa el caso de uso de registrar un dron.
 *
 * <p>Comprueba las reglas del dominio antes de tocar la persistencia y
 * traduce los fallos del repositorio a mensajes para el usuario. Solo conoce
 * el puerto {@link DronRepository}: no sabe qué base de datos hay detrás.</p>
 */
public class RegistrarDronServicio implements RegistrarDronUseCase {

    /** Puerto de salida por el que se guarda el dron. */
    private final DronRepository repositorio;

    /**
     * @param repositorio puerto de salida a utilizar.
     */
    public RegistrarDronServicio(DronRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registrar(Dron dron) {
        if (dron == null) {
            throw new OperacionFallidaException("No hay ningún dron que registrar.");
        }
        try {
            dron.validar();
        } catch (IllegalArgumentException e) {
            throw new OperacionFallidaException(e.getMessage(), e);
        }
        try {
            repositorio.guardar(dron);
        } catch (RepositorioException e) {
            throw OperacionFallidaException.porFalloDeRepositorio(e, "registrar", dron.getSerial());
        }
    }
}
