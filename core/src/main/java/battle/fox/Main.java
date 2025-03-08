package battle.fox;

import com.badlogic.gdx.Game;

public class Main extends Game {
    @Override
    public void create() {
        this.setScreen(new MainMenuScreen(this)); // Iniciar con el menú
    }
}
