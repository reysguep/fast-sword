package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.esotericsoftware.spine.SkeletonMeshRenderer;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Player;
import libgdxUtils.ColorsUtil;
import static libgdxUtils.StateCode.*;

/**
 *
 * @author reysguep
 */
public class DrawingManager {

    private final PolygonSpriteBatch batch;
    private final SkeletonMeshRenderer renderer;

    FreeTypeFontGenerator generatorName;
    FreeTypeFontParameter parameterName;
    BitmapFont fontName;
    
    FreeTypeFontGenerator generatorScore;
    FreeTypeFontParameter parameterScore;
    BitmapFont fontScore;

    GlyphLayout layout = new GlyphLayout();

    public DrawingManager(PolygonSpriteBatch batch, SkeletonMeshRenderer renderer) {
        this.batch = batch;
        this.renderer = renderer;

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
        character.draw(renderer, batch);
        drawName(character);
        if (character.getState() != DEAD) {
            drawBars(character);
        } else {
            if (character instanceof Player) {
                Player player = (Player) character;
                drawReviveBar(player);
            }
        }
    }

    private void drawBars(Character character) {
        float x, y, width;

        x = character.getX() - 50;
        y = character.getY() + character.getHeight();

        width = 100.0f * character.getProgress();

        batch.draw(ColorsUtil.BLUE, x, y, width, 10);

        width = 100f * character.getHealth() / character.getMaxHealth();

        y += 10;
        batch.draw(ColorsUtil.RED, x, y, 100, 10);
        batch.draw(ColorsUtil.GREEN, x, y, width, 10);
    }

    private void drawName(Character character) {
        float x, y;
        float textWidth;

        CharSequence text = character.getName();

        layout.setText(fontName, text);
        textWidth = layout.width;

        x = character.getX() - (textWidth / 2);
        y = character.getY() + character.getHeight() + 45;

        fontName.draw(batch, text, x, y);
    }

    private void drawReviveBar(Player player) {
        float x, y;
        
        x = player.getX() - 50;
        y = player.getY() + player.getHeight();
        
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
