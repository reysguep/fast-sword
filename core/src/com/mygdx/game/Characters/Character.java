package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import libgdxUtils.AnimatedSprite;
import libgdxUtils.AnimationCode;
import libgdxUtils.MultiAnimatedSprite;
import libgdxUtils.StatusCode;
import libgdxUtils.TextureUtil;

/**
 *
 * @author reysguep
 */
public abstract class Character {

    public Character(String name, int maxHealth, int strength, String folder) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.strength = strength;
        this.animations = TextureUtil.createAnimationsCharacter("Animations/" + folder);
        this.name = name;
        status = 1;
    }

    protected int health;
    protected int maxHealth;
    protected int strength;

    
    private int status;
    public int orgX, orgY;

    public String name, team;

    private final MultiAnimatedSprite animations;

    public boolean isDead() {
        return health <= 0;
    }

    public abstract void attack(Character target);

    protected void attackMessage(Character target) {
        System.out.println(name + " atacou " + target.getName());
        target.beAttacked(strength);
        this.animations.startAnimation(AnimationCode.ATTACKING);
    }

    public void beAttacked(int damage) {
        health -= damage;
        if (health <= 0) {
            setStatus(StatusCode.DYING);
        }
    }

    public abstract boolean canAttack();

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getStrength() {
        return strength;
    }

    public void draw(SpriteBatch batch) {
        animations.draw(batch);

    }

    public void setPosition(float x, float y) {
        animations.setPosition(x, y);
    }

    public void setSize(int width, int height) {
        animations.setSize(width, height);
    }

    public void resizeLockHeight(int height) {
        float reason = height / getHeight();
        int width = (int) (getWidth() * reason);

        setSize(width, height);
    }

    public void resizeLockWidth(int width) {
        float reason = width / getWidth();
        int height = (int) (getHeight() * reason);

        setSize(width, height);
    }

    public void setAnimation(String animation) {
        animations.startAnimation(animation);
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return animations.getX();
    }

    public float getY() {
        return animations.getY();
    }

    public int getHeight() {
        return (int) animations.getHeight();
    }

    public int getWidth() {
        return (int) animations.getWidth();
    }

    public void flipAllAnimations(boolean flipX, boolean flipY) {
        AnimatedSprite as;

        as = new AnimatedSprite(animations.getAnimation(AnimationCode.IDLE));
        as.flipFrames(flipX, flipY);

        as = new AnimatedSprite(animations.getAnimation(AnimationCode.ATTACKING));
        as.flipFrames(flipX, flipY);

        as = new AnimatedSprite(animations.getAnimation(AnimationCode.DYING));
        as.flipFrames(flipX, flipY);
    }

    public MultiAnimatedSprite getAnimations() {
        return animations;
    }

    public boolean compareAnimation(String animation) {
        return animations.getAnimation() == animations.getAnimation(animation);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;

        switch (status) {
            case StatusCode.WAITING:
                setAnimation(AnimationCode.IDLE);
                break;
            case StatusCode.GOING:
                setAnimation(AnimationCode.RUNNING);
                break;
            case StatusCode.ATTACKING:
                setAnimation(AnimationCode.ATTACKING);
                break;
            case StatusCode.RETURNING:
                setAnimation(AnimationCode.RUNNING);
                animations.flipFrames(true, false);
                break;
            case StatusCode.DYING:
                health = 0;
                animations.startAnimation(AnimationCode.DYING);
                System.out.println(name + " morreu!");
                break;
        }
    }

    public abstract float getProgress();
}
