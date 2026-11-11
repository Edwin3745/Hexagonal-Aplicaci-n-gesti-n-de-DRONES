package co.edu.poli.sw2.aplicacion.servicio.mision;

import co.edu.poli.sw2.dominio.command.OperacionesDron;
import co.edu.poli.sw2.dominio.modelo.Dron;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Receiver del patrón Command. Envuelve un Dron y contiene la lógica real
 * de cada operación (aquí simulada).
 *
 * <p>Implementa {@link OperacionesDron}, la interfaz de la que dependen los
 * comandos, de modo que la clase {@link Dron} no se modifica.</p>
 */
public class DroneOperacionService implements OperacionesDron {

    private static final AtomicInteger CONSECUTIVO_FOTOS = new AtomicInteger();

    private final Dron dron;

    /**
     * Crea el receptor de las tareas del dron indicado.
     *
     * @param dron dron que ejecutará las tareas.
     * @throws IllegalArgumentException si {@code dron} es nulo.
     */
    public DroneOperacionService(Dron dron) {
        if (dron == null) {
            throw new IllegalArgumentException("El dron no puede ser nulo.");
        }
        this.dron = dron;
    }

    /**
     * Devuelve el serial del dron envuelto.
     *
     * @return serial del dron.
     */
    @Override
    public String getSerial() {
        return dron.getSerial();
    }

    /**
     * Simula la toma de una fotografía y genera un nombre de archivo con el
     * serial del dron y un consecutivo compartido por todos los drones.
     *
     * @return nombre del archivo generado, por ejemplo
     *         {@code SERIAL_foto_001.jpg}.
     */
    @Override
    public String tomarFotografia() {
        return String.format("%s_foto_%03d.jpg", dron.getSerial(),
                CONSECUTIVO_FOTOS.incrementAndGet());
    }

    /**
     * Simula el escaneo de la zona con un número aleatorio de puntos de
     * interés (entre 0 y 14).
     *
     * @return descripción del resultado del escaneo.
     */
    @Override
    public String escanearZona() {
        int puntos = ThreadLocalRandom.current().nextInt(0, 15);
        return "Zona escaneada por dron " + dron.getTipo().getCodigo()
                + ": " + puntos + " puntos de interés detectados";
    }

    /**
     * Simula una medición de temperatura con un valor aleatorio entre 15 y
     * 30 °C.
     *
     * @return temperatura medida en °C.
     */
    @Override
    public double medirTemperatura() {
        return 15 + ThreadLocalRandom.current().nextDouble() * 15;
    }
}
