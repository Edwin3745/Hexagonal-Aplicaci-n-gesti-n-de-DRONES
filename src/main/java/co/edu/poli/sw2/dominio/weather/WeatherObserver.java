package co.edu.poli.sw2.dominio.weather;

/**
 * Observador del patrón Observer: todo lo que quiera enterarse de un nuevo
 * reporte del clima.
 *
 * <p>Quien implementa esta interfaz se suscribe en el notificador
 * {@code WeatherNotificationService} (capa de aplicación) y, desde ese
 * momento, recibe automáticamente cada clima consultado sin tener que
 * pedirlo. El notificador no conoce la clase concreta del observador:
 * solo sabe que puede llamar a {@link #update(Weather)}, y por eso se pueden
 * agregar observadores nuevos sin modificarlo.</p>
 *
 * <p>El dominio solo define este contrato; así no depende de la capa de
 * aplicación, que es donde vive el notificador.</p>
 */
public interface WeatherObserver {

    /**
     * Recibe el clima recién consultado.
     *
     * <p>Lo invoca {@code WeatherNotificationService} una vez por cada
     * notificación. La implementación decide qué hacer con el dato:
     * mostrarlo, guardarlo, imprimirlo o evaluarlo.</p>
     *
     * @param weather clima consultado; nunca es nulo.
     */
    void update(Weather weather);
}