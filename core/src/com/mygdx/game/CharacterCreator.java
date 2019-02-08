package com.mygdx.game;

import com.mygdx.game.Characters.*;
import com.mygdx.game.Characters.enemies.*;
import com.mygdx.game.Characters.players.*;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.exceptions.CharacterClassNotFound;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reysguep
 */
public class CharacterCreator {

    public static int PLAYER_CLASSES = 1, ENEMY_CLASSES = 1;
    
    public static Player createPlayer(int classNumber, br.cefetmg.move2play.model.Player playerModel,
            BattleScreen screen) {

        Player player;

        switch (classNumber) {
            case 0:
                player = new Viking(playerModel, screen);
                break;

            default: {
                try {
                    throw new CharacterClassNotFound("PLAYER", classNumber);
                } catch (CharacterClassNotFound ex) {
                    Logger.getLogger(CharacterCreator.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    player = null;
                }
            }
        }

        return player;
    }

    public static Enemy createEnemy(int classNumber, BattleScreen screen) {
        Enemy enemy;

        switch (classNumber) {
            case 0:
                enemy = new BadViking(screen);
                break;

            default: {
                try {
                    throw new CharacterClassNotFound("PLAYER", classNumber);
                } catch (CharacterClassNotFound ex) {
                    Logger.getLogger(CharacterCreator.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    enemy = null;
                }
            }
        }
        
        return enemy;
    }
}
