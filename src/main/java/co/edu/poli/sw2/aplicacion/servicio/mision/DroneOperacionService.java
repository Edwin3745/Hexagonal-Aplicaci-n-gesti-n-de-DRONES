package co.edu.poli.sw2.aplicacion.servicio.mision;

import co.edu.poli.sw2.dominio.command.OperacionesDron;
import co.edu.poli.sw2.dominio.modelo.Dron;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Receiver del patrón Command. Envuelve un Dron y contiene la lógica real
 * de cada operación (aquí simulada).
 */
public class DroneOperacionService implements OperacionesDron {

    private static final AtomicInteger CONSECUTIVO_FOTOS = new AtomicInteger();

    private final Dron dron;

    public DroneOperacionService(Dron dron) {
        if (dron == null) {
            throw new IllegalArgumentException("El dron no puede ser nulo.");
        }
        this.dron = dron;
    }

    @Override
    public String getSerial() {
        return dron.getSerial();
    }

    @Override
    public String tomarFotografia() {
        return String.format("%s_foto_%03d.jpg", dron.getSerial(),
                CONSECUTIVO_FOTOS.incrementAndGet());
    }

    @Override
    public String escanearZona() {
        int puntos = ThreadLocalRandom.current().nextInt(0, 15);
        return "Zona escaneada por dron " + dron.getTipo().getCodigo()
                + ": " + puntos + " puntos de interés detectados";
    }

    @Override
    public double medirTemperatura() {
        return 15 + ThreadLocalRandom.current().nextDouble() * 15;
    }
}
