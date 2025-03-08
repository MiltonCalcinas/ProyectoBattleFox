package battle.fox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class Nivel4 implements Screen {
    private Game game;
    private Texture background;
    private SpriteBatch batch;
    public Nivel4(Game game){
        this.game = game;
    }


    @Override
    public void show() {
        background = new Texture("fondo-bosque4.png");
        batch =new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();

        //inicia nivel 5
        iniciarNivel5();
    }

    private void iniciarNivel5() {
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
           game.setScreen(new Nivel5(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}
