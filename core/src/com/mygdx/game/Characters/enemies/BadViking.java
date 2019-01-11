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
        preset.folder = "player/viking/";
        preset.maxHealth = 300;
        preset.strength = 70;
        preset.scale = 1.0f;
        preset.speed = 75;
        preset.timeToAttack = 8;
        
        return preset;
    }
    
    @Override
    public void action() {
        super.action();
        Array<Character> targets;
        
        targets = TargetSelection.allEnemies(this, screen);
        CharacterActions.normalAttack(this, targets);
    }
}
