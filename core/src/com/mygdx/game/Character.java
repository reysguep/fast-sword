package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.awt.Point;

/**
 *
 * @author reysguep
 */
public abstract class Character {

    public Character(int health, int maxHealth, int strength, Point position, String folderAnim) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.strength = strength;
        this.position = position;
        idleAnim = GdxUtil.getAnimationAt("Animations/" + folderAnim + "/idle", true, false);
    }

    protected int health;
    protected int maxHealth;
    protected int strength;
    protected final Point position;

    private final Animation<TextureRegion> idleAnim;
    private Animation<TextureRegion> attackingAnim;
    private Animation<TextureRegion> dyingAnim;
    protected Animation<TextureRegion> actualAnim;

    protected void atacar() {
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getStrength() {
        return strength;
    }

    public void draw(Batch batch, float stateTime) {
        TextureRegion currentFrame = idleAnim.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y);
    }
    
    public void setIdleAnimation(String folder){
        GdxUtil.getAnimationAt(folder);
    }
}
