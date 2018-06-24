package com.mygdx.game.Characters;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author reysguep
 */
public class CharacterPreset {
    public String folder, actionCode, deathCode, targetAnimation;
    
    public Array<Sound> hitSounds, deathSounds;
    
    public int width, height,
            maxHealth,
            maxStr;
}
