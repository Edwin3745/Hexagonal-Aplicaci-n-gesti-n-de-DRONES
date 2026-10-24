package co.edu.poli.sw2.infraestructura.ui;

import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.dominio.modelo.Agricultura;
import co.edu.poli.sw2.dominio.modelo.Dron;
import co.edu.poli.sw2.dominio.enumeracion.TipoDron;
import co.edu.poli.sw2.dominio.modelo.Vigilancia;
import co.edu.poli.sw2.dominio.weather.Weather;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

/**
 * Tecnología de la interfaz: controlador JavaFX de la pantalla de drones.
 *
 * <p>Es la única clase que conoce JavaFX. Solo se ocupa de los controles:
 * recoge lo que el usuario escribe, se lo pasa a {@link DronAdaptador} y
 * muestra lo que este devuelve. No conoce los casos de uso ni la base de datos,
 * y no interpreta errores técnicos: cuando algo falla, muestra el mensaje que
 * ya viene redactado desde la aplicación.</p>
 *
 * <p>Recibe el adaptador por su constructor. Lo crea {@code AppDron}, que se lo
 * entrega a través de la fábrica de controladores del {@code FXMLLoader}.</p>
 */
public class DronTecnologia {

    @FXML private ComboBox<TipoDron> cmbTipo;
    @FXML private TextField txtId;
    @FXML private TextField txtSerial;
    @FXML private TextField txtModelo;
    @FXML private TextField txtFabricante;
    @FXML private TextField txtPeso;

    @FXML private Label lblCapacidadTanque;
    @FXML private TextField txtCapacidadTanque;
    @FXML private Label lblDeteccionTermica;
    @FXML private CheckBox chkDeteccionTermica;

    @FXML private TableView<Dron> tablaDrones;
    @FXML private TableColumn<Dron, Integer> colId;
    @FXML private TableColumn<Dron, String> colTipo;
    @FXML private TableColumn<Dron, String> colSerial;
    @FXML private TableColumn<Dron, String> colModelo;
    @FXML private TableColumn<Dron, String> colFabricante;
    @FXML private TableColumn<Dron, Double> colPeso;

    @FXML private TextField txtCiudad;
    @FXML private TextArea txtClima;

    /** Adaptador de entrada al que se delega cada acción del usuario. */
    private final DronAdaptador adaptador;
    private final ObservableList<Dron> listaDrones = FXCollections.observableArrayList();

    /**
     * Crea el controlador de la pantalla con el adaptador que va a usar.
     *
     * @param adaptador adaptador de entrada de la aplicación.
     */
    public DronTecnologia(DronAdaptador adaptador) {
        this.adaptador = adaptador;
    }

    @FXML
    public void initialize() {
        configurarComboTipo();
        configurarTabla();
        actualizarTabla();
    }

    // ------------------------------------------------------------------
    // Configuración inicial
    // ------------------------------------------------------------------

    /**
     * Puebla el selector de tipo con las constantes del enumerado y hace que
     * los campos específicos aparezcan solo cuando corresponden al tipo elegido.
     */
    private void configurarComboTipo() {
        cmbTipo.setItems(FXCollections.observableArrayList(TipoDron.values()));

        // Muestra "Agricultura" en vez de "AGRICULTURA" sin acoplar el enum a la vista.
        cmbTipo.setConverter(new StringConverter<>() {
            @Override
            public String toString(TipoDron tipo) {
                if (tipo == null) {
                    return "";
                }
                String codigo = tipo.getCodigo();
                return Character.toUpperCase(codigo.charAt(0)) + codigo.substring(1);
            }

            @Override
            public TipoDron fromString(String texto) {
                return TipoDron.desdeCodigo(texto);
            }
        });

        cmbTipo.valueProperty().addListener((obs, anterior, nuevo) -> mostrarCamposDe(nuevo));

        cmbTipo.getSelectionModel().selectFirst();
    }

