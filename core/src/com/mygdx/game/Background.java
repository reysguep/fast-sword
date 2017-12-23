package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Random;
import libgdxUtils.AnimatedSprite;

/**
 *
 * @author reysguep
 */
public class Background {

    public Background() {
        Animation<TextureRegion> animation;
        Random random = new Random();
        int sortedNumber = random.nextInt(5);
        animation = GdxUtil.getAnimationAt("Animations/backgrounds/fbg" + sortedNumber);
        animacao = new AnimatedSprite(animation);
        animacao.setSize(1280, 720);
    }

    private final AnimatedSprite animacao;

    public void draw(Batch batch) {
        animacao.draw(batch);
    }
}
