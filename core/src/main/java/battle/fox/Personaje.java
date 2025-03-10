package battle.fox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;

public class Personaje {
    private Rectangle bounds;
    private Animation<TextureRegion> walkAnimation;
    private float stateTime;
    private float velocidadSalto = 10f, gravedad = -0.5f, velocidadY = 0f;
    private boolean enElSuelo = true;
    private float tiempoSalto = 0f;
    private float tiempoMaxSalto = 2f;
    private TextureRegion frameReposo;
    private boolean mirandoDerecha = false;
    private Array<BolaDeFuego> bolasDeFuego = new Array<>();
    private Array<Enemigo> enemigos;
    private  boolean colsionConEnemigo = false;

    public Personaje(float x, float y, float width, float height, Array<Enemigo> enemigos) {
        this.bounds = new Rectangle(x, y, width, height);
        this.enemigos = enemigos;
        configurarAnimacion();
    }

    private void configurarAnimacion() {
        Texture paso1 = new Texture(Gdx.files.internal("lobo_paso1.png"));
        Texture paso2 = new Texture(Gdx.files.internal("lobo_paso2.png"));
        Texture paso3 = new Texture(Gdx.files.internal("lobo_paso3.png"));
        Texture reposo = new Texture(Gdx.files.internal("reposo.png"));

        frameReposo = new TextureRegion(reposo);

        Array<TextureRegion> walkFrames = new Array<>();
        walkFrames.add(new TextureRegion(paso1));
        walkFrames.add(new TextureRegion(paso2));
        walkFrames.add(new TextureRegion(paso3));

        walkAnimation = new Animation<>(0.2f, walkFrames);
        stateTime = 0f;
    }

    public void manejarMovimiento(Array<Rectangle> superficies, float delta) {
        float movimientoX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            movimientoX = 5;
            mirandoDerecha = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            movimientoX = -5;
            mirandoDerecha = false;
        }

        Rectangle nuevoBounds = new Rectangle(bounds);
        nuevoBounds.x += movimientoX;

        boolean colisionLateral = false;
        for (Rectangle superficie : superficies) {
            if (nuevoBounds.overlaps(superficie)) {
                colisionLateral = true;
                break;
            }
        }
        boolean dentroLimites = (bounds.x + movimientoX > 0) && (bounds.x + movimientoX + bounds.width < Gdx.graphics.getWidth() - 5);

        if ((!colisionLateral || !enElSuelo) && dentroLimites) {
            bounds.x += movimientoX;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            disparar();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (enElSuelo) {
                velocidadY = velocidadSalto;
                enElSuelo = false;
                tiempoSalto = 0;
            } else if (tiempoSalto < tiempoMaxSalto) {
                velocidadY += 0.2f;
                tiempoSalto += delta;
            }
        } else {
            tiempoSalto = tiempoMaxSalto;
        }

        velocidadY += gravedad;
        bounds.y += velocidadY;

        enElSuelo = false;
        for (Rectangle superficie : superficies) {
            if (bounds.overlaps(superficie)) {
                if (velocidadY < 0) {
                    float bordeInferiorPersonaje = bounds.y;
                    float bordeSuperiorPlataforma = superficie.y + superficie.height;
                    float bordeInferiorAnterior = bordeInferiorPersonaje - velocidadY;
                    if (bordeInferiorAnterior >= bordeSuperiorPlataforma) {
                        bounds.y = superficie.y + superficie.height;
                        velocidadY = 0;
                        enElSuelo = true;
                        tiempoSalto = 0;
                    }
                }
            }

            // colision con enemigo

            // Comprobar colisión con enemigos y eliminar al personaje y enemigos
            for (int i = enemigos.size - 1; i >= 0; i--) {
                Enemigo enemigo = enemigos.get(i);
                if (bounds.overlaps(enemigo.getBounds())) {
                    enemigos.clear(); // Eliminar todos los enemigos
                    bounds.set(-100, -100, 0, 0); // Mueve al personaje fuera de la pantalla
                    colsionConEnemigo = true;
                    break;
                }
            }
        }
    }

    public boolean isDead(){

        return colsionConEnemigo;
    }

    public void disparar() {
        bolasDeFuego.add(new BolaDeFuego(bounds.x, bounds.y + bounds.height / 2, mirandoDerecha));
    }

    public void actualizarBalas(float delta) {
        for (int i = bolasDeFuego.size - 1; i >= 0; i--) {
            BolaDeFuego bola = bolasDeFuego.get(i);
            bola.actualizar(delta);
            if (!bola.estaActiva()) {
                bolasDeFuego.removeIndex(i);
            }
        }

        for (int i = enemigos.size - 1; i >= 0; i--) {
            Enemigo enemigo = enemigos.get(i);
            for (int j = bolasDeFuego.size - 1; j >= 0; j--) {
                if (bolasDeFuego.get(j).getBounds().overlaps(enemigo.getBounds())) {
                    enemigos.removeIndex(i);
                    bolasDeFuego.removeIndex(j);
                    break;
                }
            }
        }
    }

    public void renderizar(float delta, SpriteBatch batch) {


        stateTime += delta;
        actualizarBalas(delta);

        TextureRegion frameActual = (!Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) ? frameReposo : walkAnimation.getKeyFrame(stateTime, true);

        if (mirandoDerecha && !frameActual.isFlipX()) {
            frameActual.flip(true, false);
        } else if (!mirandoDerecha && frameActual.isFlipX()) {
            frameActual.flip(true, false);
        }

        batch.draw(frameActual, bounds.x, bounds.y, bounds.width, bounds.height);

        for (BolaDeFuego bola : bolasDeFuego) {
            bola.renderizar(batch);
        }
    }

    public void setX(float x) {
        bounds.x = x;
    }

    public void setY(float y) {
        bounds.y = y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void printar(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public int enemigosVivos(){
        return enemigos.size;
    }

    public void setEnemigos(Array<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }


}
