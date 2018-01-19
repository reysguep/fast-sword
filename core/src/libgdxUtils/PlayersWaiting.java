package libgdxUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.Character;
import com.mygdx.game.Player;
import java.util.ArrayList;

/**
 *
 * @author reysguep
 */
public class PlayersWaiting extends ArrayList<WaitingNode> {

    public PlayersWaiting(GlyphLayout layout) {
        super();
        this.layout = layout;
        FreeTypeFontGenerator generatorP = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/VeniceClassic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        
        parameterP.size = 40;
        parameterP.borderWidth = 3;
        playerMessage = generatorP.generateFont(parameterP);
    }

    private final GlyphLayout layout;
    private final BitmapFont playerMessage;

    public void addPlayer(Character character) {
        WaitingNode wn = new WaitingNode(character);
        super.add(wn);
    }
    
    public void removePlayer(String name){
        Character player = searchPlayer(name);
        for(WaitingNode wn: this){
            if(wn.getCharacter() == player){
                super.remove(wn);
                return;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        int i = 0;
        for (WaitingNode wn : this) {
            int x = 500, y = 500 - 50 * i;
            layout.setText(playerMessage, wn.getCharacter().getName());
            wn.getStar().setPosition(x - 40, y - 25);
            wn.getStar().draw(batch);
            playerMessage.draw(batch, wn.getCharacter().getName(), x, y);
            i++;
        }
    }

    public Player searchPlayer(String str) {
        Character character;
        for (WaitingNode wn : this) {
            character = wn.getCharacter();
            if (character instanceof Player) {
                Player player = (Player) character;
                if (player.getUuid().equals(str) || player.getName().equals(str)) {
                    return player;
                }
            }
        }

        return null;
    }
    
    public void dispose(){
        playerMessage.dispose();
    }
}
