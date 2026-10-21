package co.edu.poli.sw2.aplicacion.servicio.weather;

import co.edu.poli.sw2.dominio.weather.Weather;
import co.edu.poli.sw2.dominio.weather.WeatherObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sujeto concreto del patrón Observer: publica cada clima consultado a todos
 * los observadores registrados.
 *
 * <p>Es el notificador al que se suscriben los drones. Mantiene la lista de
 * {@link WeatherObserver} y, cuando recibe un clima nuevo, llama a
 * {@code update} en cada uno, en el orden en que se suscribieron. Ni el
 * servicio sabe qué hace cada observador ni los observadores se conocen entre
 * sí: agregar uno nuevo no obliga a modificar esta clase.</p>
 *
 * <p>Los observadores entran y salen en tiempo de ejecución con
 * {@link #subscribe} y {@link #unsubscribe}; es lo que usan los botones de
 * suscribir y desuscribir drones de la interfaz.</p>
 *
 * <p>Dos decisiones de robustez:</p>
 * <ul>
 *   <li>La notificación recorre una <em>copia</em> de la lista. Así un
 *       observador puede suscribir o desuscribir a otro (o a sí mismo)
 *       mientras se está notificando sin provocar una
 *       {@code ConcurrentModificationException}.</li>
 *   <li>Si un observador falla, el error se registra y la notificación
 *       continúa con los demás: un observador defectuoso no deja sin datos al
 *       resto.</li>
 * </ul>
 */
public class WeatherNotificationService {

    private static final Logger LOG = Logger.getLogger(WeatherNotificationService.class.getName());

    /** Observadores registrados, en orden de suscripción. */
    private final List<WeatherObserver> observers = new ArrayList<>();

    /**
     * Registra un observador para que reciba las próximas notificaciones.
     * Registrar dos veces el mismo observador no duplica sus notificaciones.
     *
     * @param observer observador a registrar.
     * @throws IllegalArgumentException si el observador es nulo.
     */
    public void subscribe(WeatherObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("El observador a suscribir no puede ser nulo.");
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Retira un observador; deja de recibir notificaciones desde ese momento.
     * Si no estaba registrado, no ocurre nada.
     *
     * @param observer observador a retirar.
     */
    public void unsubscribe(WeatherObserver observer) {
        observers.remove(observer);
    }

    /**
     * Entrega el clima a todos los observadores registrados, uno por uno, en
     * el orden en que se suscribieron.
     *
     * @param weather clima que se notifica.
     * @throws IllegalArgumentException si el clima es nulo.
     */
    public void notifyObservers(Weather weather) {
        if (weather == null) {
            throw new IllegalArgumentException("No se puede notificar un clima nulo.");
        }
        for (WeatherObserver observer : List.copyOf(observers)) {
            try {
                observer.update(weather);
            } catch (RuntimeException e) {
                LOG.log(Level.WARNING, "El observador " + observer.getClass().getSimpleName()
                        + " falló al recibir el clima; se continúa con los demás.", e);
            }
        }
    }

    /**
     * Indica si un observador está registrado.
     *
     * @param observer observador a consultar.
     * @return {@code true} si recibirá la próxima notificación.
     */
    public boolean isSubscribed(WeatherObserver observer) {
        return observers.contains(observer);
    }

    /**
     * Cantidad de observadores registrados en este momento.
     *
     * @return número de observadores suscritos.
     */
    public int getObserverCount() {
        return observers.size();
    }
}