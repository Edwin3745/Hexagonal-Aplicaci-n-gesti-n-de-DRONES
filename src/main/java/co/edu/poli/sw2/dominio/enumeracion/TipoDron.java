package co.edu.poli.sw2.dominio.enumeracion;
/**
 * Enumera los tipos concretos de dron que maneja el sistema.
 *
 * <p>Cada constante conoce el código textual con el que se persiste en la base
 * de datos (columna discriminadora), de modo que ese literal exista en un
 * único lugar del proyecto.</p>
 */
public enum TipoDron {

    /** Dron agrícola ({@code Agricultura}); código {@code "agricultura"}. */
    AGRICULTURA("agricultura"),
    /** Dron de vigilancia ({@code Vigilancia}); código {@code "vigilancia"}. */
    VIGILANCIA("vigilancia");

    /** Código textual persistido en la columna discriminadora. */
    private final String codigo;

    /**
     * Asocia la constante con su código persistido.
     *
     * @param codigo código textual de la columna discriminadora
     */
    TipoDron(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Devuelve el código con el que este tipo se guarda en la base de datos.
     *
     * @return código persistido en la columna discriminadora de la BD.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Reconstruye el tipo a partir del código leído de la base de datos.
     *
     * <p>La comparación ignora mayúsculas/minúsculas y espacios al inicio
     * o al final.</p>
     *
     * @param codigo valor de la columna discriminadora
     * @return constante correspondiente
     * @throws IllegalArgumentException si el código es nulo o no corresponde a ningún tipo
     */
    public static TipoDron desdeCodigo(String codigo) {
        if (codigo == null) {
            throw new IllegalArgumentException("El código de tipo no puede ser nulo.");
        }
        for (TipoDron tipo : values()) {
            if (tipo.codigo.equalsIgnoreCase(codigo.trim())) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de dron desconocido: " + codigo);
    }
}