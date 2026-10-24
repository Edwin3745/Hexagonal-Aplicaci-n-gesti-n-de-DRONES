package co.edu.poli.sw2.dominio.weather;

/**
 * Condiciones climáticas de una ciudad en un momento dado.
 *
 * <p>Reúne los cinco aspectos que se usan para decidir si un dron puede
 * operar, ya expresados en las unidades de las reglas: temperatura en °C,
 * humedad en %, velocidad del viento en km/h, presión en hPa y visibilidad
 * en km.</p>
 *
 * <p>Es inmutable: el mismo objeto se entrega a todos los observadores y
 * ninguno puede alterarlo.</p>
 */
public final class Weather {

    private final String city;
    private final double temperature;
    private final double humidity;
    private final double windSpeed;
    private final double pressure;
    private final double visibility;

    /**
     * @param city        nombre de la ciudad.
     * @param temperature temperatura en °C.
     * @param humidity    humedad relativa en %.
     * @param windSpeed   velocidad del viento en km/h.
     * @param pressure    presión atmosférica en hPa.
     * @param visibility  visibilidad en km.
     */
    public Weather(String city, double temperature, double humidity,
                   double windSpeed, double pressure, double visibility) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.visibility = visibility;
    }

    /** @return nombre de la ciudad. */
    public String getCity() {
        return city;
    }

    /** @return temperatura en °C. */
    public double getTemperature() {
        return temperature;
    }

    /** @return humedad relativa en %. */
    public double getHumidity() {
        return humidity;
    }

    /** @return velocidad del viento en km/h. */
    public double getWindSpeed() {
        return windSpeed;
    }

    /** @return presión atmosférica en hPa. */
    public double getPressure() {
        return pressure;
    }

    /** @return visibilidad en km. */
    public double getVisibility() {
        return visibility;
    }

    /**
     * @return la ciudad y los cinco aspectos con sus unidades, en una línea.
     */
    @Override
    public String toString() {
        return String.format(
                "%s: temperatura %.1f °C, humedad %.0f %%, viento %.1f km/h, "
                + "presión %.0f hPa, visibilidad %.1f km",
                city, temperature, humidity, windSpeed, pressure, visibility);
    }
}