package libgdxUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reysguep
 */
public class TextureUtil {

    public static String[][] splitFile(File file, String spliter) {

        FileReader fr;
        BufferedReader br;
        ArrayList<String[]> array = new ArrayList<>();

        String[] temp;
        String line;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            line = br.readLine();
            while (line != null) {
                temp = line.split(spliter);
                array.add(temp);
                line = br.readLine();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextureUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TextureUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[][] strArray = new String[array.size()][];
        for (int i = 0; i < array.size(); i++) {
            strArray[i] = array.get(i);
        }
        return strArray;
    }

    public static Animation spriteSheetToAnimation(Texture sprite, int nFrames, float time) {
        TextureRegion[][] frames;
        TextureRegion[] animationFrames;
        int frameHeight, frameWidth;
        Animation<TextureRegion> animacao;

        frameHeight = sprite.getHeight();
        frameWidth = sprite.getWidth() / nFrames;

        frames = TextureRegion.split(sprite, frameWidth, frameHeight);

        animationFrames = new TextureRegion[nFrames];

        System.arraycopy(frames[0], 0, animationFrames, 0, nFrames);

        animacao = new Animation<TextureRegion>(time, animationFrames);

        return animacao;
    }
}
