package co.edu.poli.sw2;

import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.aplicacion.servicio.mision.DroneOperacionService;
import co.edu.poli.sw2.aplicacion.servicio.mision.ProgramadorTareas;
import co.edu.poli.sw2.dominio.command.*;
import co.edu.poli.sw2.dominio.modelo.Agricultura;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProgramadorTareasTest {

    @Test
    void ejecutaLasTareasEnElOrdenRegistrado() {
        OperacionesDron receptor =
                new DroneOperacionService(new Agricultura(1, "AGR-001", "M1", "DJI", 5.0, 10.0));
        ProgramadorTareas programador = new ProgramadorTareas();

        programador.programar(new TomarFotografiaCommand(receptor));
        programador.programar(new EscanearZonaCommand(receptor));
        programador.programar(new MedirTemperaturaCommand(receptor));

        List<ResultadoTarea> resultados = programador.ejecutar();

        assertEquals(3, resultados.size());
        assertTrue(resultados.get(0).tarea().startsWith("Tomar fotografía"));
        assertTrue(resultados.get(1).tarea().startsWith("Escanear zona"));
        assertTrue(resultados.get(2).tarea().startsWith("Medir temperatura"));
        assertTrue(resultados.stream().allMatch(ResultadoTarea::exitosa));
        assertTrue(programador.listarPendientes().isEmpty());
    }

    @Test
    void sinTareasProgramadasLanzaExcepcion() {
        assertThrows(OperacionFallidaException.class, () -> new ProgramadorTareas().ejecutar());
    }
}
