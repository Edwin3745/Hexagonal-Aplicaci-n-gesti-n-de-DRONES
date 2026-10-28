package co.edu.poli.sw2.infraestructura.loader;

import co.edu.poli.sw2.aplicacion.puerto.salida.strategy.RutaPort;
import co.edu.poli.sw2.dominio.strategy.PuntoGeografico;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Adaptador de salida que implementa {@link RutaPort} leyendo el origen y el
 * destino desde un archivo JSON con este formato:
 *
 * <pre>
 * {
 *   "origen":  { "nombre": "...", "latitud": 0.0, "longitud": 0.0 },
 *   "destino": { "nombre": "...", "latitud": 0.0, "longitud": 0.0 }
 * }
 * </pre>
 *
 * <p>El archivo se lee en cada consulta, así que si se edita no hace falta
 * reiniciar la aplicación.</p>
 */
public class JsonRutaLoader implements RutaPort {

    private final Path archivo;

    /**
     * @param archivo ruta del archivo JSON.
     */
    public JsonRutaLoader(Path archivo) {
        this.archivo = archivo;
    }

    /** {@inheritDoc} */
    @Override
    public PuntoGeografico obtenerOrigen() {
        return leerPunto("origen");
    }

    /** {@inheritDoc} */
    @Override
    public PuntoGeografico obtenerDestino() {
        return leerPunto("destino");
    }

    /**
     * Lee el archivo y convierte en {@link PuntoGeografico} el objeto indicado.
     *
     * @param clave {@code "origen"} o {@code "destino"}.
     * @return el punto leído.
     * @throws UncheckedIOException si no se puede leer el archivo.
     */
    private PuntoGeografico leerPunto(String clave) {
        try {
            JSONObject punto = new JSONObject(Files.readString(archivo, StandardCharsets.UTF_8))
                    .getJSONObject(clave);
            return new PuntoGeografico(
                    punto.getString("nombre"),
                    punto.getDouble("latitud"),
                    punto.getDouble("longitud"));
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer " + archivo, e);
        }
    }
}
