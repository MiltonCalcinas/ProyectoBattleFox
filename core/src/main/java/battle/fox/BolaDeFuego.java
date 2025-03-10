package battle.fox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

public class BolaDeFuego {
    private Texture textura;
    private Rectangle bounds;
    private float velocidadX;
    private boolean activa;
    private float tiempoVida;
    private final float TIEMPO_MAXIMO = 3f; // 3 segundos de vida máxima
    public BolaDeFuego(float x, float y, boolean direccionDerecha) {
        textura = new Texture(Gdx.files.internal("bola de fuego.png"));
        bounds = new Rectangle(x, y, 30, 30); // Ajusta tamaño según la imagen
        velocidadX = direccionDerecha ? 7 : -7; // Velocidad según dirección
        activa = true;
        tiempoVida = 0;
    }

    public void actualizar(float delta) {
        bounds.x += velocidadX;
        tiempoVida += delta;

        // Desactivar si sale de la pantalla o supera el tiempo máximo
        if (bounds.x > Gdx.graphics.getWidth() || bounds.x < 0 || tiempoVida > TIEMPO_MAXIMO) {
            activa = false;
        }
    }

    public void renderizar(SpriteBatch batch) {
        if (activa) {
            batch.draw(textura, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public boolean estaActiva() {
        return activa;
    }

    public Rectangle getBounds(){
        return bounds;
    }
}