    /**
     * Ajusta la visibilidad de los campos propios de cada subtipo.
     *
     * <p>Los campos ocultos también se retiran del flujo del formulario
     * ({@code managed}) para que no dejen un hueco vacío.</p>
     *
     * @param tipo tipo seleccionado; si es nulo se ocultan todos.
     */
    private void mostrarCamposDe(TipoDron tipo) {
        boolean esAgricultura = tipo == TipoDron.AGRICULTURA;
        boolean esVigilancia = tipo == TipoDron.VIGILANCIA;

        lblCapacidadTanque.setVisible(esAgricultura);
        lblCapacidadTanque.setManaged(esAgricultura);
        txtCapacidadTanque.setVisible(esAgricultura);
        txtCapacidadTanque.setManaged(esAgricultura);

        lblDeteccionTermica.setVisible(esVigilancia);
        lblDeteccionTermica.setManaged(esVigilancia);
        chkDeteccionTermica.setVisible(esVigilancia);
        chkDeteccionTermica.setManaged(esVigilancia);
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSerial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colFabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));

        // El tipo no es una propiedad simple del bean: se obtiene del método
        // polimórfico que cada subclase implementa.
        colTipo.setCellValueFactory(fila ->
                new javafx.beans.property.SimpleStringProperty(
                        fila.getValue().getTipo().getCodigo()));

        tablaDrones.setItems(listaDrones);

        tablaDrones.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, seleccionado) -> {
                    if (seleccionado != null) {
                        cargarEnFormulario(seleccionado);
                    }
                });
    }

    // ------------------------------------------------------------------
    // Acciones del usuario
    // ------------------------------------------------------------------

    @FXML
    public void agregarDron() {
        try {
            TipoDron tipo = cmbTipo.getValue();
            if (tipo == null) {
                mostrarAlerta("Selecciona un tipo de dron.");
                return;
            }

            adaptador.registrarDron(
                    tipo,
                    txtSerial.getText().trim(),
                    txtModelo.getText().trim(),
                    txtFabricante.getText().trim(),
                    leerDecimal(txtPeso, "peso"),
                    tipo == TipoDron.AGRICULTURA
                            ? leerDecimal(txtCapacidadTanque, "capacidad del tanque") : 0.0,
                    chkDeteccionTermica.isSelected());

            actualizarTabla();
            limpiarFormulario();

        } catch (OperacionFallidaException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    @FXML
    public void actualizarDronSeleccionado() {
        Dron seleccionado = tablaDrones.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un dron de la tabla para actualizar.");
            return;
        }

        try {
            seleccionado.setSerial(txtSerial.getText().trim());
            seleccionado.setModelo(txtModelo.getText().trim());
            seleccionado.setFabricante(txtFabricante.getText().trim());
            seleccionado.setPeso(leerDecimal(txtPeso, "peso"));

            if (seleccionado instanceof Agricultura agricultura) {
                agricultura.setCapacidadTanque(
                        leerDecimal(txtCapacidadTanque, "capacidad del tanque"));
            } else if (seleccionado instanceof Vigilancia vigilancia) {
                vigilancia.setDeteccionTermica(chkDeteccionTermica.isSelected());
            }

            adaptador.actualizarDron(seleccionado);
            actualizarTabla();
            limpiarFormulario();

        } catch (OperacionFallidaException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    @FXML
    public void eliminarDron() {
        Dron seleccionado = tablaDrones.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un dron de la tabla para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Eliminar el dron " + seleccionado.getSerial() + "?");

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                try {
                    adaptador.eliminarDron(seleccionado.getId());
                    actualizarTabla();
                    limpiarFormulario();
                } catch (OperacionFallidaException e) {
                    mostrarAlerta(e.getMessage());
                }
            }
        });
    }

    @FXML
    public void limpiarFormulario() {
        txtId.clear();
        txtSerial.clear();
        txtModelo.clear();
        txtFabricante.clear();
        txtPeso.clear();
        txtCapacidadTanque.clear();
        chkDeteccionTermica.setSelected(false);
        cmbTipo.getSelectionModel().selectFirst();
        tablaDrones.getSelectionModel().clearSelection();
    }

    // ------------------------------------------------------------------
    // Clima (patrón Observer)
    // ------------------------------------------------------------------

    /**
     * Consulta el clima de la ciudad escrita. No muestra nada por sí mismo:
     * el resultado llega a la pantalla a través de los observadores suscritos.
     */
    @FXML
    public void consultarClima() {
        String ciudad = txtCiudad.getText().trim();
        if (ciudad.isEmpty()) {
            mostrarAlerta("Escribe una ciudad para consultar el clima.");
            return;
        }
        try {
            adaptador.getWeather(ciudad);
        } catch (OperacionFallidaException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    /** Suscribe el dron seleccionado en la tabla al notificador del clima. */
    @FXML
    public void suscribirDron() {
        Dron seleccionado = tablaDrones.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un dron de la tabla para suscribirlo.");
            return;
        }
        try {
            adaptador.subscribeDrone(seleccionado, this::mostrarLinea);
            mostrarLinea("Dron " + seleccionado.getSerial() + " suscrito.");
        } catch (OperacionFallidaException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    /** Desuscribe el dron seleccionado en la tabla del notificador del clima. */
    @FXML
    public void desuscribirDron() {
        Dron seleccionado = tablaDrones.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un dron de la tabla para desuscribirlo.");
            return;
        }
        try {
            adaptador.unsubscribeDrone(seleccionado);
            mostrarLinea("Dron " + seleccionado.getSerial() + " desuscrito.");
        } catch (OperacionFallidaException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    /**
     * Muestra los cinco aspectos del clima. La invoca el {@code DashboardObserver}.
     *
     * @param weather clima recibido.
     */
    public void mostrarClima(Weather weather) {
        mostrarLinea("");
        mostrarLinea("Clima en " + weather.getCity());
        mostrarLinea(String.format("  Temperatura: %.1f °C", weather.getTemperature()));
        mostrarLinea(String.format("  Humedad: %.0f %%", weather.getHumidity()));
        mostrarLinea(String.format("  Viento: %.1f km/h", weather.getWindSpeed()));
        mostrarLinea(String.format("  Presión: %.0f hPa", weather.getPressure()));
        mostrarLinea(String.format("  Visibilidad: %.1f km", weather.getVisibility()));
    }

    /**
     * Agrega una línea al área de resultados del clima.
     *
     * @param linea texto a mostrar.
     */
    private void mostrarLinea(String linea) {
        txtClima.appendText(linea + System.lineSeparator());
    }

    // ------------------------------------------------------------------
    // Apoyo
    // ------------------------------------------------------------------

    /**
     * Carga en el formulario los datos del dron seleccionado, incluidos los
     * campos propios de su subtipo.
     */
    private void cargarEnFormulario(Dron dron) {
        cmbTipo.setValue(dron.getTipo());
        txtId.setText(String.valueOf(dron.getId()));
        txtSerial.setText(dron.getSerial());
        txtModelo.setText(dron.getModelo());
        txtFabricante.setText(dron.getFabricante());
        txtPeso.setText(String.valueOf(dron.getPeso()));

        if (dron instanceof Agricultura agricultura) {
            txtCapacidadTanque.setText(String.valueOf(agricultura.getCapacidadTanque()));
            chkDeteccionTermica.setSelected(false);
        } else if (dron instanceof Vigilancia vigilancia) {
            txtCapacidadTanque.clear();
            chkDeteccionTermica.setSelected(vigilancia.isDeteccionTermica());
        }
    }

    private void actualizarTabla() {
        try {
            listaDrones.setAll(adaptador.listarDrones());
        } catch (OperacionFallidaException e) {
            mostrarAlerta(e.getMessage());
        }
    }

    /**
     * Lee un campo numérico del formulario.
     *
     * <p>Convierte el fallo de formato en el mismo tipo de excepción que usa la
     * capa de control, de modo que todos los errores del formulario se atiendan
     * en un único {@code catch}.</p>
     *
     * @param campo   campo de texto a leer.
     * @param nombre  nombre del dato, para redactar el mensaje de error.
     * @return valor numérico introducido.
     * @throws OperacionFallidaException si el texto no es un número válido.
     */
    private double leerDecimal(TextField campo, String nombre) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            throw new OperacionFallidaException("Introduce " + nombre + ".");
        }
        try {
            return Double.parseDouble(texto.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new OperacionFallidaException(
                    "El campo " + nombre + " debe ser un número. Valor recibido: " + texto);
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atención");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}