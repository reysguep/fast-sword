package com.mygdx.game.Characters;

import com.badlogic.gdx.utils.TimeUtils;

/**
 *
 * @author reysguep
 */
public class Enemy extends Character {

    public Enemy(EnemyPreset preset, int health, int strength, float timeToAttack) {
        super(preset.name, preset, health, strength);
        
        this.startTime = TimeUtils.millis();
        this.timeToAttack = (long) (timeToAttack * 1000);
    }


    private final long timeToAttack;
    private long startTime;

    @Override
    public boolean canAttack() {
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        return elapsedTime >= timeToAttack;
    }

    @Override
    public void act() {
        startTime = TimeUtils.millis();
    }

    @Override
    public float getProgress() {
        float progress;

        if (!this.isDead()) {
            long elapsedTime = TimeUtils.timeSinceMillis(startTime);
            progress = (float)elapsedTime / timeToAttack;
            if(progress >= 1){
                startTime += elapsedTime - timeToAttack;
                progress = 1;
            }
        } else {
            progress = 0;
        }
        return progress;
    }
}
