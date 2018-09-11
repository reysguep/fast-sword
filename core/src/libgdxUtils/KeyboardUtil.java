package libgdxUtils;

import br.cefetmg.move2play.game.Move2PlayGame;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Main;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.Screens.EndMatchScreen;
import com.mygdx.game.Screens.WaitingScreen;

/**
 *
 * @author reysguep
 */
public class KeyboardUtil implements InputProcessor {

    public KeyboardUtil(Move2PlayGame screen) {
        this.screenM = screen;
    }

    private final Move2PlayGame screenM;

    @Override
    public boolean keyDown(int keycode) {
        if (screenM instanceof WaitingScreen) {
            WaitingScreen screen = (WaitingScreen) screenM;
            switch (keycode) {
                case Keys.U:
                    screen.addPlayer(Main.createPlayer(0, "Lancelot"));
                    break;
                case Keys.I:
                    screen.addPlayer(Main.createPlayer(1, "Eliot"));
                    break;
                case Keys.O:
                    screen.addPlayer(Main.createPlayer(2, "Noah"));
                    break;
                case Keys.P:
                    screen.addPlayer(Main.createPlayer(3, "General"));
                    break;
                case Keys.K:
                    //screen.removePlayer("Lancelot");
                    break;
                case Keys.L:
                    //screen.removePlayer("Elliot");
                    break;
                case Keys.ENTER:
                    screen.startMatch();
                    break;
                default:
                    break;
            }
        }
        else if (screenM instanceof BattleScreen) {
            BattleScreen screen = (BattleScreen) screenM;
            switch (keycode) {
                case Keys.U:
                    screen.move("Lancelot", 1);
                    break;
                case Keys.I:
                    screen.move("Elliot", 1);
                    break;
                case Keys.O:
                    screen.move("Noah", 1);
                    break;
                case Keys.P:
                    screen.move("General", 1);
                    break;
                case Keys.K:
                    screen.removePlayer("Lancelot");
                    break;
                case Keys.L:
                    screen.removePlayer("Elliot");
                    break;
                case Keys.BACKSPACE:
                    screen.finishMatch();
                default:
                    break;
            }
        }
        else if(screenM instanceof EndMatchScreen){
            switch(keycode){
                case Keys.ENTER:
                    screenM.initGame();
                break;
            }
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
