package com.mygdx.game.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SoundEffect;
import com.mygdx.game.file.filters.AudioFilter;
import com.mygdx.game.file.filters.DirectoryFilter;
import java.io.File;

/**
 *
 * @author reysguep
 */
public class SoundEffectLoader {

    private static final String DEFAULT_PATH = "Audios/sounds/";

    public static Array<SoundEffect> loadSoundEffects() {
        File[] soundEffectFolders;
        Array<SoundEffect> soundEffects;
        
        soundEffects = new Array<>();
        soundEffectFolders = findSoundEffectFolders();
        for(File folder : soundEffectFolders) {
            Array<Sound> sounds;
            SoundEffect soundEffect;
            
            sounds = getSoundsFromFolder(folder);
            soundEffect = new SoundEffect(folder.getName(), sounds);
            soundEffects.add(soundEffect);
        }
        
        return soundEffects;
    }
    
    private static File[] findSoundEffectFolders() {
        File soundFolder = Gdx.files.internal(DEFAULT_PATH).file();
        File[] soundEffectFolders = soundFolder.listFiles(new DirectoryFilter());
        
        return soundEffectFolders;
    }
    
    private static Array<Sound> getSoundsFromFolder(File directory) {
        Array<Sound> sounds;
        File[] effectsFiles;
        
        sounds = new Array<>();
        effectsFiles = directory.listFiles(new AudioFilter());
        
        for(File effectFile : effectsFiles) {
            Sound sound;
            sound = Gdx.audio.newSound(Gdx.files.internal(effectFile.getPath()));
            sounds.add(sound);
        }
        
        return sounds;
    }
}
