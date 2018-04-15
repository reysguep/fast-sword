package libgdxUtils;

import aurelienribon.tweenengine.TweenAccessor;
import com.mygdx.game.Characters.Character;
/**
 *
 * @author reysguep
 */
public class CharacterAccessor implements TweenAccessor<Character>{

    public static final int POS_X = 1;

    @Override
    public int getValues(Character target, int tweenType,
            float[] returnValues) {
        switch (tweenType) {
            case POS_X:
                returnValues[0] = target.getX();
                return 1;
                
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Character target, int tweenType,
            float[] newValues) {
        switch (tweenType) {
            case POS_X:
                target.setPosition(newValues[0], target.orgY);
                break;
                
            default:
                assert false;
        }
    }
}
