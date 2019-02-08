package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.io.File;
import java.util.Random;
import libgdxUtils.AnimatedSprite;

/**
 *
 * @author reysguep
 */
public class BackgroundManager {

    Sprite currentBackground;
    Array<String> allBackgrounds, playedBackgrounds;
    public static final String PATH = "Animations/backgrounds/battle";
    
    public BackgroundManager() {
        allBackgrounds = new Array<String>();  
        playedBackgrounds = new Array<String>();
        File battleBGs = new File(PATH);
        File[] backgroundFiles = battleBGs.listFiles();
        for (File backgroundFile : backgroundFiles) {
            String bgName = PATH + "/" + backgroundFile.getName();
            allBackgrounds.add(bgName);
        }
        
        allBackgrounds.shuffle();
    }
    
    public void draw(Batch batch) {
        currentBackground.draw(batch);
    }
    
    public void nextBackground() {
        if(allBackgrounds.size == 0) {
            allBackgrounds.addAll(playedBackgrounds);
            playedBackgrounds.clear();
        }
        
        String bgName = allBackgrounds.random();
        allBackgrounds.removeValue(bgName, true);
        playedBackgrounds.add(bgName);
        currentBackground = new Sprite(new Texture(Gdx.files.internal(bgName)));
        currentBackground.setSize(1280, 720);
    }
}
