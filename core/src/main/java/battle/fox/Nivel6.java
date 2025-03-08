package battle.fox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class Nivel6 implements Screen {
    private Game game;
    private Texture background;
    private SpriteBatch batch;
    private Music backgroundSound;
    public Nivel6(Game game){
        this.game = game;
    }


    @Override
    public void show() {
        background = new Texture("fondo-bosque6.png");
        batch =new SpriteBatch();

        backgroundSound = Gdx.audio.newMusic(Gdx.files.internal("background_sound_nivel6.mp3"));
        backgroundSound.setLooping(true);
        backgroundSound.setVolume(0.7f);
        backgroundSound.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();


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
        backgroundSound.dispose();
    }
}
