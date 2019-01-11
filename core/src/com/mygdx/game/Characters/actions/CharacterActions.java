package com.mygdx.game.Characters.actions;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Characters.Character;

/**
 *
 * @author mackg
 */
public class CharacterActions {
    public static void normalAttack(Character character, Character target) {
        target.beAttacked(character.getStrength());
        if(target.isDead()) {
            System.out.println(target.getName() + " was killed by " + character.getName());
        } else {
            System.out.println(character.getName() + " attacked " + target.getName());
        }
    }
    
    public static void normalAttack(Character character, Array<Character> targets) {
        for(Character target : targets) {
            normalAttack(character, target);
        }
    }
}
