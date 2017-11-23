package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Random;

/**
 *
 * @author reysguep
 */
public class Background {

    public Background() {
        TextureRegion background[];
        Random random = new Random();
        int sortedNumber = random.nextInt(5);
        animation = GdxUtil.getAnimationAt("Animations/backgrounds/fbg" + sortedNumber);
    }

    private final Animation<TextureRegion> animation;

    public void draw(Batch batch, float stateTime) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, 0, 0, 1280, 720);
    }
}
