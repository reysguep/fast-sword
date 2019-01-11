package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import libgdxUtils.AnimatedSprite;
import com.mygdx.game.Characters.Character;

/**
 *
 * @author reysguep
 */
public class VisualEffect {

    private final Character target;
    private final AnimatedSprite animation;
    private final long startTime;

    public VisualEffect(Character target, Animation<TextureRegion> preAnim) {
        this.target = target;
        this.animation = new AnimatedSprite(preAnim);
        this.animation.setSize(200, 200);
        startTime = TimeUtils.millis();
        
        this.target.setColor(new Color(1f, 0.3f, 0.3f, 1f));
    }

    public void draw(Batch batch) {
        if (!animation.isAnimationFinished()) {
            int x, y;
            x = (int) (target.getX() + (target.getWidth() / 2) - (animation.getWidth() / 2));
            y = (int) (target.getY() + (target.getHeight() / 2) - (animation.getHeight() / 2));
            animation.setPosition(x, y);
            animation.draw(batch);

            if (TimeUtils.timeSinceMillis(startTime) >= 300) {
                target.setColor(Color.WHITE);
            }
        }
    }
    
    public boolean isComplete() {
        return animation.isAnimationFinished();
    }

    public Character getTarget() {
        return target;
    }

    public AnimatedSprite getAnimation() {
        return animation;
    }

    public long getStartTime() {
        return startTime;
    }
    
    
}
