package libgdxUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import java.io.File;

/**
 *
 * @author reysguep
 */
public class SoundUtil {
    public static Array<Sound> getSounds(String folderStr){
        Array<Sound> sounds = new Array<Sound>();
        
        File folder = new File(folderStr);
        File[] listOfFiles = folder.listFiles();
        
        for(File soundF : listOfFiles){
            sounds.add(Gdx.audio.newSound(Gdx.files.internal(folderStr + "/" + soundF.getName())));
        }
        
        return sounds;
    }
}
