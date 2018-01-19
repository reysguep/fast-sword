package libgdxUtils;

import br.cefetmg.move2play.game.Move2PlayGame;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.BattleScreen;
import com.mygdx.game.WaitingScreen;

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
            WaitingScreen screen = (WaitingScreen)screenM;
            switch (keycode) {
                case Keys.I:
                    screen.addPlayer("Lancelot");
                    break;
                case Keys.O:
                    screen.addPlayer("Elliot");
                    break;
                case Keys.K:
                    screen.removePlayer("Lancelot");
                    break;
                case Keys.L:
                    screen.removePlayer("Elliot");
                    break;
                case Keys.ENTER:
                    screen.startMatch();
                    break;
                default:
                    break;
            }
        }
        if(screenM instanceof BattleScreen){
            BattleScreen screen = (BattleScreen)screenM;
            switch (keycode) {
                case Keys.I:
                    screen.addPlayer("Lancelot");
                    break;
                case Keys.O:
                    screen.addPlayer("Elliot");
                    break;
                case Keys.K:
                    screen.removePlayer("Lancelot");
                    break;
                case Keys.L:
                    screen.removePlayer("Elliot");
                    break;
                default:
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
