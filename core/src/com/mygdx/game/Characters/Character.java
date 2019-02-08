package com.mygdx.game.Characters;

import com.mygdx.game.Characters.presets.CharacterPreset;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.SkeletonAnimation;
import com.mygdx.game.Team;
import libgdxUtils.AnimationCode;
import com.mygdx.game.accessors.CharacterAccessor;
import com.mygdx.game.exceptions.StateNotFoundException;
import com.mygdx.game.loaders.SpineLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import libgdxUtils.StateCode;

/**
 *
 * @author reysguep
 */
public abstract class Character extends SkeletonAnimation {

    public int health;
    protected int maxHealth;
    protected int strength;

    private final String actionSoundEffect, deathSoundEffect;

    protected int state;
    protected int speed;
    protected int orgX, orgY;

    private final String name;

    private final String targetAnimation;

    private char team;
    protected final BattleScreen screen;

    public Character(String name, CharacterPreset preset, BattleScreen screen) {
        super(SpineLoader.getClassSkeleton(preset.folder));
        state = 1;
        this.name = name;
        this.maxHealth = preset.maxHealth;
        this.health = preset.maxHealth;
        this.strength = preset.strength;
        this.speed = preset.speed;
        this.actionSoundEffect = preset.actionSound;
        this.deathSoundEffect = preset.deathSound;
        this.targetAnimation = preset.hitAnimation;
        
        skeleton.getRootBone().setScale(preset.scale);
        width *= preset.scale;
        height *= preset.scale;

        this.screen = screen;
    }

    public boolean isDead() {
        return health <= 0;
    }
    
    public void addHealth(int n) {
        if(health + n > maxHealth) {
            health = maxHealth;
        } else if(health + n < 0) {
            health = 0;
        } else {
            health += n;
        }
    }

    public void action() {
        screen.soundEffectManager.playSoundEffect(actionSoundEffect);
    }

    public void beAttacked(int damage) {
        addHealth(damage * -1);
        if (isDead()) {
            setState(StateCode.DYING);
        }

    }

    public void setState(int state) {
        boolean defaultSide;
        TweenManager tween;

        tween = screen.tweenManager;
        defaultSide = (team == 'a'); //TURNS TO RIGHT OR LEFT BY DEFAULT

        screen.tweenManager.killTarget(this);
        
        this.state = state;
        switch (state) {
            case StateCode.WAITING:
                flipX(defaultSide);
                setAnimation(AnimationCode.IDLE, true);
                setPosition(orgX, orgY);
                break;

            case StateCode.GOING:
                flipX(defaultSide);
                setAnimation(AnimationCode.RUNNING, true);
                move(tween);
                break;

            case StateCode.RETURNING:
                flipX(!defaultSide);
                setAnimation(AnimationCode.RUNNING, true);
                move(tween);
                break;

            case StateCode.ACTING:
                flipX(defaultSide);
                setAnimation(AnimationCode.ATTACKING, false);
                break;

            case StateCode.DYING:
                setAnimation(AnimationCode.DYING, false);
                health = 0;
                break;

            case StateCode.REVIVING:
                setAnimation(AnimationCode.DYING, true);
                reverseAnimation(true);
                break;

            case StateCode.DEAD:
                screen.soundEffectManager.playSoundEffect(getActionSoundEffect());
                if (this instanceof Player) {
                    Player thisPlayer = (Player) this;
                    thisPlayer.takenSteps = 0;
                    thisPlayer.score /= 2;
                    thisPlayer.timeDied = TimeUtils.millis();
                }
                break;

            default: {
            try {
                throw new StateNotFoundException(String.valueOf(state));
            } catch (StateNotFoundException ex) {
                Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        } //end switch

        
    } //end method

    public abstract boolean canAttack();

    public boolean canAttack(boolean factor1) {
        boolean canAttack, factor2;
        Team enemies;

        if (team == 'a') {
            enemies = screen.teamB;
        } else {
            enemies = screen.teamA;
        }

        factor2 = !enemies.getLiveMembers().isEmpty();

        canAttack = factor1 & factor2;
        return canAttack;
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

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean compareAnimation(String animation) {
        return animation.equals(animation);
    }

    public int getState() {
        return state;
    }

    public abstract float getProgress();

    public void move(TweenManager tween) {
        int destiny, distance, moveDirection;
        float time;

        if (team == 'a') {
            moveDirection = 1;
        } else {
            moveDirection = -1;
        }

        switch (state) {
            case StateCode.RETURNING:
                destiny = orgX;
                break;

            case StateCode.GOING:
                destiny = orgX + 100 * moveDirection;
                break;

            default:
                return;
        }

        distance = Math.abs((int) this.getX() - destiny);
        time = distance / speed;
        Tween.to(this, CharacterAccessor.POS_X, time).target(destiny).delay(0).start(tween);
    }

    public String getActionSoundEffect() {
        return actionSoundEffect;
    }

    public String getDeathSoundEffect() {
        return deathSoundEffect;
    }

    public int getOrgX() {
        return orgX;
    }

    public int getOrgY() {
        return orgY;
    }

    public String getTargetAnimation() {
        return targetAnimation;
    }

    public char getTeam() {
        return team;
    }

    public void setOrgX(int orgX) {
        this.orgX = orgX;
    }

    public void setOrgY(int orgY) {
        this.orgY = orgY;
    }

    public void setTeam(char team) {
        this.team = team;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    
}
