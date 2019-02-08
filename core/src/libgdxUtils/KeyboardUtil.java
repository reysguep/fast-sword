package libgdxUtils;

import br.cefetmg.move2play.game.Move2PlayGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Main;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.Screens.EndMatchScreen;
import com.mygdx.game.Screens.WaitingScreen;
import com.mygdx.game.Characters.Character;

/**
 *
 * @author reysguep
 */
public class KeyboardUtil implements InputProcessor {

    public KeyboardUtil(Move2PlayGame screen) {
        this.screenM = screen;
    }

    private final Move2PlayGame screenM;
    private boolean fullscreen = false;

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.F11) {
            if (fullscreen == false) {
                Monitor currMonitor = Gdx.graphics.getMonitor();
                DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
                if (Gdx.graphics.setFullscreenMode(displayMode)) {
                    fullscreen = true;
                }

            } else {
                if (Gdx.graphics.setWindowedMode(1280, 720)) {
                    fullscreen = false;
                }
            }
        }

        if (screenM instanceof WaitingScreen) {
            WaitingScreen screen = (WaitingScreen) screenM;
            switch (keycode) {
                case Keys.U:
                    screen.addPlayer(Player.createPlayerModel("Lancelot", "0000"));
                    break;
                case Keys.I:
                    screen.addPlayer(Player.createPlayerModel("Elliot", "1111"));
                    break;
                case Keys.O:
                    screen.addPlayer(Player.createPlayerModel("Noah", "2222"));
                    break;
                case Keys.P:
                    screen.addPlayer(Player.createPlayerModel("Eliza", "3333"));
                    break;
                case Keys.K:
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
        } else if (screenM instanceof BattleScreen) {
            BattleScreen screen = (BattleScreen) screenM;
            Character c;
            switch (keycode) {
                case Keys.U:
                    screen.move("0000", 1);
                    break;
                    
                case Keys.I:
                    screen.move("1111", 1);
                    break;
                    
                case Keys.O:
                    screen.move("2222", 1);
                    break;
                    
                case Keys.P:
                    screen.move("3333", 1);
                    break;
                    
                case Keys.ENTER:
                    screen.finishMatch();
                    break;
                    
                default:
                    break;
            }
        } else if (screenM instanceof EndMatchScreen) {
            switch (keycode) {
                case Keys.ENTER:
                    screenM.initGame();
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode
    ) {
        return false;
    }

    @Override
    public boolean keyTyped(char character
    ) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button
    ) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button
    ) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer
    ) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY
    ) {
        return false;
    }

    @Override
    public boolean scrolled(int amount
    ) {
        return false;
    }
}
