package co.edu.poli.sw2.infraestructura.ui;

import co.edu.poli.sw2.aplicacion.puerto.entrada.ActualizarDronUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.EliminarDronUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.ListarDronUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.ListarDronesUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.entrada.RegistrarDronUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.weather.EvaluarViabilidadVueloUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.weather.GetWeatherByCityUseCase;
import co.edu.poli.sw2.aplicacion.observer.FlightStatusObserver;
import co.edu.poli.sw2.aplicacion.servicio.weather.WeatherNotificationService;
import co.edu.poli.sw2.dominio.enumeracion.TipoDron;
import co.edu.poli.sw2.dominio.modelo.Agricultura;
import co.edu.poli.sw2.dominio.modelo.Dron;
import co.edu.poli.sw2.dominio.modelo.Vigilancia;
import co.edu.poli.sw2.dominio.weather.Weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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
    private final GetWeatherByCityUseCase consultarClima;
    private final WeatherNotificationService notificador;
    private final EvaluarViabilidadVueloUseCase evaluador;

    /** Observador de cada dron suscrito, por id, para poder desuscribirlo. */
    private final Map<Integer, FlightStatusObserver> dronesSuscritos = new HashMap<>();

    /**
     * Construye el adaptador con los casos de uso que va a invocar.
     *
     * @param registrar      caso de uso de registrar un dron.
     * @param listarTodos    caso de uso de listar todos los drones.
     * @param listarUno      caso de uso de consultar un dron.
     * @param actualizar     caso de uso de actualizar un dron.
     * @param eliminar       caso de uso de eliminar un dron.
     * @param consultarClima caso de uso de consultar el clima de una ciudad.
     * @param notificador    notificador al que se suscriben los drones.
     * @param evaluador      caso de uso que evalúa el clima con la cadena.
     */
    public DronAdaptador(RegistrarDronUseCase registrar,
                         ListarDronesUseCase listarTodos,
                         ListarDronUseCase listarUno,
                         ActualizarDronUseCase actualizar,
                         EliminarDronUseCase eliminar,
                         GetWeatherByCityUseCase consultarClima,
                         WeatherNotificationService notificador,
                         EvaluarViabilidadVueloUseCase evaluador) {
        this.registrar = registrar;
        this.listarTodos = listarTodos;
        this.listarUno = listarUno;
        this.actualizar = actualizar;
        this.eliminar = eliminar;
        this.consultarClima = consultarClima;
        this.notificador = notificador;
        this.evaluador = evaluador;
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

    // ------------------------------------------------------------------
    // Clima (patrón Observer)
    // ------------------------------------------------------------------

    /**
     * Consulta el clima de la ciudad; el caso de uso lo notifica a todos los
     * observadores suscritos.
     *
     * @param city nombre de la ciudad.
     * @return el clima consultado.
     * @throws OperacionFallidaException si la consulta no puede completarse.
     */
    public Weather getWeather(String city) {
        return consultarClima.execute(city);
    }

    /**
     * Suscribe un dron al notificador del clima.
     *
     * @param dron   dron a suscribir.
     * @param salida acción de la interfaz que muestra su resultado.
     * @throws OperacionFallidaException si el dron ya estaba suscrito.
     */
    public void subscribeDrone(Dron dron, Consumer<String> salida) {
        if (dronesSuscritos.containsKey(dron.getId())) {
            throw new OperacionFallidaException("El dron " + dron.getSerial() + " ya está suscrito.");
        }
        FlightStatusObserver observador = new FlightStatusObserver(dron, evaluador, salida);
        dronesSuscritos.put(dron.getId(), observador);
        notificador.subscribe(observador);
    }

    /**
     * Desuscribe un dron del notificador del clima.
     *
     * @param dron dron a desuscribir.
     * @throws OperacionFallidaException si el dron no estaba suscrito.
     */
    public void unsubscribeDrone(Dron dron) {
        FlightStatusObserver observador = dronesSuscritos.remove(dron.getId());
        if (observador == null) {
            throw new OperacionFallidaException("El dron " + dron.getSerial() + " no está suscrito.");
        }
        notificador.unsubscribe(observador);
    }
}