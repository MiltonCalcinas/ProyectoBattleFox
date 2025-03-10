package battle.fox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;



public class NivelesConfig {

    private Texture background;
    private ConfigurarSuperficies configSuperficie;
    private Array<Enemigo> enemigos;
    private Personaje lobo;
    private Music backgroundSound;
    private float volumenActual;
    
    public NivelesConfig(Texture background, 
                        ConfigurarSuperficies configSuperficie,
                        Array<Enemigo> enemigos, 
                        Personaje lobo, Music backgroundSound) {
        this.background = background;
        this.configSuperficie = configSuperficie;
        this.enemigos = enemigos;
        this.lobo = lobo;
        this.backgroundSound = backgroundSound;
        volumenActual = Configuracion.getInstancia().getVolumen();


    }

    public void setNivel(int nivelI) {


        // sonido de fondo
        backgroundSound.stop();
        String [] ArrayBackSounds={
            "background_sound_nivel1.mp3",
            "background_sound_nivel2.mp3",
            "background_sound_nivel3.mp3",
            "background_sound_nivel4.mp3",
            "background_sound_nivel5.mp3",
            "background_sound_nivel6.mp3"
        };
        backgroundSound = Gdx.audio.newMusic(Gdx.files.internal(ArrayBackSounds[nivelI-1]));
        backgroundSound.setLooping(true);
        backgroundSound.setVolume(volumenActual);
        backgroundSound.play();
        // fondo de pantalla
        String[] ArrayFondoBosques={
            "fondo-bosque1.png",
            "fondo-bosque2.png",
            "fondo-bosque3.png",
            "fondo-bosque4.png",
            "fondo-bosque5.png",
            "fondo-bosque6b.png"
        };
        background = new Texture(ArrayFondoBosques[nivelI-1]);

        configSuperficie.forNivel(nivelI);
        int[][] posXY={
            {100,40},//1
            {100,60},//2
            {100,30},//3
            {100,40},//4
            {100,40},//5
            {100,60}//6
        };
        lobo.setX(posXY[nivelI-1][0]);
        lobo.setY(posXY[nivelI-1][1]);

        // enemigos añadidos
        int[]numEnemigos= {7,7,8,9,10,11};

        for(int i=1;i<numEnemigos[nivelI-1];i++){
            enemigos.add(new Enemigo(90*i,800,50,50));
        }
        // actuaalizo para el nivel 2
        lobo.setEnemigos(enemigos);
    }



    public Texture getBackground() {
        return this.background;
    }
    public void finJuego(){

        backgroundSound.stop();
        //backgroundSound = Gdx.audio.newMusic(Gdx.files.internal("background-sound-fin-juego.mp3"));
        //backgroundSound.setLooping(true);
        //backgroundSound.setVolume(volumenActual);
        //backgroundSound.play();

        this.background = new Texture("fondo-fin-juego.jpeg");

        this.configSuperficie.finJuego();

        this.enemigos.clear();
        lobo.setEnemigos(enemigos);

    }


}
