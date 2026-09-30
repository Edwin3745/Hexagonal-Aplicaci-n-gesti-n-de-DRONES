package co.edu.poli.sw2.infraestructura.ui;

import co.edu.poli.sw2.aplicacion.puerto.entrada.ActualizarDronUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.EliminarDronUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.ListarDronUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.ListarDronesUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.entrada.RegistrarDronUseCase;
import co.edu.poli.sw2.dominio.enumeracion.TipoDron;
import co.edu.poli.sw2.dominio.modelo.Agricultura;
import co.edu.poli.sw2.dominio.modelo.Dron;
import co.edu.poli.sw2.dominio.modelo.Vigilancia;

import java.util.List;

/**
 * Adaptador de entrada: traduce las acciones del usuario en llamadas a los
 * casos de uso.
 *
 * <p>Recibe los datos tal como los entrega la interfaz —textos, números,
 * casillas— y los convierte en objetos del dominio antes de invocar los
 * puertos de entrada. No depende de JavaFX: la tecnología de la interfaz vive
 * en {@code DronTecnologia}, y este adaptador serviría igual para otra
 * interfaz distinta.</p>
 *
 * <p>Solo conoce los puertos de entrada y el dominio. No sabe qué servicio
 * implementa cada caso de uso ni qué base de datos hay detrás: las
 * dependencias se las entrega {@code AppDron} al construirlo.</p>
 */
public class DronAdaptador {

    private final RegistrarDronUseCase registrar;
    private final ListarDronesUseCase listarTodos;
    private final ListarDronUseCase listarUno;
    private final ActualizarDronUseCase actualizar;
    private final EliminarDronUseCase eliminar;

    /**
     * Construye el adaptador con los casos de uso que va a invocar.
     *
     * @param registrar   caso de uso de registrar un dron.
     * @param listarTodos caso de uso de listar todos los drones.
     * @param listarUno   caso de uso de consultar un dron.
     * @param actualizar  caso de uso de actualizar un dron.
     * @param eliminar    caso de uso de eliminar un dron.
     */
    public DronAdaptador(RegistrarDronUseCase registrar,
                         ListarDronesUseCase listarTodos,
                         ListarDronUseCase listarUno,
                         ActualizarDronUseCase actualizar,
                         EliminarDronUseCase eliminar) {
        this.registrar = registrar;
        this.listarTodos = listarTodos;
        this.listarUno = listarUno;
        this.actualizar = actualizar;
        this.eliminar = eliminar;
    }

    /**
     * Construye el dron con los datos del formulario y lo registra.
     *
     * <p>Es el único punto de la entrada que decide qué subclase del dominio
     * construir a partir del tipo elegido por el usuario.</p>
     *
     * @param tipo             subtipo de dron a registrar.
     * @param serial           número de serie, debe ser único.
     * @param modelo           modelo del dron.
     * @param fabricante       fabricante del dron.
     * @param peso             peso en kilogramos.
     * @param capacidadTanque  litros del tanque; solo aplica a AGRICULTURA.
     * @param deteccionTermica cámara térmica; solo aplica a VIGILANCIA.
     * @throws OperacionFallidaException si la operación no puede completarse.
     */
    public void registrarDron(TipoDron tipo, String serial, String modelo,
                              String fabricante, double peso,
                              double capacidadTanque, boolean deteccionTermica) {
        if (tipo == null) {
            throw new OperacionFallidaException("Selecciona un tipo de dron.");
        }

        // El id lo genera la base de datos, por eso se envía 0.
        Dron dron = switch (tipo) {
            case AGRICULTURA -> new Agricultura(0, serial, modelo, fabricante,
                                                peso, capacidadTanque);
            case VIGILANCIA -> new Vigilancia(0, serial, modelo, fabricante,
                                              peso, deteccionTermica);
        };

        registrar.registrar(dron);
    }

    /**
     * @param id identificador del dron.
     * @return {@code true} si se eliminó.
     * @throws OperacionFallidaException si la operación no puede completarse.
     */
    public boolean eliminarDron(int id) {
        return eliminar.eliminar(id);
    }

    /**
     * @param id identificador del dron.
     * @return el dron encontrado, o {@code null} si no existe.
     * @throws OperacionFallidaException si la consulta no puede completarse.
     */
    public Dron buscarDron(int id) {
        return listarUno.listarPorId(id);
    }

    /**
     * @return lista de drones, vacía si no hay ninguno.
     * @throws OperacionFallidaException si la consulta no puede completarse.
     */
    public List<Dron> listarDrones() {
        return listarTodos.listar();
    }

    /**
     * @param dron dron con los datos actualizados.
     * @return {@code true} si se actualizó.
     * @throws OperacionFallidaException si la operación no puede completarse.
     */
    public boolean actualizarDron(Dron dron) {
        return actualizar.actualizar(dron);
    }
}
