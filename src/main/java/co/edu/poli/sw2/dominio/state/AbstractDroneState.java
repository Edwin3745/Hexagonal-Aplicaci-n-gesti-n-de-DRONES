package co.edu.poli.sw2.dominio.state;

/**
 * Base de los estados concretos del patrón State: rechaza toda operación
 * con {@link IllegalStateException} por defecto.
 *
 * <p>Cada estado concreto sobrescribe únicamente las operaciones que permite
 * y define su nombre con {@link #getNombre()}.</p>
 */
public abstract class AbstractDroneState implements DroneState {

    @Override
    public void encender(DroneContext contexto) {
        rechazar("encender");
    }

    @Override
    public void apagar(DroneContext contexto) {
        rechazar("apagar");
    }

    @Override
    public void despegar(DroneContext contexto) {
        rechazar("despegar");
    }

    @Override
    public void aterrizar(DroneContext contexto) {
        rechazar("aterrizar");
    }

    @Override
    public void finalizarAterrizaje(DroneContext contexto) {
        rechazar("finalizar el aterrizaje de");
    }

    private void rechazar(String accion) {
        throw new IllegalStateException(
                "No se puede " + accion + " un dron en estado " + getNombre() + ".");
    }
}