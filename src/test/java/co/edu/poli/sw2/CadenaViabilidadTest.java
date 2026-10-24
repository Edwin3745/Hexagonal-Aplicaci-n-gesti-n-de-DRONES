package co.edu.poli.sw2;

import co.edu.poli.sw2.dominio.chain.CadenaViabilidad;
import co.edu.poli.sw2.dominio.chain.ResultadoViabilidad;
import co.edu.poli.sw2.dominio.chain.WeatherHandler;
import co.edu.poli.sw2.dominio.weather.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de la cadena de responsabilidad de viabilidad de vuelo.
 * Solo usan el dominio: no requieren API, base de datos ni JavaFX.
 */
class CadenaViabilidadTest {

    private WeatherHandler cadena;

    @BeforeEach
    void preparar() {
        cadena = CadenaViabilidad.crear();
    }

    /** Crea un clima; cada prueba cambia solo el dato que quiere probar. */
    private Weather clima(double temp, double hum, double viento, double presion, double visib) {
        return new Weather("Bogotá", temp, hum, viento, presion, visib);
    }

    @Test
    void climaIdealEsApto() {
        ResultadoViabilidad r = cadena.handle(clima(20, 50, 10, 1013, 10));
        assertTrue(r.isApto());
        assertEquals("CLIMA APTO PARA OPERACIÓN DE DRONES", r.getMensaje());
        assertNull(r.getReglaIncumplida());
    }

    @Test
    void limitesExactosSonAptos() {
        assertTrue(cadena.handle(clima(0, 85, 40, 950, 5)).isApto());
        assertTrue(cadena.handle(clima(40, 85, 40, 1050, 5)).isApto());
    }

    @Test
    void temperaturaFueraDeRangoRechaza() {
        assertEquals("Temperatura", cadena.handle(clima(-0.1, 50, 10, 1013, 10)).getReglaIncumplida());
        assertEquals("Temperatura", cadena.handle(clima(40.1, 50, 10, 1013, 10)).getReglaIncumplida());
    }

    @Test
    void humedadAltaRechaza() {
        ResultadoViabilidad r = cadena.handle(clima(20, 85.1, 10, 1013, 10));
        assertFalse(r.isApto());
        assertEquals("Humedad", r.getReglaIncumplida());
    }

    @Test
    void vientoFuerteRechaza() {
        assertEquals("Velocidad del viento", cadena.handle(clima(20, 50, 40.1, 1013, 10)).getReglaIncumplida());
    }

    @Test
    void presionFueraDeRangoRechaza() {
        assertEquals("Presión atmosférica", cadena.handle(clima(20, 50, 10, 949.9, 10)).getReglaIncumplida());
        assertEquals("Presión atmosférica", cadena.handle(clima(20, 50, 10, 1050.1, 10)).getReglaIncumplida());
    }

    @Test
    void visibilidadBajaRechaza() {
        assertEquals("Visibilidad", cadena.handle(clima(20, 50, 10, 1013, 4.9)).getReglaIncumplida());
    }

    @Test
    void laCadenaSeDetieneEnLaPrimeraRegla() {
        // Incumple todo: debe reportar solo la primera de la cadena (Temperatura).
        ResultadoViabilidad r = cadena.handle(clima(99, 99, 99, 500, 0));
        assertEquals("Temperatura", r.getReglaIncumplida());
    }

    @Test
    void mensajeNoAptoIncluyeElMotivo() {
        ResultadoViabilidad r = cadena.handle(clima(20, 90, 10, 1013, 10));
        assertTrue(r.getMensaje().startsWith("CLIMA NO APTO PARA OPERACIÓN DE DRONES"));
        assertTrue(r.getMensaje().contains("humedad"));
    }

    @Test
    void datoInvalidoNuncaSeConsideraApto() {
        // NaN no cumple ninguna comparación: la cadena debe rechazarlo, no aprobarlo.
        assertFalse(cadena.handle(clima(Double.NaN, 50, 10, 1013, 10)).isApto());
        assertFalse(cadena.handle(clima(20, 50, 10, 1013, Double.NaN)).isApto());
    }
}