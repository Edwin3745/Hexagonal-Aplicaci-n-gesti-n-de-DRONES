package co.edu.poli.sw2.dominio.command;

/**
 * Tareas que un dron puede recibir durante una misión.
 *
 * <p>La interfaz de usuario elige un valor y el adaptador lo traduce al
 * comando concreto correspondiente.</p>
 */
public enum TipoTarea {
    /** Tomar una fotografía ({@link TomarFotografiaCommand}). */
    FOTOGRAFIA,
    /** Escanear la zona ({@link EscanearZonaCommand}). */
    ESCANEO,
    /** Medir la temperatura ({@link MedirTemperaturaCommand}). */
    TEMPERATURA
}
