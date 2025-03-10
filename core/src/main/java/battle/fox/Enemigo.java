package battle.fox;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;
public class Enemigo {
    private Rectangle bounds; // El rectángulo que define las dimensiones del enemigo
    private float velocidadX; // Velocidad de movimiento en el eje X
    private float velocidadY; // Velocidad de caída (gravedad)
    private boolean enElSuelo; // Para verificar si el enemigo está sobre el suelo
    private final float gravedad = -9.8f; // Valor de la gravedad
    private final float velocidadSalto = 5f; // Velocidad de salto cuando rebota
    private Texture texturaEnemigo;
    private Animation<TextureRegion> enemigoAnimation;
    private float stateTime;


    public Enemigo(float x, float y, float width, float height) {
        bounds = new Rectangle(x, y, width, height);
        velocidadX = 2f; // Velocidad horizontal del enemigo
        velocidadY = 0f; // Inicialmente el enemigo no se mueve en el eje Y
        configurarAnimacion();
    }

    private void configurarAnimacion() {

        Texture paso1 = new Texture(Gdx.files.internal("ave1.png"));
        Texture paso2 = new Texture(Gdx.files.internal("ave2.png"));

        Array<TextureRegion> enemigoFrames = new Array<>();
        enemigoFrames.add(new TextureRegion(paso1));
        enemigoFrames.add(new TextureRegion(paso2));

        enemigoAnimation = new Animation<>(0.3f, enemigoFrames);
        stateTime = 0f;
    }

    public void actualizar(Array<Rectangle> superficies, float delta) {
        // Movimiento horizontal (rebotando en superficies)
        bounds.x += velocidadX;

        // Verificar colisiones laterales
        for (Rectangle superficie : superficies) {
            if (bounds.overlaps(superficie)) {
                // Si el enemigo colide lateralmente, rebotar en el eje X
                velocidadX = -velocidadX; // Invertir la dirección del movimiento horizontal
                bounds.x += velocidadX;  // Mover al enemigo a la nueva posición
            }
        }

        // Gravedad (caída)
        velocidadY += gravedad * delta;  // Aplicar la gravedad

        // Actualizar la posición del enemigo en el eje Y
        bounds.y += velocidadY;

        // Verificar colisión con el suelo o plataformas
        enElSuelo = false; // Suponer que está en el aire
        for (Rectangle superficie : superficies) {
            if (bounds.overlaps(superficie)) {
                // Si el enemigo cae, detener la caída y colocarlo sobre la plataforma
                if (velocidadY < 0) {  // Solo lo consideramos si está cayendo
                    float bordeInferiorPersonaje = bounds.y;
                    float bordeSuperiorPlataforma = superficie.y + superficie.height;
                    float bordeInferiorAnterior = bordeInferiorPersonaje - velocidadY;
                    if (bordeInferiorAnterior >= bordeSuperiorPlataforma) {
                        bounds.y = superficie.y + superficie.height;
                        velocidadY = 0;
                        enElSuelo = true;
                    }
                }
            }
        }

        // Si no está sobre el suelo, puede seguir cayendo
        if (!enElSuelo) {
            velocidadY = -2f; // Asegurarse de que sigue cayendo si no está sobre el suelo
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void renderizar(float delta,SpriteBatch batch,boolean fin) {
        if(fin){
            return;
        }
        stateTime+=delta;
        TextureRegion frameActual = enemigoAnimation.getKeyFrame(stateTime, true);

        if(velocidadX<0 && !frameActual.isFlipX()){
            frameActual.flip(true,false);
        }
        else if(velocidadX>0  && frameActual.isFlipX()){
            frameActual.flip(true,false);
        }

        batch.draw(frameActual, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void printar(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(bounds.x,bounds.y,bounds.width,bounds.height);
    }
}
