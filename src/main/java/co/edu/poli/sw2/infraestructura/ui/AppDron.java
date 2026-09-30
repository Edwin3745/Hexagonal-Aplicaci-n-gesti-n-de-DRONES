package co.edu.poli.sw2.infraestructura.ui;

import co.edu.poli.sw2.aplicacion.puerto.salida.DronRepository;
import co.edu.poli.sw2.aplicacion.servicio.ActualizarDronServicio;
import co.edu.poli.sw2.aplicacion.servicio.EliminarDronServicio;
import co.edu.poli.sw2.aplicacion.servicio.ListarDronServicio;
import co.edu.poli.sw2.aplicacion.servicio.ListarDronesServicio;
import co.edu.poli.sw2.aplicacion.servicio.RegistrarDronServicio;
import co.edu.poli.sw2.infraestructura.persistencia.ConexionBD;
import co.edu.poli.sw2.infraestructura.persistencia.PostgresDronRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
     * @param repositorio adaptador de salida que usarán los servicios.
     * @return adaptador de entrada listo para usarse.
     */
    public static DronAdaptador crearAdaptador(DronRepository repositorio) {
        return new DronAdaptador(
                new RegistrarDronServicio(repositorio),
                new ListarDronesServicio(repositorio),
                new ListarDronServicio(repositorio),
                new ActualizarDronServicio(repositorio),
                new EliminarDronServicio(repositorio));
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

        // 2. Servicios y adaptador de entrada, conectados sobre ese repositorio.
        DronAdaptador adaptador = crearAdaptador(repositorio);

        // 3. La vista recibe el adaptador por su constructor.
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co/edu/poli/sw2/vista/DrownView.fxml"));
        loader.setControllerFactory(tipo -> new DronTecnologia(adaptador));
        Parent root = loader.load();

        Scene scene = new Scene(root, 750, 500);
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
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
