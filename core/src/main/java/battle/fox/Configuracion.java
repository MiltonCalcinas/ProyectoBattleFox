package battle.fox;

public class Configuracion {
    private static Configuracion instancia;
    private float volumen = 0.7f;

    private Configuracion() {} // Constructor privado

    public static Configuracion getInstancia() {
        if (instancia == null) {
            instancia = new Configuracion();
        }
        return instancia;
    }

    public float getVolumen() {
        return volumen;
    }

    public void setVolumen(float nuevoVolumen) {
        volumen = Math.max(0.0f, Math.min(1.0f, nuevoVolumen));
    }
}
