package com.mygdx.game.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.file.filters.DirectoryFilter;
import java.io.File;
import java.util.HashMap;
import java.util.logging.FileHandler;
import libgdxUtils.TextureUtil;

/**
 *
 * @author reysguep
 */

//ATENÇÃO: ESSA CLASSE UTILIZA RECURSOS ANTIGOS E MAL IDENTADOS
public class VisualEffectLoader {
    
    public static final String VISUAL_EFFECTS_FOLDER = "Animations/visual effects/";
    public static HashMap<String, Animation<TextureRegion>> visualEffects;
    
    public static void load() {
        visualEffects = new HashMap<>();
        File[] folders = findVisualEffectsFolders();
        
        for(File folder : folders) {
            Animation<TextureRegion> animation;
            
            animation = createVisualEffectAnimation(folder);
            visualEffects.put(folder.getName(), animation);
        }
    }
    
    private static File[] findVisualEffectsFolders() {
        File[] folders;
        File effectsFolder;
        
        effectsFolder = Gdx.files.internal(VISUAL_EFFECTS_FOLDER).file();
        folders = effectsFolder.listFiles(new DirectoryFilter());
        
        return folders;
    }
    
    
    private static Animation<TextureRegion> createVisualEffectAnimation(File folder) {
        Animation<TextureRegion> animation;
        int rows;
        float secondsPerFrame;
        String folderPath;
        File data;
        String[] animationData;
        Texture animationSheet;
                
        folderPath = VISUAL_EFFECTS_FOLDER + folder.getName();
        data = Gdx.files.internal(folderPath + "/info.txt").file();
        animationData = TextureUtil.splitFile(data, ";")[0];
        
        rows = Integer.parseInt(animationData[0]);
        secondsPerFrame = Float.parseFloat(animationData[1]);
        
        animationSheet = new Texture(Gdx.files.internal(folderPath + "/effect.png"));
        animation = TextureUtil.spriteSheetToAnimation(animationSheet, rows, secondsPerFrame);
        
        return animation;
    }
}
