package com.mygdx.game.Characters;

import com.mygdx.game.Characters.Character;
import com.badlogic.gdx.utils.TimeUtils;
import com.google.gson.Gson;
import libgdxUtils.EnemyPreset;

/**
 *
 * @author reysguep
 */
public class Enemy extends Character {

    public Enemy(String nome, int maxHealth, int strength, float timeToAttack, String folder) {
        super(nome, maxHealth, strength, "enemies/" + folder);

        this.startTime = TimeUtils.millis();
        this.timeToAttack = (long) (timeToAttack * 1000);
    }

    public static Enemy jsonToEnemy(String fileName) {
        Gson gson = new Gson();
        EnemyPreset obj = gson.fromJson("Presets/" + fileName, EnemyPreset.class);
        
        Enemy enemy = new Enemy(obj.name, obj.maxHealth, obj.strength, obj.timeToAttack, obj.fileName);
        enemy.setSize(obj.width, obj.height);
        
        return enemy;
    }

    private final long timeToAttack;
    private long startTime;

    @Override
    public boolean canAttack() {
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        return elapsedTime >= timeToAttack;
    }

    @Override
    public void attack(Character target) {
        super.attackMessage(target);
        startTime = TimeUtils.millis();
    }
    
    @Override
    public float getProgress(){
        float progress;
        
        if(!this.isDead()){
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        progress = elapsedTime / timeToAttack;
        } else{
            progress = 0;
        }
        return progress;
    }
}
