package co.edu.poli.sw2.dominio.weather;

/**
 * Observador del patrón Observer: todo lo que quiera enterarse de un nuevo
 * reporte del clima.
 *
 * <p>Quien implementa esta interfaz se registra en un {@link WeatherSubject}
 * y, desde ese momento, recibe automáticamente cada clima consultado sin
 * tener que pedirlo. El sujeto no conoce la clase concreta del observador:
 * solo sabe que puede llamar a {@link #update(Weather)}, y por eso se pueden
 * agregar observadores nuevos sin modificarlo.</p>
 */
public interface WeatherObserver {

    /**
     * Recibe el clima recién consultado.
     *
     * <p>Lo invoca el {@link WeatherSubject} una vez por cada notificación.
     * La implementación decide qué hacer con el dato: mostrarlo, guardarlo,
     * imprimirlo o evaluarlo.</p>
     *
     * @param weather clima consultado; nunca es nulo.
     */
    void update(Weather weather);
}