package com.mygdx.game.Characters.enemies;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Characters.presets.PlayerPreset;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Characters.actions.TargetSelection;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Characters.actions.CharacterActions;
import com.mygdx.game.Characters.presets.EnemyPreset;

/**
 *
 * @author reysguep
 */
public class BadViking extends Enemy{
    
    public BadViking(BattleScreen screen) {
        super(createPreset(), screen);
    }
    
    private static EnemyPreset createPreset() {
        EnemyPreset preset = new EnemyPreset();
        
        preset.actionSound = "hit1";
        preset.deathSound = "death1";
        preset.hitAnimation = "slash";
        preset.folder = "viking";
        preset.maxHealth = 300;
        preset.strength = 70;
        preset.scale = 0.6f;
        preset.speed = 75;
        preset.timeToAttack = 8;
        preset.name = "Bad Viking";
        
        return preset;
    }
    
    @Override
    public void action() {
        super.action();
        Array<Character> targets;

        targets = TargetSelection.allEnemies(this, screen);
        for (int i = 0; i < targets.size; i++) {
            screen.visualEffectManager.addEffect(this, targets.get(i));
        }
        CharacterActions.normalAttack(this, targets);
    }
}
