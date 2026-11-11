package co.edu.poli.sw2.aplicacion.servicio.weather;

import co.edu.poli.sw2.dominio.weather.Weather;
import co.edu.poli.sw2.dominio.weather.WeatherObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Notificador (Subject) del patrón Observer: los drones y demás observadores
 * se suscriben aquí y reciben automáticamente cada clima consultado.
 *
 * <p>Solo conoce la interfaz {@link WeatherObserver}, por lo que se pueden
 * agregar observadores nuevos sin modificar esta clase.</p>
 */
public class WeatherNotificationService {

    /** Observadores suscritos. */
    private final List<WeatherObserver> observers = new ArrayList<>();

    /**
     * Suscribe un observador.
     *
     * @param observer observador a suscribir.
     */
    public void subscribe(WeatherObserver observer) {
        observers.add(observer);
    }

    /**
     * Desuscribe un observador; deja de recibir notificaciones.
     *
     * @param observer observador a desuscribir.
     */
    public void unsubscribe(WeatherObserver observer) {
        observers.remove(observer);
    }

    /**
     * Envía el clima a todos los observadores suscritos, en el orden en que
     * se suscribieron.
     *
     * @param weather clima consultado.
     */
    public void notifyObservers(Weather weather) {
        for (WeatherObserver observer : observers) {
            observer.update(weather);
        }
    }
}