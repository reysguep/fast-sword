package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import java.io.File;

/**
 *
 * @author reysguep
 */
public class MusicManager {
    
    private static final String DIRECTORY = "Audios/musics/battle screen";
    
    private boolean repeat;

    private final Array<String> playedMusics, musics;
    
    public MusicManager() {
        musics = new Array<>();
        playedMusics = new Array<>();

        repeat = false;

        File folder = Gdx.files.internal(DIRECTORY).file();
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            musics.add(file.getName());
        }
    }

    public Music nextSong() {
        if (musics.size == 0) {
            musics.addAll(playedMusics);
            playedMusics.clear();
        }

        String selectedMusic = musics.random();

        if (repeat == false) {
            playedMusics.add(selectedMusic);
            musics.removeValue(selectedMusic, true);
        }
        
        Music music = Gdx.audio.newMusic(Gdx.files.internal(DIRECTORY + "/" + selectedMusic));
        music.setLooping(true);
        return music;
    }

    public void setRepeat(boolean set) {
        if (set == true) {
            musics.addAll(playedMusics);
            playedMusics.clear();
        }
        this.repeat = set;
    }

}
