package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author reysguep
 */
public class SoundEffect {

    private final String effectName;
    private final Array<Sound> sounds;

    public SoundEffect(String effectName, Array<Sound> sounds) {
        this.effectName = effectName;
        this.sounds = sounds;
    }
    
    public void play(int index) {
        Sound sound;
        sound = sounds.get(index);
        sound.play();
    }

    public String getEffectName() {
        return effectName;
    }

    public Array<Sound> getSounds() {
        return sounds;
    }
    
    public int size() {
        return sounds.size;
    }
}
