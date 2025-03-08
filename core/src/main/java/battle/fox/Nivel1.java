package battle.fox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;



public class Nivel1 implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Texture background;
    private ShapeRenderer shapeRenderer;
    int[][] datos;
    private Array<Rectangle> superficies;

    private Music backgroundSound;
    private Personaje mario;

    public Nivel1(Game game) {
        this.game = game; // Guardar la instancia del juego


    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("fondo-bosque1.png");
        shapeRenderer = new ShapeRenderer();


        configurarSuperficies();
        mario = new Personaje(100,40,50,50);
        configurarSonidoFondo();

    }

    private void configurarSonidoFondo() {
        backgroundSound = Gdx.audio.newMusic(Gdx.files.internal("background_sound_nivel1.mp3"));
        backgroundSound.setLooping(true);
        backgroundSound.setVolume(0.7f);
        backgroundSound.play();
    }


    private void configurarSuperficies() {
         // datos son las coordenadas de x y con el width y heigth de las superficies
        // width, height,x,y
         datos = new int[][]{{ 0, 0,Gdx.graphics.getWidth(), 40}, // suelo
             { 0, 0,55, Gdx.graphics.getHeight()}, // pared izq
             { 1320, 0,100, Gdx.graphics.getHeight()},// pared der
             {55,300,365,70}, // 1
             {360,250,215,65}, // 2
             {565,195,100,65},// 3
             {665,135,110,65}, // 4
             {775,90,155,55},// 5
             //{930,135,125,65},//6
             {1000,200,130,115},//7
             {1100,200,200,60}, // 8
             {1270,200,130,115},//9
             {260,40,218,65}//10
         }; // 5

         // superficies
        superficies = new Array<>();
        for (int i =0;i<datos.length;i++){
            superficies.add(new Rectangle(datos[i][0],datos[i][1],datos[i][2],datos[i][3]));

        }



    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // movimientos del personaje
        mario.manejarMovimiento(superficies,delta);
        // Verificar colisiones
        //mario.verificarColisiones(superficies);

        // pintar fondo
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mario.renderizar(delta,batch);
        batch.end();


        // pintar SUPERFICIES Y MARIO
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        printarSuperficies();
        mario.printar(shapeRenderer);
        shapeRenderer.end();
        // nivel 2
        iniciarNivel2();


    }

    private void iniciarNivel2() {
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            backgroundSound.stop();
            dispose();
            game.setScreen(new Nivel2(game));
        }
    }



    private void printarSuperficies() {

        shapeRenderer.setColor(1, 0, 0, 1);
        //printar superficies
        for(int i=0;i<superficies.size;i++){
            shapeRenderer.rect(superficies.get(i).x,superficies.get(i).y,superficies.get(i).width,superficies.get(i).height);
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
