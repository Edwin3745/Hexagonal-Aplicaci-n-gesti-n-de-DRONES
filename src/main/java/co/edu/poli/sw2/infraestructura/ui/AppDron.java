package co.edu.poli.sw2.infraestructura.ui;

import co.edu.poli.sw2.aplicacion.observer.ConsoleObserver;
import co.edu.poli.sw2.aplicacion.observer.DashboardObserver;
import co.edu.poli.sw2.aplicacion.observer.LoggerObserver;
import co.edu.poli.sw2.aplicacion.puerto.entrada.weather.EvaluarViabilidadVueloUseCase;
import co.edu.poli.sw2.aplicacion.puerto.entrada.weather.GetWeatherByCityUseCase;
import co.edu.poli.sw2.aplicacion.puerto.salida.DronRepository;
import co.edu.poli.sw2.aplicacion.servicio.ActualizarDronServicio;
import co.edu.poli.sw2.aplicacion.servicio.EliminarDronServicio;
import co.edu.poli.sw2.aplicacion.servicio.ListarDronServicio;
import co.edu.poli.sw2.aplicacion.servicio.ListarDronesServicio;
import co.edu.poli.sw2.aplicacion.servicio.RegistrarDronServicio;
import co.edu.poli.sw2.aplicacion.servicio.weather.EvaluarViabilidadVueloServicio;
import co.edu.poli.sw2.aplicacion.servicio.weather.GetWeatherByCityService;
import co.edu.poli.sw2.aplicacion.servicio.weather.WeatherNotificationService;
import co.edu.poli.sw2.infraestructura.persistencia.ConexionBD;
import co.edu.poli.sw2.infraestructura.persistencia.PostgresDronRepository;
import co.edu.poli.sw2.infraestructura.weather.GeoApiAdapter;
import co.edu.poli.sw2.infraestructura.weather.GeoApiClient;
import co.edu.poli.sw2.infraestructura.weather.OpenWeatherAdapter;
import co.edu.poli.sw2.infraestructura.weather.OpenWeatherClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Aplicación JavaFX y punto de composición de la arquitectura hexagonal.
 *
 * <p>Es la única clase del sistema que conoce todas las capas. Crea el
 * adaptador de salida, los servicios de aplicación y el adaptador de entrada,
 * y los conecta entre sí. El resto de las clases solo conocen interfaces: nadie
 * más decide qué implementación concreta se usa.</p>
 *
 * <p>Por eso, cambiar de base de datos consiste en cambiar una sola línea de
 * esta clase: la que crea el repositorio.</p>
 */
public class AppDron extends Application {

    /**
     * Conecta los servicios de aplicación y el adaptador de entrada sobre el
     * repositorio indicado.
     *
     * <p>Es el cableado de dependencias de la arquitectura. Lo usa la propia
     * aplicación al arrancar, y también las pruebas, que así se ejecutan con
     * la misma composición que el programa real.</p>
     *
     * @param repositorio    adaptador de salida que usarán los servicios.
     * @param consultarClima caso de uso de consultar el clima.
     * @param notificador    notificador del clima.
     * @param evaluador      caso de uso que evalúa el clima con la cadena.
     * @return adaptador de entrada listo para usarse.
     */
    public static DronAdaptador crearAdaptador(DronRepository repositorio,
                                               GetWeatherByCityUseCase consultarClima,
                                               WeatherNotificationService notificador,
                                               EvaluarViabilidadVueloUseCase evaluador) {
        return new DronAdaptador(
                new RegistrarDronServicio(repositorio),
                new ListarDronesServicio(repositorio),
                new ListarDronServicio(repositorio),
                new ActualizarDronServicio(repositorio),
                new EliminarDronServicio(repositorio),
                consultarClima,
                notificador,
                evaluador);
    }

    /**
     * Arma la aplicación y muestra la ventana principal.
     *
     * @param stage escenario principal proporcionado por JavaFX.
     * @throws Exception si no se puede cargar la vista o sus recursos.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // 1. Adaptador de salida: la única línea que decide la base de datos.
        DronRepository repositorio = new PostgresDronRepository();

        // 2. Clima: adaptadores de OpenWeatherMap, notificador y casos de uso.
        String apiKey = leerApiKey();
        WeatherNotificationService notificador = new WeatherNotificationService();
        GetWeatherByCityUseCase consultarClima = new GetWeatherByCityService(
                new GeoApiAdapter(new GeoApiClient(apiKey)),
                new OpenWeatherAdapter(new OpenWeatherClient(apiKey)),
                notificador);
        EvaluarViabilidadVueloUseCase evaluador = new EvaluarViabilidadVueloServicio();

        // 3. Servicios y adaptador de entrada.
        DronAdaptador adaptador = crearAdaptador(repositorio, consultarClima, notificador, evaluador);

        // 4. La vista recibe el adaptador por su constructor.
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co/edu/poli/sw2/vista/DrownView.fxml"));
        loader.setControllerFactory(tipo -> new DronTecnologia(adaptador));
        Parent root = loader.load();
        DronTecnologia vista = loader.getController();

        // 5. Observadores fijos del clima; los drones se suscriben desde la vista.
        notificador.subscribe(new DashboardObserver(vista::mostrarClima));
        notificador.subscribe(new LoggerObserver());
        notificador.subscribe(new ConsoleObserver());

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(
                getClass().getResource("/co/edu/poli/sw2/vista/styles.css").toExternalForm());
        stage.setTitle("Gestión de Drones");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Libera los recursos al cerrarse la aplicación.
     *
     * <p>Cierra la conexión compartida del Singleton {@link ConexionBD}: es el
     * único punto del programa autorizado a hacerlo, porque mientras la
     * aplicación esté viva cualquier operación puede necesitarla.</p>
     */
    @Override
    public void stop() {
        ConexionBD.getInstancia().cerrarConexion();
    }

    /**
     * Lee la API key de OpenWeatherMap del archivo {@code .env} del proyecto
     * (línea {@code OPENWEATHER_API_KEY=...}).
     *
     * @return la API key, o una cadena vacía si no está definida; en ese caso
     *         la API responde que la clave es inválida y la vista lo informa.
     * @throws IOException si el archivo existe pero no se puede leer.
     */
    private static String leerApiKey() throws IOException {
        Path env = Path.of(System.getProperty("user.dir"), ".env");
        if (Files.exists(env)) {
            for (String linea : Files.readAllLines(env)) {
                if (linea.trim().startsWith("OPENWEATHER_API_KEY=")) {
                    return linea.substring(linea.indexOf('=') + 1).trim();
                }
            }
        }
        return "";
    }

    /**
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}