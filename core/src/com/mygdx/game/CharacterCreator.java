package com.mygdx.game;

import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Screens.BattleScreen;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reysguep
 */
public class CharacterCreator {
    
    
    public static Player createPlayer(br.cefetmg.move2play.model.Player playerModel,
            Constructor<?> cons, BattleScreen screen) {
        Player player = null;
        
        try {
            player = (Player)cons.newInstance(playerModel, screen);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(CharacterCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return player;
    }
    
    public static Enemy createEnemy(Constructor<?> cons) {
        Enemy enemy = null;
        
        try {
            enemy = (Enemy)cons.newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(CharacterCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return enemy;
    }
}
