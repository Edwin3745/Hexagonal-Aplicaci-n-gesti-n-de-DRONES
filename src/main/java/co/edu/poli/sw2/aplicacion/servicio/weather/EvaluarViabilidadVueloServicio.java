package co.edu.poli.sw2.aplicacion.servicio.weather;

import co.edu.poli.sw2.aplicacion.puerto.entrada.weather.EvaluarViabilidadVueloUseCase;
import co.edu.poli.sw2.dominio.chain.CadenaViabilidad;
import co.edu.poli.sw2.dominio.chain.ResultadoViabilidad;
import co.edu.poli.sw2.dominio.chain.WeatherHandler;
import co.edu.poli.sw2.dominio.weather.Weather;

/**
 * Servicio de aplicación que implementa el caso de uso de evaluar la
 * viabilidad de vuelo.
 *
 * <p>No contiene reglas de negocio: solo recibe el clima y se lo entrega a la
 * cadena de responsabilidad del dominio, que es quien decide.</p>
 */
public class EvaluarViabilidadVueloServicio implements EvaluarViabilidadVueloUseCase {

    /** Primer eslabón de la cadena de validaciones climáticas. */
    private final WeatherHandler cadena;

    /** Crea el servicio con la cadena estándar del enunciado. */
    public EvaluarViabilidadVueloServicio() {
        this(CadenaViabilidad.crear());
    }

    /**
     * Permite inyectar otra cadena (por ejemplo, en pruebas).
     *
     * @param cadena primer handler de la cadena a utilizar.
     */
    public EvaluarViabilidadVueloServicio(WeatherHandler cadena) {
        if (cadena == null) {
            throw new IllegalArgumentException("La cadena de validación no puede ser nula.");
        }
        this.cadena = cadena;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResultadoViabilidad evaluar(Weather weather) {
        if (weather == null) {
            throw new IllegalArgumentException("El clima a evaluar no puede ser nulo.");
        }
        return cadena.handle(weather);
    }
}