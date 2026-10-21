package co.edu.poli.sw2.dominio.weather;

/**
 * Sujeto del patrón Observer: quien publica los reportes del clima.
 *
 * <p>Mantiene la lista de {@link WeatherObserver} interesados y les avisa a
 * todos cuando hay un clima nuevo. Los observadores pueden entrar y salir de
 * la lista en cualquier momento mientras la aplicación está en ejecución,
 * que es lo que permite suscribir y desuscribir drones desde la interfaz.</p>
 */
public interface WeatherSubject {

    /**
     * Registra un observador para que reciba las próximas notificaciones.
     *
     * <p>Registrar dos veces el mismo observador no debe duplicar sus
     * notificaciones.</p>
     *
     * @param observer observador a registrar; no puede ser nulo.
     */
    void subscribe(WeatherObserver observer);

    /**
     * Retira un observador; a partir de ese momento deja de recibir
     * notificaciones. Si no estaba registrado, no ocurre nada.
     *
     * @param observer observador a retirar.
     */
    void unsubscribe(WeatherObserver observer);

    /**
     * Entrega el clima a todos los observadores registrados, uno por uno.
     *
     * @param weather clima que se notifica; no puede ser nulo.
     */
    void notifyObservers(Weather weather);
}