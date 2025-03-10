package battle.fox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.awt.Rectangle;

public class MainMenuScreen implements Screen {

    private Game game;
    private Texture backgroundMenu;
    private SpriteBatch batch;
    private Rectangle startButtonBounds;
    private ShapeRenderer shapeRenderer; // Nueva instancia de ShapeRenderer

    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundMenu = new Texture("menu.png");

        // Coordenadas de StartButtonBounds
        int buttonWidth = 250, buttonHeight = 40, buttonX = 580, buttonY = 270;
        startButtonBounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);

        // Inicializa el ShapeRenderer
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundMenu, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Llamar a la función para dibujar los bordes
        drawButtonBorder();

        // cambiar a ventana de juego
        presskEnter();
        clickButtonStart();
    }

    private void clickButtonStart() {
        // Verifica si el usuario ha tocado la pantalla o ha hecho clic con el ratón
        if (Gdx.input.justTouched()) {
            // Obtiene las coordenadas del toque/clic en la pantalla (en coordenadas de LibGDX)
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            // Corrige la coordenada Y, ya que en LibGDX el eje Y está invertido
            touchPos.y = Gdx.graphics.getHeight() - touchPos.y;
            // Verifica si ocurrió dentro del área
            if (startButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new Nivel(game));
            }
        }
    }

    private void presskEnter() {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(new Nivel(game));
        }
    }

    // Nueva función para dibujar el borde del botón
    private void drawButtonBorder() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // Inicia el dibujo de contornos
        shapeRenderer.setColor(1, 0, 0, 1); // Establece el color del borde (Rojo en este caso)
        shapeRenderer.rect(startButtonBounds.x, startButtonBounds.y, startButtonBounds.width, startButtonBounds.height);
        shapeRenderer.end(); // Finaliza el dibujo
    }

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
        backgroundMenu.dispose();
        shapeRenderer.dispose(); // No olvides liberar los recursos de ShapeRenderer
    }
}
