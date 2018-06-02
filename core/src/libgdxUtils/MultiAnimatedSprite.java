package libgdxUtils;

import com.badlogic.gdx.graphics.g2d.Animation;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * coutinho
 */
public class MultiAnimatedSprite extends AnimatedSprite {

    private final Map<String, Animation> animations;
    private final Map<String, SpriteFlip> flips;
    
    private String currentAnimation;

    public MultiAnimatedSprite(Map<String, Animation> animations,
            String initialAnimationName) {
        super(animations.get(initialAnimationName));
        this.animations = animations;
        flips = new HashMap<String, SpriteFlip>();
        
        for(String animationName : animations.keySet()){
            SpriteFlip newFlip = new SpriteFlip(false, false);
            flips.put(animationName, newFlip);
        }
    }

    public void startAnimation(String animationName) {
        if (!animations.containsKey(animationName)) {
            throw new IllegalArgumentException(
                    "Pediu-se para iniciar uma animação com o nome + '"
                    + animationName + "', mas esta MultiAnimatedSprite"
                    + "não possui uma animação com esse nome");
        }

        //Configurando o time que define o frame de ínicio da animação para zero by Bruno e Carlos 
        super.setTime(0);

        super.setAnimation(animations.get(animationName));
        currentAnimation = animationName;

        super.flipX = flips.get(animationName).flipX;
        super.flipY = flips.get(animationName).flipY;
    }

    public Animation getAnimation(String nomeAnimacao) {
        return animations.get(nomeAnimacao);
    }
    
    
    public void flipAllAnimations(boolean flipX, boolean flipY) {
        AnimatedSprite as;
        for(Animation animation : animations.values()){
            as = new AnimatedSprite(animation);
            as.flipFrames(flipX, flipY);
        }
    }
    
    @Override
    public void flipFrames(boolean flipX, boolean flipY) {
        super.flipFrames(flipX, flipY);
        flips.get(currentAnimation).set(super.flipX, super.flipY);
    }

    @Override
    public void flipFrames(boolean flipX, boolean flipY, boolean set) {
        super.flipFrames(flipX, flipY, set);
        flips.get(currentAnimation).set(super.flipX, super.flipY);
    }

    @Override
    public void flipFrames(float startTime, float endTime, boolean flipX, boolean flipY) {
        super.flipFrames(startTime, endTime, flipX, flipY);
        flips.get(currentAnimation).set(super.flipX, super.flipY);
    }

    @Override
    public void flipFrames(float startTime, float endTime, boolean flipX, boolean flipY, boolean set) {
        super.flipFrames(startTime, endTime, flipX, flipY, set);
        flips.get(currentAnimation).set(super.flipX, super.flipY);
    }
}
