package libgdxUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Player;
import static libgdxUtils.StatusCode.*;

/**
 *
 * @author reysguep
 */
public class DrawingCharacter {

    private final SpriteBatch batch;

    FreeTypeFontGenerator generatorName;
    FreeTypeFontParameter parameterName;
    BitmapFont fontName;
    
    FreeTypeFontGenerator generatorScore;
    FreeTypeFontParameter parameterScore;
    BitmapFont fontScore;

    GlyphLayout layout = new GlyphLayout();

    public DrawingCharacter(SpriteBatch batch) {
        this.batch = batch;

        generatorName = new FreeTypeFontGenerator(Gdx.files.internal("fonts/VeniceClassic.ttf"));
        parameterName = new FreeTypeFontParameter();
        parameterName.size = 35;
        parameterName.borderWidth = 3;
        fontName = generatorName.generateFont(parameterName);
        
        generatorScore = new FreeTypeFontGenerator(Gdx.files.internal("fonts/alterebro-pixel-font.ttf"));
        parameterScore = new FreeTypeFontParameter();
        parameterScore.size = 30;
        parameterScore.borderWidth = 1;
        fontScore = generatorScore.generateFont(parameterScore);
        fontScore.setColor(Color.ORANGE);
    }

    public void draw(Character character) {
        character.getAnimations().draw(batch);
        drawName(character);
        if (character.getStatus() != DEAD) {
            drawBars(character);
        } else {
            if (character instanceof Player) {
                Player player = (Player) character;
                drawReviveBar(player);
            }
        }
    }

    private void drawBars(Character character) {
        int x, y, width;

        x = (int) character.getX() + (character.getWidth() / 2) - 50;
        y = (int) character.getY() + character.getHeight();

        width = (int) (100.0f * character.getProgress());

        batch.draw(ColorsUtil.BLUE, x, y, width, 10);

        width = (character.getHealth() * 100) / character.getMaxHealth();

        y += 15;
        batch.draw(ColorsUtil.RED, x, y, 100, 10);
        batch.draw(ColorsUtil.GREEN, x, y, width, 10);
    }

    private void drawName(Character character) {
        int x, y;
        float textWidth;

        CharSequence text = character.getName();

        layout.setText(fontName, text);
        textWidth = layout.width;

        x = (int) character.getX() + (character.getWidth() / 2) - (int) (textWidth / 2);
        y = (int) character.getY() + character.getHeight() + 45;

        fontName.draw(batch, text, x, y);
    }

    private void drawReviveBar(Player player) {
        int x, y;
        
        x = (int) player.getX() + (player.getWidth() / 2) - 50;
        y = (int) player.getY() + player.getHeight();
        
        long timeSince = TimeUtils.millis() - player.timeDied;
        long timeRemaining = 6000 - timeSince;
        
        float width = 100f * (timeRemaining / 6000f);
        
        
        batch.draw(ColorsUtil.GOLD, x, y, width, 10);
    }
    
    public void drawScores(Array<Character> allCharacters){
        int x, y;
        x = 50;
        y = 700;
        
        for(Character chr : allCharacters){
            if(chr instanceof Player){
                Player player = (Player)chr;
                String sentence = player.getName() + ": " + player.score;
                fontScore.draw(batch, sentence, x, y);
                
                y -= 40;
            }
        }
    }

    public void dispose() {
        generatorName.dispose();
        ColorsUtil.dispose();
    }
}
