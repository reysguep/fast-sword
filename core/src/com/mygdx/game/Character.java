package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import libgdxUtils.AnimatedSprite;
import libgdxUtils.AnimationCode;
import libgdxUtils.MultiAnimatedSprite;
import libgdxUtils.TextureUtil;

/**
 *
 * @author reysguep
 */
public abstract class Character {

    public Character(String nome, int maxHealth, int strength, String folder) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.strength = strength;
        this.animations = TextureUtil.createAnimationsCharacter("Animations/" + folder);
        this.nome = nome;
    }

    protected int health;
    protected int maxHealth;
    protected int strength;

    private final String nome;

    public final MultiAnimatedSprite animations;

    public boolean isDead() {
        return health <= 0;
    }

    private void die() {
        health = 0;
        animations.startAnimation(AnimationCode.DYING);
        System.out.println(nome + " morreu!");
    }

    public void attack(Character target) {
        if (!isDead()) {
            System.out.println(nome + " atacou " + target.getNome());
            target.beAttacked(strength);
            this.animations.startAnimation(AnimationCode.ATTACKING);
        }
    }

    public void beAttacked(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
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

    public void draw(Batch batch) {
        animations.draw(batch);
    }

    public void setPosition(int x, int y) {
        animations.setPosition(x, y);
    }

    public void setSize(int width, int height) {
        animations.setSize(width, height);
    }

    public void setAnimation(String animation) {
        animations.startAnimation(animation);
    }

    public String getNome() {
        return nome;
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
}
