package libgdxUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.mygdx.game.Character;

/**
 *
 * @author reysguep
 */
public class DrawingCharacter {

    private Character character;
    
    private final SpriteBatch batch;

    FreeTypeFontGenerator generatorHealth;
    FreeTypeFontParameter parameterHealth;
    BitmapFont fontHealth;

    FreeTypeFontGenerator generatorName;
    FreeTypeFontParameter parameterName;
    BitmapFont fontName;
    
    GlyphLayout layout = new GlyphLayout();

    public DrawingCharacter(SpriteBatch batch) {
        this.batch = batch;
        
        generatorHealth = new FreeTypeFontGenerator(Gdx.files.internal("fonts/alterebro-pixel-font.ttf"));
        parameterHealth = new FreeTypeFontParameter();
        parameterHealth.size = 30;
        parameterHealth.borderWidth = 4;
        fontHealth = generatorHealth.generateFont(parameterHealth);
        fontHealth.setColor(Color.RED);

        generatorName = new FreeTypeFontGenerator(Gdx.files.internal("fonts/VeniceClassic.ttf"));
        parameterName = new FreeTypeFontParameter();
        parameterName.size = 35;
        parameterName.borderWidth = 3;
        fontName = generatorName.generateFont(parameterName);
    }

    public void draw(Character chtr) {

        this.character = chtr;
        character.getAnimations().draw(batch);
        drawHealth();
        drawName();
    }

    private void drawHealth() {
        int x, y;
        float textWidth;

        CharSequence text = character.getHealth() + " / " + character.getMaxHealth();
        
        layout.setText(fontHealth, text);
        textWidth = layout.width;
        
        x = character.getX() + (character.getWidth() / 2) - (int)(textWidth / 2);
        y = character.getY() + character.getHeight();
        
        fontHealth.draw(batch, text, x, y);
    }

    private void drawName() {
        int x, y;
        float textWidth;
        
        CharSequence text = character.getName();
        
        layout.setText(fontHealth, text);
        textWidth = layout.width;
        
        x = character.getX() + (character.getWidth() / 2) - (int)(textWidth / 2);
        y = character.getY() + character.getHeight() + 30;
        
        fontName.draw(batch, text, x, y);
    }

    public void dispose() {
        generatorHealth.dispose();
        generatorName.dispose();
    }
}
