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

    private static final Array<SoundEffect> SOUND_EFFECTS = SoundEffectLoader.loadSoundEffects();
    private static HashMap<String, Integer> counter;

    private static HashMap<String, Integer> resetCount() {
        HashMap<String, Integer> newCounter;

        newCounter = new HashMap<>();

        for (SoundEffect soundEffect : SOUND_EFFECTS) {
            newCounter.put(soundEffect.getEffectName(), 0);
        }

        return newCounter;
    }

    public static void playSoundEffect(String effectName) {
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

    private static SoundEffect getSoundEffectByName(String effectName) throws SoundEffectNotFoundException {
        SoundEffect soundEffect = null;

        for (int i = 0; i < SOUND_EFFECTS.size; i++) {
            if (SOUND_EFFECTS.get(i).getEffectName().equals(effectName)) {
                soundEffect = SOUND_EFFECTS.get(i);
                break;
            }
        }

        if (soundEffect == null) {
            throw new SoundEffectNotFoundException(effectName);
        }

        return soundEffect;
    }
}
