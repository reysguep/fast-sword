package libgdxUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author reysguep
 */
public class ColorsUtil {
    public static final Texture RED = generateColorTexture(Color.RED);
    public static final Texture BLUE = generateColorTexture(Color.BLUE);
    public static final Texture BLACK = generateColorTexture(Color.BLACK);
    public static final Texture GREEN = generateColorTexture(Color.GREEN);
    public static final Texture GOLD = generateColorTexture(Color.GOLD);
    
    public static void dispose(){
        RED.dispose();
        BLUE.dispose();
        GREEN.dispose();
        BLACK.dispose();
        GOLD.dispose();
    }
    
    public static Texture generateColorTexture(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        
        pixmap.setColor(color);
        
        pixmap.fill();
        
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        
        return texture;
    }
}
