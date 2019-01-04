package com.mygdx.game.managers;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Characters.Player;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reysguep
 */
public class CharacterManager {

    private static final String[] PLAYERS = new String[]{
        "Viking",
        "Archer"
    };

    private static final String[] ENEMIES = new String[]{
        "Skeleton",
        "Slime"
    };

    private static final String PLAYERS_PACKAGE_PATH
            = "com.mygdx.game.Characters.players";

    private static final String ENEMIES_PACKAGE_PATH
            = "com.mygdx.game.Characters.enemies";

    private Array<Class<Player>> playerClasses;
    private Array<Class<Enemy>> enemyClasses;

    public CharacterManager() {
        playerClasses = new Array<>();
        enemyClasses = new Array<>();

        for (String playerClassName : PLAYERS) {
            String completeClassName = PLAYERS_PACKAGE_PATH
                    + "." + playerClassName;

            try {
                Class playerClass = Class.forName(completeClassName);
                playerClasses.add(playerClass);
                Constructor c = playerClass.getConstructor(types)
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for (String enemyClassName : ENEMIES) {
            String completeClassName = ENEMIES_PACKAGE_PATH
                    + "." + enemyClassName;

            try {
                Class enemyClass = Class.forName(completeClassName);
                enemyClasses.add(enemyClass);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
