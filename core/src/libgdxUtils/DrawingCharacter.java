package libgdxUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Player;

/**
 *
 * @author reysguep
 */
public class DrawingCharacter {

    private final SpriteBatch batch;

    FreeTypeFontGenerator generatorName;
    FreeTypeFontParameter parameterName;
    BitmapFont fontName;

    ShapeRenderer rectangle;

    GlyphLayout layout = new GlyphLayout();

    public DrawingCharacter(SpriteBatch batch) {
        this.batch = batch;

        rectangle = new ShapeRenderer();
        generatorName = new FreeTypeFontGenerator(Gdx.files.internal("fonts/VeniceClassic.ttf"));
        parameterName = new FreeTypeFontParameter();
        parameterName.size = 35;
        parameterName.borderWidth = 3;
        fontName = generatorName.generateFont(parameterName);
    }

    public void draw(Character character) {
        character.getAnimations().draw(batch);
        if (!character.isDead()) {
            drawName(character);
            drawBars(character);
        }
    }

    private void drawBars(Character character) {
        int x, y, width;

        x = (int)character.getX() + (character.getWidth() / 2) - 50;
        y = (int)character.getY() + character.getHeight();

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

        x = (int)character.getX() + (character.getWidth() / 2) - (int) (textWidth / 2);
        y = (int)character.getY() + character.getHeight() + 45;

        fontName.draw(batch, text, x, y);
    }

    public void dispose() {
        generatorName.dispose();
        rectangle.dispose();
        ColorsUtil.dispose();
    }
}
