package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;

/**
 *
 * @author reysguep
 */
public abstract class Enemy extends Character{
    
    public Enemy(String nome, int maxHealth, int strength, float timeToAttack, String folder){
        super(nome, maxHealth, strength, "enemies/" + folder);
        startTime = TimeUtils.millis();
        this.timeToAttack = (long)(timeToAttack * 1000);
    }
    
    private long timeToAttack, startTime;
    
    @Override
    public boolean canAttack(){
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        return elapsedTime >= timeToAttack;
    }
    
    @Override
    public void attack(Character target){
        msgAttack(target);
        startTime = TimeUtils.millis();
    }
}
