package battle.fox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Game;


public class Opciones implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Texture background;

    private Rectangle botonVolumenMas;
    private Rectangle botonVolumenMenos;
    private Rectangle botonVolver;
    private ShapeRenderer shapeRenderer;

    private float volumen = 0.7f; // Volumen inicial (rango 0.0 - 1.0)

    public Opciones(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        background = new Texture("opciones.jpeg"); // Asegúrate de tener esta imagen

        // Posicionar los botones en el centro, separados por 100px verticalmente
        int pantallaAncho = Gdx.graphics.getWidth();
        int pantallaAlto = Gdx.graphics.getHeight();
        int botonAncho = 150;
        int botonAlto = 50;
        int centroX = 630;
        int centroY =440;

        botonVolumenMas = new Rectangle(centroX, centroY, botonAncho, botonAlto);
        botonVolumenMenos = new Rectangle(centroX, centroY - 90, botonAncho, botonAlto);
        botonVolver = new Rectangle(centroX, centroY - 210, botonAncho, botonAlto);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        dibujarRectangulos();
        detectarClick();
    }

    private void dibujarRectangulos() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // Inicia el dibujo de contornos
        shapeRenderer.setColor(1, 0, 0, 1); // Establece el color del borde (Rojo en este caso)
        shapeRenderer.rect(botonVolumenMas.x, botonVolumenMas.y, botonVolumenMas.width, botonVolumenMas.height);
        shapeRenderer.rect(botonVolumenMenos.x, botonVolumenMenos.y, botonVolumenMenos.width, botonVolumenMenos.height);
        shapeRenderer.rect(botonVolver.x, botonVolver.y, botonVolver.width, botonVolver.height);
        shapeRenderer.end(); // Finaliza el dibujo
    }

    private void detectarClick() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Convertir coordenadas

            if (botonVolumenMas.contains(touchX, touchY)) {
                ajustarVolumen(0.1f);
            } else if (botonVolumenMenos.contains(touchX, touchY)) {
                ajustarVolumen(-0.1f);
            } else if (botonVolver.contains(touchX, touchY)) {
                game.setScreen(new MainMenuScreen(game)); // Regresar al menú principal
            }
        }
    }

    private void ajustarVolumen(float cambio) {
        float nuevoVolumen = Configuracion.getInstancia().getVolumen() + cambio;
        Configuracion.getInstancia().setVolumen(nuevoVolumen);
        System.out.println("Volumen actual: " + Configuracion.getInstancia().getVolumen());
    }



    @Override
    public void show() {}


    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}
