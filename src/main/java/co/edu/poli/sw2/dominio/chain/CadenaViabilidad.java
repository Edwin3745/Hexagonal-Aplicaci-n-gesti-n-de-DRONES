package co.edu.poli.sw2.dominio.chain;

/**
 * Construye la cadena de reglas climáticas (Client/constructor del patrón
 * Chain of Responsibility).
 *
 * <p>Centraliza el orden de los eslabones para que quien evalúa el clima solo
 * conozca el primer {@link WeatherHandler}. Es una clase utilitaria: no se
 * puede instanciar.</p>
 */
public final class CadenaViabilidad {

    private CadenaViabilidad() {
    }

    /**
     * Crea una cadena nueva con el orden: temperatura, humedad, viento,
     * presión atmosférica y visibilidad.
     *
     * @return el primer eslabón ({@link TemperatureHandler}); la evaluación
     *         empieza llamando a {@link WeatherHandler#handle(co.edu.poli.sw2.dominio.weather.Weather)} sobre él.
     */
    public static WeatherHandler crear() {
        WeatherHandler primero = new TemperatureHandler();
        primero.setNext(new HumidityHandler())
               .setNext(new WindHandler())
               .setNext(new PressureHandler())
               .setNext(new VisibilityHandler());
        return primero;
    }
}