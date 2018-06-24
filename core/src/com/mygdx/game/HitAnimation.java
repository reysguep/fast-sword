package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import libgdxUtils.AnimatedSprite;
import com.mygdx.game.Characters.Character;

/**
 *
 * @author reysguep
 */
public class HitAnimation {

    public HitAnimation(Character target, Animation<TextureRegion> orgAnim, Array<HitAnimation> thisArray) {
        this.target = target;
        this.animation = new AnimatedSprite(orgAnim);
        this.thisArray = thisArray;
        this.animation.setSize(200, 200);
        startTime = TimeUtils.millis();
        this.target.getAnimations().setColor(Color.BLUE);
    }

    private final Character target;
    private final AnimatedSprite animation;
    private final Array<HitAnimation> thisArray;
    private final long startTime;

    public void draw(SpriteBatch batch) {
        if (!animation.isAnimationFinished()) {
            int x, y;
            x = (int) (target.getX() + (target.getWidth() / 2) - (animation.getWidth() / 2));
            y = (int) (target.getY() + (target.getHeight() / 2) - (animation.getHeight() / 2));
            animation.setPosition(x, y);
            animation.draw(batch);
        } else {
            thisArray.removeValue(this, false);
        }

        if (TimeUtils.timeSinceMillis(startTime) >= 300) {
            target.getAnimations().setColor(Color.WHITE);
        } else {
            target.getAnimations().setColor(1f, 0.3f, 0.3f, 1f);
        }
    }
}
