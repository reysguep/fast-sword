package com.mygdx.game.Characters.players;

import com.mygdx.game.Characters.Player;
import com.mygdx.game.Characters.PlayerPreset;
import com.mygdx.game.Team;

/**
 *
 * @author reysguep
 */
public class Viking extends Player{
    
    
    
    public Viking(char team) {
        PlayerPreset preset = new PlayerPreset();
        
        preset.actionSound = "hit1";
        preset.deathSound = "death1";
        preset.hitAnimation = "slash";
        preset.folder = "player/viking/";
        preset.maxHealth = 300;
        preset.strength = 70;
        preset.scale = 1.0f;
        preset.speed = 75;
    }
    
    @Override
    public void action() {
        super.action();
    }
}
