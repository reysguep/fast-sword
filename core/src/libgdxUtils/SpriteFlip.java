package libgdxUtils;

/**
 *
 * @author reysguep
 */
public class SpriteFlip {
    public SpriteFlip(boolean flipX, boolean flipY){
        this.flipX = flipX;
        this.flipY = flipY;
    }
    
    public boolean flipX = false, flipY = false;
    
    public void set(boolean flipX, boolean flipY){
        this.flipX = flipX;
        this.flipY = flipY;
    }
}
