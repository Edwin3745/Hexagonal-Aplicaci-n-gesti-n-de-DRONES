package co.edu.poli.sw2.aplicacion.puerto.entrada.weather;

import co.edu.poli.sw2.aplicacion.puerto.entrada.OperacionFallidaException;
import co.edu.poli.sw2.dominio.weather.Weather;

/**
 * Puerto de entrada: consultar el clima actual de una ciudad y avisar a los
 * drones suscritos.
 *
 * <p>Es lo que invoca el adaptador de la interfaz cuando el usuario escribe
 * una ciudad y pulsa "Consultar clima". Quien lo implemente debe, en este
 * orden: ubicar la ciudad, obtener su clima y notificarlo a todos los
 * observadores registrados.</p>
 */
public interface GetWeatherByCityUseCase {

    /**
     * Consulta el clima de la ciudad y lo notifica a los observadores.
     *
     * @param city nombre de la ciudad, por ejemplo {@code "Bogota"} o
     *             {@code "Tunja,CO"}.
     * @return el clima consultado, el mismo que recibieron los observadores.
     * @throws OperacionFallidaException si la ciudad está vacía, no existe o
     *         la API no responde; el mensaje es apto para mostrarse al usuario.
     */
    Weather execute(String city);
}
