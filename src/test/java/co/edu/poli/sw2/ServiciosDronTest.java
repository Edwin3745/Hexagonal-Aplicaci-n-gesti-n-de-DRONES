package co.edu.poli.sw2;

import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.puerto.salida.DronRepository;
import co.edu.poli.sw2.aplicacion.puerto.salida.RepositorioException;
import co.edu.poli.sw2.aplicacion.servicio.ActualizarDronServicio;
import co.edu.poli.sw2.aplicacion.servicio.EliminarDronServicio;
import co.edu.poli.sw2.aplicacion.servicio.ListarDronServicio;
import co.edu.poli.sw2.aplicacion.servicio.ListarDronesServicio;
import co.edu.poli.sw2.aplicacion.servicio.RegistrarDronServicio;
import co.edu.poli.sw2.dominio.modelo.Agricultura;
import co.edu.poli.sw2.dominio.modelo.Dron;
import co.edu.poli.sw2.dominio.modelo.Vigilancia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias de los servicios de aplicación.
 *
 * <p>No usan base de datos: el puerto de salida se sustituye por un
 * repositorio en memoria. Es justamente lo que la arquitectura hexagonal
 * permite: como los servicios solo dependen de la interfaz
 * {@link DronRepository}, se pueden probar sin PostgreSQL.</p>
 */
class ServiciosDronTest {

    private RepositorioEnMemoria repositorio;

    @BeforeEach
    void preparar() {
        repositorio = new RepositorioEnMemoria();
    }

    @Test
    void registrar_dronValido_leAsignaIdentificador() {
        Dron dron = new Agricultura(0, "AGR-100", "Agras T40", "DJI", 38.0, 40.0);

        new RegistrarDronServicio(repositorio).registrar(dron);

        assertTrue(dron.getId() > 0, "El repositorio debe asignar el identificador");
        assertEquals(1, repositorio.listarTodos().size());
    }

    @Test
    void registrar_sinSerial_seRechazaSinTocarElRepositorio() {
        Dron dron = new Vigilancia(0, "  ", "Matrice 30T", "DJI", 3.7, true);

        OperacionFallidaException e = assertThrows(OperacionFallidaException.class,
                () -> new RegistrarDronServicio(repositorio).registrar(dron));

        assertTrue(e.getMessage().toLowerCase().contains("serial"));
        assertTrue(repositorio.listarTodos().isEmpty(),
                "Un dron inválido no debe llegar al repositorio");
    }

    @Test
    void registrar_conPesoNegativo_seRechaza() {
        Dron dron = new Agricultura(0, "AGR-101", "Agras T40", "DJI", -1.0, 40.0);

        OperacionFallidaException e = assertThrows(OperacionFallidaException.class,
                () -> new RegistrarDronServicio(repositorio).registrar(dron));

        assertTrue(e.getMessage().toLowerCase().contains("peso"));
    }

    @Test
    void registrar_serialDuplicado_traduceElMotivoDelRepositorio() {
        RegistrarDronServicio servicio = new RegistrarDronServicio(repositorio);
        servicio.registrar(new Agricultura(0, "AGR-102", "Agras T40", "DJI", 38.0, 40.0));

        OperacionFallidaException e = assertThrows(OperacionFallidaException.class,
                () -> servicio.registrar(new Agricultura(0, "AGR-102", "Otro", "XAG", 20.0, 10.0)));

        assertTrue(e.getMessage().contains("Ya existe"),
                "El motivo DUPLICADO debe llegar como un mensaje legible");
        assertInstanceOf(RepositorioException.class, e.getCause(),
                "El error original se conserva como causa");
    }

    @Test
    void listarPorId_devuelveElDronRegistrado() {
        Dron dron = new Vigilancia(0, "VIG-100", "Matrice 30T", "DJI", 3.7, true);
        new RegistrarDronServicio(repositorio).registrar(dron);

        Dron encontrado = new ListarDronServicio(repositorio).listarPorId(dron.getId());

        assertNotNull(encontrado);
        assertEquals("VIG-100", encontrado.getSerial());
    }

    @Test
    void listar_devuelveTodosLosDrones() {
        RegistrarDronServicio registrar = new RegistrarDronServicio(repositorio);
        registrar.registrar(new Agricultura(0, "AGR-103", "Agras T40", "DJI", 38.0, 40.0));
        registrar.registrar(new Vigilancia(0, "VIG-101", "Matrice 30T", "DJI", 3.7, true));

        assertEquals(2, new ListarDronesServicio(repositorio).listar().size());
    }

    @Test
    void actualizar_sinDron_seRechaza() {
        assertThrows(OperacionFallidaException.class,
                () -> new ActualizarDronServicio(repositorio).actualizar(null));
    }

    @Test
    void actualizar_conDatosInvalidos_seRechaza() {
        Dron dron = new Agricultura(0, "AGR-104", "Agras T40", "DJI", 38.0, 40.0);
        new RegistrarDronServicio(repositorio).registrar(dron);
        dron.setModelo("");

        assertThrows(OperacionFallidaException.class,
                () -> new ActualizarDronServicio(repositorio).actualizar(dron));
    }

    @Test
    void eliminar_quitaElDron() {
        Dron dron = new Agricultura(0, "AGR-105", "Agras T40", "DJI", 38.0, 40.0);
        new RegistrarDronServicio(repositorio).registrar(dron);

        assertTrue(new EliminarDronServicio(repositorio).eliminar(dron.getId()));
        assertNull(new ListarDronServicio(repositorio).listarPorId(dron.getId()));
    }

    @Test
    void falloTecnicoDelRepositorio_llegaComoMensajeLegible() {
        repositorio.fallarConMotivo = RepositorioException.Motivo.FALLO_TECNICO;

        OperacionFallidaException e = assertThrows(OperacionFallidaException.class,
                () -> new ListarDronesServicio(repositorio).listar());

        assertTrue(e.getMessage().contains("No se pudo listar"));
    }

    /**
     * Implementación del puerto de salida en memoria, para probar los
     * servicios sin base de datos.
     */
    private static class RepositorioEnMemoria implements DronRepository {

        private final Map<Integer, Dron> drones = new LinkedHashMap<>();
        private int siguienteId = 1;

        /** Si no es nulo, todas las operaciones fallan con este motivo. */
        RepositorioException.Motivo fallarConMotivo;

        private void quizaFallar() {
            if (fallarConMotivo != null) {
                throw new RepositorioException(fallarConMotivo, "Fallo simulado");
            }
        }

        @Override
        public void guardar(Dron dron) {
            quizaFallar();
            boolean serialRepetido = drones.values().stream()
                    .anyMatch(d -> d.getSerial().equals(dron.getSerial()));
            if (serialRepetido) {
                throw new RepositorioException(RepositorioException.Motivo.DUPLICADO,
                        "Serial repetido");
            }
            dron.setId(siguienteId++);
            drones.put(dron.getId(), dron);
        }

        @Override
        public boolean actualizar(Dron dron) {
            quizaFallar();
            return drones.replace(dron.getId(), dron) != null;
        }

        @Override
        public boolean eliminar(int id) {
            quizaFallar();
            return drones.remove(id) != null;
        }

        @Override
        public Dron buscarPorId(int id) {
            quizaFallar();
            return drones.get(id);
        }

        @Override
        public List<Dron> listarTodos() {
            quizaFallar();
            return new ArrayList<>(drones.values());
        }
    }
}
