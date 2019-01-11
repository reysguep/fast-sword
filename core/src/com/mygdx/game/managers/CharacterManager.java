package com.mygdx.game.managers;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.CharacterCreator;
import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.exceptions.EmptyCharacterClassesException;
import com.mygdx.game.loaders.CharacterLoader;
import java.lang.reflect.Constructor;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reysguep
 */
public class CharacterManager {

    private final Array<Constructor<?>> playerClasses, enemyClasses;
    private int playerIndex, enemyIndex;
    private final int roundsToIntroduceEnemy;
    private int newEnemyIn;
    private final BattleScreen screen;

    public CharacterManager(BattleScreen screen, int newEnemy) {
        this.screen = screen;
        this.roundsToIntroduceEnemy = newEnemy;
        this.newEnemyIn = newEnemy;
        this.playerIndex = 0;
        this.enemyIndex = 0;

        this.playerClasses = CharacterLoader.getPlayerConstructors();
        this.enemyClasses = CharacterLoader.getEnemyConstructors();

        this.enemyClasses.shuffle();
    }

    public Player newPlayer(br.cefetmg.move2play.model.Player playerModel) {
        Player player;

        if (playerClasses.isEmpty()) {
            try {
                throw new EmptyCharacterClassesException("Player");
            } catch (EmptyCharacterClassesException ex) {
                Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (playerIndex == playerClasses.size) {
            playerIndex = 0;
        }

        player = CharacterCreator.createPlayer(playerModel,
                playerClasses.get(playerIndex), screen);
        playerIndex++;

        return player;
    }

    public void newRound() {
        int aTeamMembers, bTeamMembers;
        int enemies;
        Random random;
        Enemy enemy;

        aTeamMembers = screen.teamA.size;
        bTeamMembers = screen.teamB.size;
        random = new Random();

        if (newEnemyIn <= 0) {
            if (enemyIndex < bTeamMembers) {
                enemyIndex++;
                newEnemyIn = roundsToIntroduceEnemy;

                enemy = newEnemy(enemyIndex);
                screen.teamB.add(enemy);
            }
        } else {
            enemies = random.nextInt(bTeamMembers) + 1;
            for (int i = 0; i < enemies; i++) {
                enemy = newEnemy();

                screen.teamB.add(enemy);
            }
        }
        newEnemyIn--;
    }

    public Enemy newEnemy() {
        Enemy enemy;
        Random random;
        int classNumber;

        random = new Random();
        classNumber = random.nextInt(enemyIndex);

        enemy = newEnemy(classNumber);
        return enemy;
    }

    public Enemy newEnemy(int classNumber) {
        Enemy enemy;

        if (enemyClasses.isEmpty()) {
            try {
                throw new EmptyCharacterClassesException("Enemy");
            } catch (EmptyCharacterClassesException ex) {
                Logger.getLogger(CharacterManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        enemy = CharacterCreator.createEnemy(playerClasses.get(classNumber));
        return enemy;
    }
}
