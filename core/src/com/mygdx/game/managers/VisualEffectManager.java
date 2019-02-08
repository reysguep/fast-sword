package com.mygdx.game.managers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.VisualEffect;
import com.mygdx.game.loaders.VisualEffectLoader;
import com.mygdx.game.Characters.Character;
import java.util.HashMap;

/**
 *
 * @author reysguep
 */
public class VisualEffectManager {
    private final HashMap<String, Animation<TextureRegion>> visualEffects;
    private final Array<VisualEffect> activeEffects;
    
    public VisualEffectManager() {
        this.visualEffects = VisualEffectLoader.visualEffects;
        this.activeEffects = new Array<>();
    }
    
    public void addEffect(Character character, Character target) {
        VisualEffect effect;
        Animation<TextureRegion> animation;
        
        animation = visualEffects.get(character.getTargetAnimation());
        effect = new VisualEffect(target, animation);
        
        activeEffects.add(effect);
    }
    
    public void draw(Batch batch) {
        update();
        for(VisualEffect effect : activeEffects) {
            effect.draw(batch);
        }
    }
    
    private void update() {
        Array<VisualEffect> finishedEffects;
        finishedEffects = new Array<>();
        
        for(VisualEffect effect : activeEffects) {
            if(effect.isComplete()) {
                finishedEffects.add(effect);
            }
        }
        
        activeEffects.removeAll(finishedEffects, false);
    }
}
