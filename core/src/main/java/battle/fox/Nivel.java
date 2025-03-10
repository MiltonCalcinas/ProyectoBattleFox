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
import com.badlogic.gdx.math.Rectangle;



public class Nivel implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Texture background;
    private ShapeRenderer shapeRenderer;

    private boolean fin=false;
    private Music backgroundSound;
    private Personaje lobo;
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

        lobo = new Personaje(100,40,50,50,enemigos);

        configSuperficie = new  ConfigurarSuperficies();
        configSuperficie.establecerRectableNivel1();




        String cancionNivel1 = "background_sound_nivel1.mp3";
        configurarSonidoFondo(cancionNivel1);

        nivelesConfig = new NivelesConfig(background,configSuperficie,enemigos,lobo,backgroundSound);


    }

    private void configuararEnemigos(int num) {

        for(int i=1;i<num;i++){
            enemigos.add(new Enemigo(100*i,800,50,50));
        }

    }

    private void configurarSonidoFondo(String cancion) {
        backgroundSound = Gdx.audio.newMusic(Gdx.files.internal(cancion));
        backgroundSound.setLooping(true);
        backgroundSound.setVolume(Configuracion.getInstancia().getVolumen());
        backgroundSound.play();
    }




    @Override
    public void render(float delta) {
        // finalizar al completar o al matar al personaje
        fin = fin || lobo.isDead() ;

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // movimientos del personaje  y del enemigo
        lobo.manejarMovimiento(configSuperficie.ArraySuperficies(), delta);
        for (Enemigo enemigo_i : enemigos) {
            enemigo_i.actualizar(configSuperficie.ArraySuperficies(), delta);

        }
        // pintar fondo
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        lobo.renderizar(delta,batch);
        if(fin){
            lobo.setX(-100);
        }


        for(Enemigo enemigo_i : enemigos){
            enemigo_i.renderizar(delta,batch,fin);
        }

        batch.end();

        // pintar SUPERFICIES Y lobo
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        //configSuperficie.printarSuperficies(shapeRenderer);
        //lobo.printar(shapeRenderer);

        //for(Enemigo enemigo_i : enemigos){
        //    enemigo_i.printar(shapeRenderer);
        //}

        //shapeRenderer.end();

        cambiarDeNivel();
    }

    private void cambiarDeNivel() {
        if(lobo.enemigosVivos()==0 && !lobo.isDead() && !fin){
            nivel_i = nivel_i +1;
            if(nivel_i==7){
                fin = true;
                return;
            }
            nivelesConfig.setNivel(this.nivel_i);
            background.dispose();
            background = nivelesConfig.getBackground();
        }
        if(fin){

            nivelesConfig.finJuego();
            background.dispose();
            background = nivelesConfig.getBackground();
            menuFinJuego();
        }




    }

    private void menuFinJuego() {
        if(Gdx.input.justTouched()){
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Convertir coordenadas

            Rectangle botonMenu = configSuperficie.ArraySuperficies().get(0); // Ajusta la posición y tamaño
            Rectangle botonSalir = configSuperficie.ArraySuperficies().get(1);


            if (botonMenu.contains(touchX, touchY)) {
                game.setScreen(new MainMenuScreen(game)); // Cambia a la pantalla del menú principal
                dispose();

            } else if (botonSalir.contains(touchX, touchY)) {
                dispose();
                Gdx.app.exit(); // Salir del juego

            }

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
