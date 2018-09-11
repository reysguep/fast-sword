package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import libgdxUtils.FileUtil;

/**
 *
 * @author reysguep
 */
public class Assets {
    public Assets() {
        manager = new AssetManager();
    }
    
    public AssetManager manager;
    
    public static Map<String, Animation> animations = new HashMap<String, Animation>();
    
    public ArrayList<String> load(String path){
        ArrayList<String> names = FileUtil.allFileNames(path, 0, true);
        manager.load("Animations/star/frame_00_delay-0.01s.png", Texture.class);
        for(String file : names) {
            manager.load(file, Texture.class);
        }
        
        return names;
    }
}
