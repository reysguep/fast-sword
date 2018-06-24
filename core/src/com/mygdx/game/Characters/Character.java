package com.mygdx.game.Characters;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import libgdxUtils.AnimatedSprite;
import libgdxUtils.AnimationCode;
import libgdxUtils.Commands;
import libgdxUtils.MultiAnimatedSprite;
import libgdxUtils.StatusCode;
import libgdxUtils.TextureUtil;
import libgdxUtils.exceptions.CommandException;

/**
 *
 * @author reysguep
 */
 public abstract class Character {

    public Character(String name, CharacterPreset preset, int maxHealth, int strength) {
        this.maxHealth = maxHealth;
        this.health = this.maxHealth;
        this.strength = strength;
        this.animations = TextureUtil.createAnimationsCharacter("Animations/" + preset.folder);
        animations.setSize(preset.width, preset.height);
        this.name = name;
        status = 1;

        this.hitSounds = preset.hitSounds;
        this.deathSounds = preset.deathSounds;
        
        this.actionCode = preset.actionCode;
        this.deathCode = preset.deathCode;
        this.targetAnimation = TextureUtil.visualEffects.get(preset.targetAnimation);
    }

    public Character(String name, CharacterPreset preset) {
        this.maxHealth = preset.maxHealth;
        this.health = this.maxHealth;
        this.strength = preset.maxStr;
        this.animations = TextureUtil.createAnimationsCharacter("Animations/" + preset.folder);
        animations.setSize(preset.width, preset.height);
        this.name = name;
        status = 1;

        this.hitSounds = preset.hitSounds;
        this.deathSounds = preset.deathSounds;
        
        this.actionCode = preset.actionCode;
        this.deathCode = preset.deathCode;
        
        this.targetAnimation = TextureUtil.visualEffects.get(preset.targetAnimation);
    }

    public int health;
    protected int maxHealth;
    protected int strength;

    private final Array<Sound> hitSounds, deathSounds;

    private int status;
    public int orgX, orgY;

    public String name;
    public char team;
    
    public final String actionCode, deathCode;
    public final Animation targetAnimation;

    private final MultiAnimatedSprite animations;

    public boolean isDead() {
        return health <= 0;
    }

    public void act(Commands commands){
        setStatus(StatusCode.ACTING);
        try {
            commands.call(actionCode, this);
        } catch (CommandException ex) {
            Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        act();
        setAnimation(AnimationCode.ATTACKING);
        hitSounds.random().play();
    }
    protected abstract void act();
    

    public void beAttacked(int damage, Commands commands) {
        health -= damage;
        if (health <= 0) {
            setStatus(StatusCode.DYING);
            try {
                commands.call(deathCode, this);
            } catch (CommandException ex) {
                Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                animations.flipFrames(false, false, true);
                break;

            case StatusCode.ACTING:
                break;

            case StatusCode.RETURNING:
                setAnimation(AnimationCode.RUNNING);
                animations.flipFrames(true, false, true);
                break;
            case StatusCode.DYING:
                health = 0;
                animations.startAnimation(AnimationCode.DYING);
                System.out.println(name + " morreu!");
                deathSounds.random().play();
                break;

            case StatusCode.REVIVING:
                animations.getAnimation(AnimationCode.DYING).setPlayMode(Animation.PlayMode.REVERSED);
                setAnimation(AnimationCode.DYING);
                health = maxHealth;
                break;

            case StatusCode.DEAD:
                if (this instanceof Player) {
                    Player thisPlayer = (Player) this;
                    thisPlayer.pedaladasDadas = 0;
                    thisPlayer.score /= 2;
                    thisPlayer.timeDied = TimeUtils.millis();
                }
                break;
        }
    }

    public abstract float getProgress();
}
