package co.edu.poli.sw2.vista;

import co.edu.poli.sw2.infraestructura.ui.AppDron;

/**
 * Punto de entrada del programa.
 *
 * <p>No pertenece a ninguna capa de la arquitectura hexagonal: es solo el
 * lanzador de la raíz de composición, {@link AppDron}.</p>
 *
 * <p>Solo arranca {@link AppDron}. Existe como clase aparte porque JavaFX no
 * permite que la clase con el método {@code main} de un JAR ejecutable extienda
 * {@code Application} cuando JavaFX no está en el module-path: separar el
 * lanzador evita ese error al empaquetar la aplicación.</p>
 */
public class App {

    /**
     * Arranca la aplicación delegando en {@link AppDron#main(String[])}.
     *
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        AppDron.main(args);
    }
}
