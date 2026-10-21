package co.edu.poli.sw2;

import co.edu.poli.sw2.aplicacion.observer.ConsoleObserver;
import co.edu.poli.sw2.aplicacion.observer.DashboardObserver;
import co.edu.poli.sw2.aplicacion.observer.LoggerObserver;
import co.edu.poli.sw2.aplicacion.servicio.weather.WeatherNotificationService;
import co.edu.poli.sw2.dominio.weather.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de los observadores del clima de la capa de aplicación.
 *
 * <p>No usan la API ni la base de datos. El registro se escribe en un archivo
 * temporal que se borra al terminar cada prueba.</p>
 */
class WeatherObserversTest {

    private Weather bogota;

    @BeforeEach
    void preparar() {
        bogota = new Weather("Bogotá", 14.0, 77.0, 11.2, 1028.0, 10.0);
    }

    @Test
    void elDashboardEntregaElClimaALaPantallaYLoRecuerda() {
        List<Weather> pantalla = new ArrayList<>();
        DashboardObserver dashboard = new DashboardObserver(pantalla::add);

        dashboard.update(bogota);

        assertEquals(1, pantalla.size());
        assertSame(bogota, pantalla.get(0));
        assertSame(bogota, dashboard.getUltimoClima());
    }

    @Test
    void elLoggerAgregaUnaLineaPorCadaClimaSinBorrarLasAnteriores() throws Exception {
        Path archivo = Files.createTempFile("clima", ".log");
        try {
            LoggerObserver logger = new LoggerObserver(archivo);

            logger.update(bogota);
            logger.update(new Weather("Tunja", 11.0, 80.0, 20.0, 1030.0, 8.0));

            List<String> lineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);
            assertEquals(2, lineas.size());
            assertTrue(lineas.get(0).contains("Bogotá"));
            assertTrue(lineas.get(1).contains("Tunja"));
        } finally {
            Files.deleteIfExists(archivo);
        }
    }

    @Test
    void laConsolaImprimeLaCiudadYLosCincoAspectos() {
        ByteArrayOutputStream capturado = new ByteArrayOutputStream();
        ConsoleObserver consola = new ConsoleObserver(
                new PrintStream(capturado, true, StandardCharsets.UTF_8));

        consola.update(bogota);

        String impreso = capturado.toString(StandardCharsets.UTF_8);
        assertTrue(impreso.startsWith("[CLIMA] Bogotá"));
        assertTrue(impreso.contains("°C"));
        assertTrue(impreso.contains("km/h"));
        assertTrue(impreso.contains("hPa"));
        assertTrue(impreso.contains("km"));
    }

    @Test
    void losTresObservadoresSuscritosRecibenLaMismaNotificacion() throws Exception {
        Path archivo = Files.createTempFile("clima", ".log");
        try {
            List<Weather> pantalla = new ArrayList<>();
            ByteArrayOutputStream capturado = new ByteArrayOutputStream();

            WeatherNotificationService servicio = new WeatherNotificationService();
            servicio.subscribe(new DashboardObserver(pantalla::add));
            servicio.subscribe(new LoggerObserver(archivo));
            servicio.subscribe(new ConsoleObserver(
                    new PrintStream(capturado, true, StandardCharsets.UTF_8)));

            servicio.notifyObservers(bogota);

            assertSame(bogota, pantalla.get(0));
            assertEquals(1, Files.readAllLines(archivo, StandardCharsets.UTF_8).size());
            assertTrue(capturado.toString(StandardCharsets.UTF_8).contains("Bogotá"));
        } finally {
            Files.deleteIfExists(archivo);
        }
    }

    @Test
    void rechazanDependenciasNulas() {
        assertThrows(IllegalArgumentException.class, () -> new DashboardObserver(null));
        assertThrows(IllegalArgumentException.class, () -> new LoggerObserver(null));
        assertThrows(IllegalArgumentException.class, () -> new ConsoleObserver(null));
    }
}
