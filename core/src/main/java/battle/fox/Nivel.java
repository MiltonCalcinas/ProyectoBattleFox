package battle.fox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;



public class Nivel implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Texture background;
    private ShapeRenderer shapeRenderer;


    private Music backgroundSound;
    private Personaje mario;
    private ConfigurarSuperficies configSuperficie;
    private Array<Enemigo> enemigos;
    private int nivel_i=1;
    private NivelesConfig nivelesConfig;

    public Nivel(Game game) {
        this.game = game; // Guardar la instancia del juego
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        background = new Texture("fondo-bosque1.png");
        enemigos= new Array<>();
        configuararEnemigos(5);

        mario = new Personaje(100,40,50,50,enemigos);

        configSuperficie = new  ConfigurarSuperficies();
        configSuperficie.establecerRectableNivel1();




        String cancionNivel1 = "background_sound_nivel1.mp3";
        configurarSonidoFondo(cancionNivel1);

        nivelesConfig = new NivelesConfig(background,configSuperficie,enemigos,mario,backgroundSound);


    }

    private void configuararEnemigos(int num) {

        for(int i=1;i<num;i++){
            enemigos.add(new Enemigo(100*i,800,50,50));
        }

    }

    private void configurarSonidoFondo(String cancion) {
        backgroundSound = Gdx.audio.newMusic(Gdx.files.internal(cancion));
        backgroundSound.setLooping(true);
        backgroundSound.setVolume(0.7f);
        backgroundSound.play();
    }




    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // movimientos del personaje  y del enemigo
        mario.manejarMovimiento(configSuperficie.ArraySuperficies(),delta);
        for(Enemigo enemigo_i : enemigos){
            enemigo_i.actualizar(configSuperficie.ArraySuperficies(),delta);
        }

        // pintar fondo
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mario.renderizar(delta,batch);
        for(Enemigo enemigo_i : enemigos){
            enemigo_i.renderizar(delta,batch);
        }
        batch.end();


        // pintar SUPERFICIES Y MARIO
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        configSuperficie.printarSuperficies(shapeRenderer);
        mario.printar(shapeRenderer);
        // prntar enemigos
        for(Enemigo enemigo_i : enemigos){
            enemigo_i.printar(shapeRenderer);
        }
        shapeRenderer.end();



        if(mario.enemigosVivos()==0){
            nivel_i = nivel_i +1;
            nivelesConfig.setNivel(this.nivel_i);
            background.dispose();
            background = nivelesConfig.getBackground();
        }


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
        background.dispose();
        backgroundSound.dispose();

    }
}
