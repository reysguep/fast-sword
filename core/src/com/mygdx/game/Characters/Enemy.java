package com.mygdx.game.Characters;

import com.mygdx.game.Characters.presets.EnemyPreset;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Screens.BattleScreen;

/**
 *
 * @author reysguep
 */
public abstract class Enemy extends Character {

    private final long timeToAttack;
    private long startTime;

    public Enemy(EnemyPreset preset, BattleScreen screen) {
        super(preset.name, preset, screen);

        this.startTime = TimeUtils.millis();
        this.timeToAttack = (long) (preset.timeToAttack * 1000);
    }

    @Override
    public boolean canAttack() {
        boolean factor1;
        long elapsedTime;
        
        elapsedTime = TimeUtils.timeSinceMillis(startTime);
        factor1 = elapsedTime >= timeToAttack;
        
        return super.canAttack(factor1);
    }

    @Override
    public void action() {
        startTime = TimeUtils.millis();
    }

    @Override
    public float getProgress() {
        float progress;

        if (!this.isDead()) {
            long elapsedTime = TimeUtils.timeSinceMillis(startTime);
            progress = (float) elapsedTime / timeToAttack;
            if (progress >= 1) {
                startTime += elapsedTime - timeToAttack;
                progress = 1;
            }
        } else {
            progress = 0;
        }
        return progress;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    
    
}
