package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reysguep
 */
public class GdxUtil {
    public static Animation<TextureRegion> getAnimationAt(String folder, Animation.PlayMode playmode,
            boolean invertX, boolean invertY){
        Animation<TextureRegion> animation;
        TextureRegion tr[];
        int frames = 1;
        float secondsPerFrame = 1;

        File file = new File(folder + "/info.txt");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try {
                frames = Integer.parseInt(br.readLine());
                secondsPerFrame = Float.parseFloat(br.readLine());
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        tr = new TextureRegion[frames];
        for (int i = 0; i < frames; i++) {
            tr[i] = new TextureRegion(new Texture(Gdx.files.internal(folder + "/" + i + ".png")));
            tr[i].flip(invertX, invertY);
        }
        animation = new Animation<TextureRegion>(secondsPerFrame, tr);
        animation.setPlayMode(playmode);
        
        return animation;
    }
    
    public static Animation<TextureRegion> getAnimationAt(String folder, Animation.PlayMode playmode){
        Animation<TextureRegion> animation;
        TextureRegion tr[];
        int frames = 1;
        float secondsPerFrame = 1;

        File file = new File(folder + "/info.txt");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try {
                frames = Integer.parseInt(br.readLine());
                secondsPerFrame = Float.parseFloat(br.readLine());
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        tr = new TextureRegion[frames];
        for (int i = 0; i < frames; i++) {
            tr[i] = new TextureRegion(new Texture(Gdx.files.internal(folder + "/" + i + ".png")));
        }
        animation = new Animation<TextureRegion>(secondsPerFrame, tr);
        animation.setPlayMode(playmode);
        
        return animation;
    }
    
    public static Animation<TextureRegion> getAnimationAt(String folder){
        Animation<TextureRegion> animation;
        TextureRegion tr[];
        int frames = 1;
        float secondsPerFrame = 1;

        File file = new File(folder + "/info.txt");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try {
                frames = Integer.parseInt(br.readLine());
                secondsPerFrame = Float.parseFloat(br.readLine());
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        tr = new TextureRegion[frames];
        for (int i = 0; i < frames; i++) {
            tr[i] = new TextureRegion(new Texture(Gdx.files.internal(folder + "/" + i + ".png")));
        }
        animation = new Animation<TextureRegion>(secondsPerFrame, tr);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        
        return animation;
    }
    
    public static Animation<TextureRegion> getAnimationAt(String folder, boolean invertX, boolean invertY){
        Animation<TextureRegion> animation;
        TextureRegion tr[];
        int frames = 1;
        float secondsPerFrame = 1;

        File file = new File(folder + "/info.txt");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try {
                frames = Integer.parseInt(br.readLine());
                secondsPerFrame = Float.parseFloat(br.readLine());
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        tr = new TextureRegion[frames];
        for (int i = 0; i < frames; i++) {
            tr[i] = new TextureRegion(new Texture(Gdx.files.internal(folder + "/" + i + ".png")));
            tr[i].flip(invertX, invertY);
        }
        animation = new Animation<TextureRegion>(secondsPerFrame, tr);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        
        return animation;
    }
}
