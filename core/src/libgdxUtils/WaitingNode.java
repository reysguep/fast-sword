package libgdxUtils;

import com.badlogic.gdx.graphics.g2d.Animation;
import static libgdxUtils.VideoUtil.imageSequenceToAnimation;
import com.mygdx.game.Characters.Character;

/**
 *
 * @author mackg
 */
public class WaitingNode {

    public WaitingNode(Character character) {
        this.character = character;
        star = new AnimatedSprite(imageSequenceToAnimation("Animations/star", 0.025f));
        star.getAnimation().setPlayMode(Animation.PlayMode.LOOP);
        star.setSize(30, 30);
    }

    private final com.mygdx.game.Characters.Character character;
    private final AnimatedSprite star;

    public AnimatedSprite getStar(){
        return star;
    }

    public Character getCharacter() {
        return character;
    }
}
