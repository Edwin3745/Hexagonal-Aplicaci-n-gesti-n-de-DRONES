package co.edu.poli.sw2;

import co.edu.poli.sw2.aplicacion.servicio.weather.WeatherNotificationService;
import co.edu.poli.sw2.dominio.weather.Weather;
import co.edu.poli.sw2.dominio.weather.WeatherObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas del sujeto del patrón Observer.
 *
 * <p>No usan la API ni la base de datos: los observadores son dobles de
 * prueba que solo anotan lo que reciben.</p>
 */
class WeatherNotificationServiceTest {

    private WeatherNotificationService servicio;
    private Weather bogota;

    /** Observador de prueba que guarda cada clima recibido. */
    private static class ObservadorEspia implements WeatherObserver {
        final List<Weather> recibidos = new ArrayList<>();

        @Override
        public void update(Weather weather) {
            recibidos.add(weather);
        }
    }

    @BeforeEach
    void preparar() {
        servicio = new WeatherNotificationService();
        bogota = new Weather("Bogotá", 14.0, 77.0, 11.2, 1028.0, 10.0);
    }

    @Test
    void elObservadorSuscritoRecibeElClima() {
        ObservadorEspia espia = new ObservadorEspia();
        servicio.subscribe(espia);

        servicio.notifyObservers(bogota);

        assertEquals(1, espia.recibidos.size());
        assertSame(bogota, espia.recibidos.get(0));
    }

    @Test
    void todosLosSuscritosRecibenElMismoClima() {
        ObservadorEspia a = new ObservadorEspia();
        ObservadorEspia b = new ObservadorEspia();
        ObservadorEspia c = new ObservadorEspia();
        servicio.subscribe(a);
        servicio.subscribe(b);
        servicio.subscribe(c);

        servicio.notifyObservers(bogota);

        assertSame(bogota, a.recibidos.get(0));
        assertSame(bogota, b.recibidos.get(0));
        assertSame(bogota, c.recibidos.get(0));
    }

    @Test
    void alDesuscribirseDejaDeRecibir() {
        ObservadorEspia queda = new ObservadorEspia();
        ObservadorEspia sale = new ObservadorEspia();
        servicio.subscribe(queda);
        servicio.subscribe(sale);

        servicio.notifyObservers(bogota);
        servicio.unsubscribe(sale);
        servicio.notifyObservers(bogota);

        assertEquals(2, queda.recibidos.size());
        assertEquals(1, sale.recibidos.size(), "Solo debe recibir la notificación previa a desuscribirse");
        assertFalse(servicio.isSubscribed(sale));
    }

    @Test
    void suscribirDosVecesNoDuplicaLasNotificaciones() {
        ObservadorEspia espia = new ObservadorEspia();
        servicio.subscribe(espia);
        servicio.subscribe(espia);

        servicio.notifyObservers(bogota);

        assertEquals(1, servicio.getObserverCount());
        assertEquals(1, espia.recibidos.size());
    }

    @Test
    void desuscribirUnoQueNoEstabaNoFalla() {
        assertDoesNotThrow(() -> servicio.unsubscribe(new ObservadorEspia()));
        assertEquals(0, servicio.getObserverCount());
    }

    @Test
    void sinSuscritosNotificarNoFalla() {
        assertDoesNotThrow(() -> servicio.notifyObservers(bogota));
    }

    @Test
    void unObservadorPuedeDesuscribirseMientrasSeNotifica() {
        ObservadorEspia despues = new ObservadorEspia();
        WeatherObserver seVa = new WeatherObserver() {
            @Override
            public void update(Weather weather) {
                servicio.unsubscribe(this);
            }
        };
        servicio.subscribe(seVa);
        servicio.subscribe(despues);

        assertDoesNotThrow(() -> servicio.notifyObservers(bogota));
        assertEquals(1, despues.recibidos.size());
        assertFalse(servicio.isSubscribed(seVa));
    }

    @Test
    void unObservadorQueFallaNoImpideNotificarALosDemas() {
        ObservadorEspia despues = new ObservadorEspia();
        servicio.subscribe(weather -> { throw new IllegalStateException("falla de prueba"); });
        servicio.subscribe(despues);

        servicio.notifyObservers(bogota);

        assertEquals(1, despues.recibidos.size());
    }

    @Test
    void rechazaObservadorYClimaNulos() {
        assertThrows(IllegalArgumentException.class, () -> servicio.subscribe(null));
        assertThrows(IllegalArgumentException.class, () -> servicio.notifyObservers(null));
    }
}