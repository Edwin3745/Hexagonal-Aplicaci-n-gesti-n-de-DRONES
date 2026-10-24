package co.edu.poli.sw2.dominio.chain;

public final class CadenaViabilidad {

    private CadenaViabilidad() {
    }

    public static WeatherHandler crear() {
        WeatherHandler primero = new TemperatureHandler();
        primero.setNext(new HumidityHandler())
               .setNext(new WindHandler())
               .setNext(new PressureHandler())
               .setNext(new VisibilityHandler());
        return primero;
    }
}