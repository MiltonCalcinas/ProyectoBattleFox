package battle.fox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Rectangle;

public class Nivel1 implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Texture background,playerTexture;
    private ShapeRenderer shapeRenderer;
    int[][] datos;
    private Array<Rectangle> superficies;
    private Rectangle marioRectangle; // Área de colisión del personaje
    private Animation<TextureRegion> walkMarioAnimation;
    private float stateTime;

    public Nivel1(Game game) {
        this.game = game; // Guardar la instancia del juego
        batch = new SpriteBatch();
        background = new Texture("fondo-bosque1.png");
        shapeRenderer = new ShapeRenderer();

        configurarSuperficies();
        configurarPersonaje();

    }
    private void configurarPersonaje(){
        TextureAtlas altlas = new TextureAtlas(Gdx.files.internal("Mario_and_Enemies.pack"));
        TextureRegion bigMarioRegion = altlas.findRegion("big_mario");

        // Animación caminar mario
        Array<TextureRegion> walkMario = new Array<>();
        for (int i = 0; i < 4; i++) {
            walkMario.add(new TextureRegion(bigMarioRegion, i * 16, 0, 16, bigMarioRegion.getRegionHeight()));
        }
        walkMarioAnimation = new Animation<>(0.1f,walkMario);

        marioRectangle = new Rectangle(100,40,50,50);
        stateTime = 0f; // Iniciar el tiempo de animación
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
             {930,135,125,65},//6
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
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // movimientos del personaje
        movimientoPersonaje();



        // pintar fondo
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stateTime+= delta;
        TextureRegion currentFrame = walkMarioAnimation.getKeyFrame(stateTime,true);
        batch.draw(currentFrame,marioRectangle.x,marioRectangle.y,marioRectangle.width,marioRectangle.height);
        batch.end();
        // Verificar colisiones
        verificarColisiones();

        // pintar superificies del juego
        pintarSuperficies();

        // nivel 2
        iniciarNivel2();


    }

    private void iniciarNivel2() {
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            game.setScreen(new Nivel2(game));
        }
    }

    private void movimientoPersonaje() {
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            marioRectangle.x+=5;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            marioRectangle.x-=5;
        }

    }

    private void verificarColisiones() {
        // Pared izquierda (superficie en la posición x = 0)
        if (marioRectangle.x <= superficies.get(1).x + superficies.get(1).width) {
            marioRectangle.x = superficies.get(1).x + superficies.get(1).width; // Detener al personaje
        }

        // Pared derecha (superficie en la posición x = 800)
        if (marioRectangle.x + marioRectangle.width >= superficies.get(2).x) {
            marioRectangle.x = superficies.get(2).x - marioRectangle.width; // Detener al personaje
        }
    }


    private void pintarSuperficies() {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);

        for(int i=0;i<superficies.size;i++){
            shapeRenderer.rect(superficies.get(i).x,superficies.get(i).y,superficies.get(i).width,superficies.get(i).height);
        }
        shapeRenderer.rect(marioRectangle.x,marioRectangle.y,marioRectangle.width,marioRectangle.height);
        shapeRenderer.end();

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


    }
}
