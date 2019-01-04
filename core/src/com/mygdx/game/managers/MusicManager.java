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

    public MusicManager(String folderName) {
        musics = new Array<String>();
        playedMusics = new Array<String>();

        repeat = false;
        this.folderName = folderName;

        File folder = new File(folderName);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            musics.add(file.getName());
        }
    }

    private final String folderName;
    private boolean repeat;

    private Array<String> playedMusics, musics;

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
        
        Music music = Gdx.audio.newMusic(Gdx.files.internal(folderName + "/" + selectedMusic));
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
