package com.mygdx.game.managers;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SoundEffect;
import com.mygdx.game.exceptions.SoundEffectNotFoundException;
import com.mygdx.game.loaders.SoundEffectLoader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reysguep
 */
public class SoundEffectManager {

    private final Array<SoundEffect> soundEffetcs;
    private final HashMap<String, Integer> counter;

    public SoundEffectManager() {
        soundEffetcs = SoundEffectLoader.soundEffects;
        counter = new HashMap<>();
        
        for (SoundEffect soundEffect : soundEffetcs) {
            counter.put(soundEffect.getEffectName(), 0);
        }
    }

    public void playSoundEffect(String effectName) {
        SoundEffect soundEffect;
        int effectSize, effectIndex;

        try {
            soundEffect = getSoundEffectByName(effectName);
            effectSize = soundEffect.size();
            effectIndex = counter.get(effectName);

            if (effectIndex >= effectSize) {
                effectIndex = 0;
            }

            soundEffect.play(effectIndex);
            effectIndex++;
            counter.replace(effectName, effectIndex);

        } catch (SoundEffectNotFoundException ex) {
            Logger.getLogger(SoundEffectManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private SoundEffect getSoundEffectByName(String effectName) throws SoundEffectNotFoundException {
        SoundEffect soundEffect = null;

        for (int i = 0; i < soundEffetcs.size; i++) {
            if (soundEffetcs.get(i).getEffectName().equals(effectName)) {
                soundEffect = soundEffetcs.get(i);
                break;
            }
        }

        if (soundEffect == null) {
            throw new SoundEffectNotFoundException(effectName);
        }

        return soundEffect;
    }
}
