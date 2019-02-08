package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.file.filters.AudioFilter;
import java.io.File;

/**
 *
 * @author reysguep
 */
public class MusicManager {
    
    private static final float VOLUME = 0.7f;
    private static final String DIRECTORY = "Audios/musics/battle screen";
    
    private boolean repeat;

    private final Array<String> playedMusics, musics;
    private Music currentMusic;
    
    public MusicManager() {
        musics = new Array<>();
        playedMusics = new Array<>();

        repeat = false;

        File folder = Gdx.files.internal(DIRECTORY).file();
        File[] listOfFiles = folder.listFiles(new AudioFilter());

        for (File file : listOfFiles) {
            musics.add(file.getName());
        }
    }

    public Music nextSong() {
        if(currentMusic != null) {
            currentMusic.stop();
        }
        
        if (musics.size == 0) {
            musics.addAll(playedMusics);
            playedMusics.clear();
        }

        String selectedMusic = musics.random();

        if (repeat == false) {
            playedMusics.add(selectedMusic);
            musics.removeValue(selectedMusic, true);
        }
        
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(DIRECTORY + "/" + selectedMusic));
        currentMusic.setLooping(true);
        currentMusic.setVolume(VOLUME);
        return currentMusic;
    }

    public void setRepeat(boolean set) {
        if (set == true) {
            musics.addAll(playedMusics);
            playedMusics.clear();
        }
        this.repeat = set;
    }
    
    public void stop() {
        currentMusic.stop();
    }
    
    public void pause() {
        currentMusic.pause();
    }
    
    public void play() {
        currentMusic.play();
    }
}
