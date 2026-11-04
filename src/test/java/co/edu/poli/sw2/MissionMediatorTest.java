package co.edu.poli.sw2;

import co.edu.poli.sw2.aplicacion.mediator.MissionCoordinator;
import co.edu.poli.sw2.aplicacion.mediator.components.BatterySystem;
import co.edu.poli.sw2.aplicacion.mediator.components.CommunicationSystem;
import co.edu.poli.sw2.aplicacion.mediator.components.NavigationSystem;
import co.edu.poli.sw2.aplicacion.mediator.components.SensorSystem;
import co.edu.poli.sw2.aplicacion.servicio.mediator.MissionService;
import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.dominio.command.ResultadoTarea;
import co.edu.poli.sw2.dominio.modelo.Agricultura;
import co.edu.poli.sw2.dominio.modelo.Dron;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MissionMediatorTest {

    private final Dron dron = new Agricultura(1, "AGR-001", "M1", "DJI", 5.0, 10.0);

    private MissionService servicio(int bateria) {
        return new MissionService(new MissionCoordinator(
                new NavigationSystem(), new SensorSystem(),
                new BatterySystem(bateria), new CommunicationSystem()));
    }

    @Test
    void conBateriaSuficienteEjecutaLaSecuenciaCompleta() {
        List<ResultadoTarea> r = servicio(85).executeMission(dron);

        assertEquals(List.of("MissionCoordinator", "NavigationSystem", "SensorSystem", "BatterySystem",
                        "CommunicationSystem", "MissionCoordinator"),
                r.stream().map(ResultadoTarea::tarea).toList());
        assertTrue(r.stream().allMatch(ResultadoTarea::exitosa));
    }

    @Test
    void conBateriaBajaAbortaYNoEstableceEnlace() {
        List<ResultadoTarea> r = servicio(10).executeMission(dron);

        assertFalse(r.get(r.size() - 1).exitosa());
        assertTrue(r.stream().noneMatch(x -> x.tarea().equals("CommunicationSystem")));
    }

    @Test
    void sePuedeEjecutarVariasVecesSinArrastrarEstado() {
        MissionService s = servicio(85);
        assertEquals(s.executeMission(dron).size(), s.executeMission(dron).size());
    }

    @Test
    void bitacoraIdentificaAlDronSeleccionado() {
        List<ResultadoTarea> r = servicio(85).executeMission(dron);
        assertTrue(r.get(0).detalle().contains("AGR-001"));
        assertTrue(r.get(r.size() - 1).detalle().contains("AGR-001"));
    }

    @Test
    void sinDronSeleccionadoLanzaExcepcion() {
        assertThrows(OperacionFallidaException.class, () -> servicio(85).executeMission(null));
    }
}
