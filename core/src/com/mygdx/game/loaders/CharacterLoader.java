package com.mygdx.game.loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author reysguep
 */
public class CharacterLoader {
    private static final String CHARACTERS_FOLDER = "Animations/spine/";
    private static final HashMap<String, SkeletonData> SKELETON_MAP = loadCharacters();
     
    public static SkeletonData getClassSkeleton(String className) {
        return SKELETON_MAP.get(className);
    }
    
    private static HashMap<String, SkeletonData> loadCharacters() {
        HashMap<String, SkeletonData> newMap;
        Array<File> charactersFolders;
        
        newMap = new HashMap<>();
        charactersFolders = findCharactersFolders();
        
        for(File folder : charactersFolders) {
            SkeletonData data;
            String className;
            
            data = createSkeletonData(folder);
            className = folder.getName();
            
            newMap.put(className, data);
        }
        
        return newMap;
    }
    
    private static Array<File> findCharactersFolders() {
        Array<File> charactersFolders;
        File assetsFolder;
        
        charactersFolders = new Array<>();
        assetsFolder = new File(CHARACTERS_FOLDER);
        charactersFolders.addAll(assetsFolder.listFiles());
        return charactersFolders;
    }
    
    private static SkeletonData createSkeletonData(File folder) {
        SkeletonData data;
        TextureAtlas atlas;
        SkeletonJson json;
        String directoryPath;
        
        directoryPath = CHARACTERS_FOLDER + folder.getName() + "/";
        atlas = new TextureAtlas(
                Gdx.files.internal(directoryPath + "map.atlas"));
        json = new SkeletonJson(atlas);
        data = json.readSkeletonData(
                Gdx.files.internal(directoryPath + "data.json"));
        
        return data;
    }
}
