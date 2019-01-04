package com.mygdx.game.Characters;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.SkeletonAnimation;
import com.mygdx.game.Team;
import java.util.logging.Level;
import java.util.logging.Logger;
import libgdxUtils.AnimationCode;
import com.mygdx.game.accessors.CharacterAccessor;
import com.mygdx.game.managers.SoundEffectManager;
import libgdxUtils.StatusCode;
import libgdxUtils.TextureUtil;
import libgdxUtils.exceptions.CommandException;

/**
 *
 * @author reysguep
 */
public abstract class Character extends SkeletonAnimation {
    
    public int health;
    protected int maxHealth;
    protected int strength;

    private final String actionSoundEffect, deathSoundEffect;

    protected int status;
    protected int speed;
    protected int orgX, orgY;

    private final String name;

    private final Animation targetAnimation;
    
    private char team;
    
    public Character(String name, CharacterPreset preset, char team) {
        super(Charact, "idle");
        status = 1;
        this.team = team;
        
        this.name = name;
        this.maxHealth = preset.maxHealth;
        this.strength = preset.strength;
        this.speed = preset.speed;
        this.actionSoundEffect = preset.actionSound;
        this.deathSoundEffect = preset.deathSound;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void action() {
        setStatus(StatusCode.ACTING);
        SoundEffectManager.playSoundEffect(actionSoundEffect);
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

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean compareAnimation(String animation) {
        return animation.equals(animation);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;

        switch (status) {
            case StatusCode.WAITING:
                setAnimation(AnimationCode.IDLE, true);
                break;

            case StatusCode.GOING:
                setAnimation(AnimationCode.RUNNING, true);
                flip(false, false);
                break;

            case StatusCode.ACTING:
                break;

            case StatusCode.RETURNING:
                setAnimation(AnimationCode.RUNNING, true);
                flip(true, false);
                break;
            case StatusCode.DYING:
                health = 0;
                setAnimation(AnimationCode.DYING, false);
                System.out.println(name + " morreu!");
                SoundEffectManager.playSoundEffect(deathSoundEffect);
                break;

            case StatusCode.REVIVING:
                setAnimation(AnimationCode.DYING, false); //!arrumar uma forma de inverter a animação 
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

    public void move(int settedStatus, TweenManager tween, int moveDirection) {
        int destiny, distance;
        float time;

        switch (settedStatus) {
            case StatusCode.RETURNING:
                destiny = orgX;
                break;

            case StatusCode.GOING:
                destiny = orgX + 100 * moveDirection;
                break;

            default:
                return;
        }

        distance = Math.abs((int) this.getX() - destiny);
        time = distance / speed;
        Tween.to(this, CharacterAccessor.POS_X, time).target(destiny).delay(0).start(tween);
    }
}
