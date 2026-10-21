package co.edu.poli.sw2.dominio.weather;

/**
 * Condiciones climáticas de una ciudad en un momento dado.
 *
 * <p>Es la información que el {@link WeatherSubject} entrega a cada
 * {@link WeatherObserver} y la que evalúa la cadena de validación del
 * paquete {@code dominio.chain}. Reúne los cinco aspectos que se usan para
 * decidir si un dron puede operar.</p>
 *
 * <p>Los valores llegan ya expresados en las unidades de las reglas de
 * negocio, de modo que ninguna clase del dominio tiene que conocer el
 * formato de la API externa:</p>
 * <ul>
 *   <li>temperatura en grados Celsius,</li>
 *   <li>humedad relativa en porcentaje,</li>
 *   <li>velocidad del viento en kilómetros por hora,</li>
 *   <li>presión atmosférica en hectopascales,</li>
 *   <li>visibilidad en kilómetros.</li>
 * </ul>
 *
 * <p>La clase es inmutable: todos sus campos son {@code final} y no tiene
 * métodos {@code set}. Como el mismo objeto se entrega a varios observers,
 * ninguno puede alterar lo que reciben los demás.</p>
 *
 * <p>No valida rangos a propósito. Un viento de 60 km/h es un dato real y
 * válido; decidir si impide operar es responsabilidad de la cadena de
 * handlers, no de quien guarda el dato.</p>
 */
public final class Weather {

    /** Nombre de la ciudad consultada. */
    private final String city;

    /** Temperatura en grados Celsius. */
    private final double temperature;

    /** Humedad relativa en porcentaje (0 a 100). */
    private final double humidity;

    /** Velocidad del viento en kilómetros por hora. */
    private final double windSpeed;

    /** Presión atmosférica en hectopascales (hPa). */
    private final double pressure;

    /** Visibilidad en kilómetros. */
    private final double visibility;

    /**
     * Crea un registro del clima con los cinco aspectos ya convertidos a las
     * unidades de las reglas de negocio.
     *
     * @param city        nombre de la ciudad; no puede ser nulo ni vacío.
     * @param temperature temperatura en °C.
     * @param humidity    humedad relativa en %.
     * @param windSpeed   velocidad del viento en km/h.
     * @param pressure    presión atmosférica en hPa.
     * @param visibility  visibilidad en km.
     * @throws IllegalArgumentException si la ciudad es nula o está vacía.
     */
    public Weather(String city, double temperature, double humidity,
                   double windSpeed, double pressure, double visibility) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("La ciudad del clima no puede estar vacía.");
        }
        this.city = city.trim();
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.visibility = visibility;
    }

    /** @return nombre de la ciudad consultada. */
    public String getCity() {
        return city;
    }

    /** @return temperatura en grados Celsius. */
    public double getTemperature() {
        return temperature;
    }

    /** @return humedad relativa en porcentaje. */
    public double getHumidity() {
        return humidity;
    }

    /** @return velocidad del viento en kilómetros por hora. */
    public double getWindSpeed() {
        return windSpeed;
    }

    /** @return presión atmosférica en hectopascales. */
    public double getPressure() {
        return pressure;
    }

    /** @return visibilidad en kilómetros. */
    public double getVisibility() {
        return visibility;
    }

    /**
     * Resume el clima en una línea legible, útil para la consola y el log.
     *
     * @return texto con la ciudad y los cinco aspectos con sus unidades.
     */
    @Override
    public String toString() {
        return String.format(
                "%s: temperatura %.1f °C, humedad %.0f %%, viento %.1f km/h, "
                + "presión %.0f hPa, visibilidad %.1f km",
                city, temperature, humidity, windSpeed, pressure, visibility);
    }
}