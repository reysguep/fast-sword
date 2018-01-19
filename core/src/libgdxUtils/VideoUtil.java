package libgdxUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.io.File;

/**
 *
 * @author reysguep
 */
public class VideoUtil {

    public static Animation<TextureRegion> imageSequenceToAnimation(String url, float frameDuration) {
        File folder = new File(url);
        File[] listOfFiles = folder.listFiles();

        Animation<TextureRegion> animation;
        TextureRegion texture;
        TextureRegion[] frames = new TextureRegion[listOfFiles.length];

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                texture = new TextureRegion(new Texture(Gdx.files.internal(url + "/" + listOfFiles[i].getName())));
                frames[i] = texture;

            }
        }
        return new Animation<TextureRegion>(frameDuration, frames);
    }

    public static Animation<TextureRegion> imageSequenceToAnimation(String url, int fps) {
        float ffps = fps;
        float frameDuration = 1 / ffps;
        return imageSequenceToAnimation(url, frameDuration);
    }
}
