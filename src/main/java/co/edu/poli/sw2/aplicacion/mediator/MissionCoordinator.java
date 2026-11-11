package co.edu.poli.sw2.aplicacion.mediator;

import co.edu.poli.sw2.aplicacion.mediator.components.BatterySystem;
import co.edu.poli.sw2.aplicacion.mediator.components.CommunicationSystem;
import co.edu.poli.sw2.aplicacion.mediator.components.NavigationSystem;
import co.edu.poli.sw2.aplicacion.mediator.components.SensorSystem;
import co.edu.poli.sw2.dominio.command.ResultadoTarea;
import co.edu.poli.sw2.dominio.modelo.Dron;

import java.util.ArrayList;
import java.util.List;

/**
 * ConcreteMediator: coordina navegación, sensores, batería y comunicación.
 *
 * <p>Es el único que conoce a los cuatro componentes; ellos solo conocen la
 * interfaz {@link DroneMediator}. La secuencia de la misión está centralizada
 * aquí:</p>
 * <ol>
 *   <li>Navegación prepara la ruta  → {@code RUTA_LISTA}</li>
 *   <li>Sensores se activan         → {@code SENSORES_OK}</li>
 *   <li>Batería se verifica         → {@code BATERIA_OK} o {@code BATERIA_BAJA} (aborta)</li>
 *   <li>Se establece el enlace      → {@code ENLACE_OK}</li>
 *   <li>Misión de vigilancia iniciada</li>
 * </ol>
 *
 * <p>Cada paso queda registrado como un {@link ResultadoTarea} en la
 * bitácora que devuelve {@link #iniciarMision(Dron)}.</p>
 */
public class MissionCoordinator implements DroneMediator {

    private final NavigationSystem navigation;
    private final SensorSystem sensors;
    private final BatterySystem battery;
    private final CommunicationSystem comms;

    private final List<ResultadoTarea> bitacora = new ArrayList<>();
    private boolean abortada;
    private String dronActual = "";

    /**
     * Recibe los cuatro componentes y registra a este mediador en cada uno,
     * para que puedan notificarle sus eventos.
     *
     * @param navigation sistema de navegación.
     * @param sensors    sistema de sensores.
     * @param battery    sistema de batería.
     * @param comms      sistema de comunicación.
     * @throws IllegalArgumentException si alguno de los componentes es nulo.
     */
    public MissionCoordinator(NavigationSystem navigation, SensorSystem sensors,
                              BatterySystem battery, CommunicationSystem comms) {
        if (navigation == null || sensors == null || battery == null || comms == null) {
            throw new IllegalArgumentException("Todos los componentes son obligatorios.");
        }
        this.navigation = navigation;
        this.sensors = sensors;
        this.battery = battery;
        this.comms = comms;

        navigation.setMediator(this);
        sensors.setMediator(this);
        battery.setMediator(this);
        comms.setMediator(this);
    }

    /**
     * Inicia la operación de vigilancia del dron indicado.
     *
     * <p>Limpia la bitácora de la misión anterior, registra el dron
     * seleccionado y arranca la secuencia pidiéndole a la navegación que
     * prepare la ruta; los pasos siguientes los dispara
     * {@link #notify(Object, MissionEvent)}.</p>
     *
     * @param dron dron seleccionado para la misión.
     * @return copia inmodificable de la bitácora: un resultado por cada paso,
     *         en orden.
     * @throws IllegalArgumentException si {@code dron} es nulo.
     */
    @Override
    public List<ResultadoTarea> iniciarMision(Dron dron) {
        if (dron == null) {
            throw new IllegalArgumentException("Se requiere un dron para iniciar la misión.");
        }
        bitacora.clear();
        abortada = false;
        dronActual = dron.getTipo().getCodigo() + " " + dron.getSerial()
                + " (" + dron.getModelo() + ")";
        registrar("MissionCoordinator", true,
                "Dron seleccionado: " + dronActual + ". Iniciando coordinación.");

        navigation.prepararRuta();   // arranca la cadena; el resto lo dispara notify()

        return List.copyOf(bitacora);
    }

    /**
     * Recibe el aviso de un componente, lo registra en la bitácora y decide
     * el siguiente paso: {@code RUTA_LISTA} activa los sensores,
     * {@code SENSORES_OK} verifica la batería, {@code BATERIA_OK} establece el
     * enlace y {@code ENLACE_OK} da la misión por iniciada.
     * {@code BATERIA_BAJA} registra el error y aborta la misión; a partir de
     * ese momento se ignoran los avisos restantes.
     *
     * @param sender componente que notifica; el nombre de su clase se usa como
     *               origen en la bitácora.
     * @param event  lo que ocurrió.
     */
    @Override
    public void notify(Object sender, MissionEvent event) {
        if (abortada) {
            return;
        }
        String origen = sender.getClass().getSimpleName();

        switch (event) {
            case RUTA_LISTA -> {
                registrar(origen, true, navigation.getDetalle());
                sensors.activarSensores();
            }
            case SENSORES_OK -> {
                registrar(origen, true, sensors.getDetalle());
                battery.verificarBateria();
            }
            case BATERIA_OK -> {
                registrar(origen, true, battery.getDetalle());
                comms.establecerEnlace();
            }
            case BATERIA_BAJA -> {
                registrar(origen, false, battery.getDetalle());
                registrar("MissionCoordinator", false, "Misión abortada para el dron " + dronActual + ": no se puede iniciar sin energía suficiente.");
                abortada = true;
            }
            case ENLACE_OK -> {
                registrar(origen, true, comms.getDetalle());
                 registrar("MissionCoordinator", true, "Misión de vigilancia iniciada con el dron " + dronActual + ".");
            }
        }
    }

    private void registrar(String componente, boolean exitosa, String detalle) {
        bitacora.add(new ResultadoTarea(componente, exitosa, detalle));
    }
}
