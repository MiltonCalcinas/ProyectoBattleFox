package battle.fox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;



public class Personaje {
    private Rectangle bounds; // Área de colisión
    private Animation<TextureRegion> walkAnimation;
    private float stateTime;
    private float velocidadSalto =10f,gravedad=-0.5f,velocidadY=0f;
    private boolean enElSuelo =  true;
    private float tiempoSalto = 0f;  // Contador de tiempo de salto
    private float tiempoMaxSalto = 2f;  // Máximo tiempo de salto en segundos

    public Personaje(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
        configurarAnimacion();
    }

    private void configurarAnimacion() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("Mario_and_Enemies.pack"));
        TextureRegion bigMarioRegion = atlas.findRegion("big_mario");

        Array<TextureRegion> walkFrames = new Array<>();
        for (int i = 0; i < 4; i++) {
            walkFrames.add(new TextureRegion(bigMarioRegion, i * 16, 0, 16, bigMarioRegion.getRegionHeight()));
        }
        walkAnimation = new Animation<>(0.1f, walkFrames);
        stateTime = 0f;
    }



    public void manejarMovimiento(Array<Rectangle> superficies, float delta) {
        // Movimiento horizontal con detección de colisiones laterales
        float movimientoX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movimientoX = 5;  // Intentar mover a la derecha
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movimientoX = -5;  // Intentar mover a la izquierda
        }

        // Verificar colisión lateral antes de mover el personaje
        Rectangle nuevoBounds = new Rectangle(bounds);
        nuevoBounds.x += movimientoX; // Simular el movimiento

        boolean colisionLateral = false;
        for (Rectangle superficie : superficies) {
            if (nuevoBounds.overlaps(superficie)) {
                colisionLateral = true;
                break;  // Salir del bucle si hay colisión
            }
        }

        // Solo mover si no hay colisión lateral
        if (!colisionLateral) {
            bounds.x += movimientoX;
        }

        // Manejo de salto con duracion  variable
        //if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && enElSuelo) {
        //    velocidadY = velocidadSalto; // Iniciar salto
        //    enElSuelo = false; // Ya no está en el suelo
        //}
        // Manejo de salto con duración variable
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (enElSuelo) {
                velocidadY = velocidadSalto; // Iniciar salto
                enElSuelo = false;
                tiempoSalto = 0;  // Reiniciar el contador de salto
            } else if (tiempoSalto < tiempoMaxSalto) {
                velocidadY += 0.2f;  // Aumentar la velocidad de subida
                tiempoSalto += delta;  // Acumular tiempo de salto
            }
        } else {
            tiempoSalto = tiempoMaxSalto;  // Detener el incremento de salto si se suelta la tecla
        }


        // Aplicar gravedad
        velocidadY += gravedad;
        bounds.y += velocidadY;

        // Verificar colisión con el suelo o plataformas
        enElSuelo = false;  // Suponer que está en el aire hasta que colisione
        for (Rectangle superficie : superficies) {
            if (bounds.overlaps(superficie)) {
                // Si está cayendo, detener la caída y colocarlo sobre la plataforma
                if (velocidadY < 0) {
                    bounds.y = superficie.y + superficie.height;
                    velocidadY = 0;
                    enElSuelo = true;
                    tiempoSalto = 0; // Reiniciar tiempo de salto al tocar el suelo
                } else if (velocidadY > 0) {  // Si está subiendo (colisión con un techo)
                    bounds.y = superficie.y - bounds.height; // Colocarlo justo debajo del techo
                    velocidadY = 0; // Detener el ascenso
                    tiempoSalto = tiempoMaxSalto;  // Bloquear más impulso
                }
            }
        }
    }



    public void renderizar(float delta,SpriteBatch batch) {
        stateTime+=delta;
        TextureRegion frameActual = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(frameActual, bounds.x, bounds.y, bounds.width, bounds.height);
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

    public void verificarColisiones(Array<Rectangle> superficies) {
        // Verificar colisiones con todas las superficies
        for (Rectangle superficie : superficies) {
            // Si el personaje está moviéndose hacia la derecha
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                // Verificar si el personaje colisiona con la superficie
                if (bounds.x + bounds.width > superficie.x && bounds.x < superficie.x + superficie.width &&
                    bounds.y + bounds.height > superficie.y && bounds.y < superficie.y + superficie.height) {
                    bounds.x = superficie.x - bounds.width; // Detener al personaje en la colisión
                    break; // Salir del bucle para evitar colisiones múltiples
                }
            }

            // Si el personaje está moviéndose hacia la izquierda
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                // Verificar si el personaje colisiona con la superficie
                if (bounds.x < superficie.x + superficie.width && bounds.x + bounds.width > superficie.x &&
                    bounds.y + bounds.height > superficie.y && bounds.y < superficie.y + superficie.height) {
                    bounds.x = superficie.x + superficie.width; // Detener al personaje en la colisión
                    break; // Salir del bucle para evitar colisiones múltiples
                }
            }
        }
    }


    public void printar(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(bounds.x,bounds.y,bounds.width,bounds.height);
    }
}
