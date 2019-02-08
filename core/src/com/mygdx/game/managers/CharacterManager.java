package com.mygdx.game.managers;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.CharacterCreator;
import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Screens.BattleScreen;
import java.util.Random;

/**
 *
 * @author reysguep
 */
public class CharacterManager {

    private int playerIndex, enemyIndex;
    private final Array<Integer> enemyShuffler;
    private final int roundsToIntroduceEnemy;
    private int newEnemyIn;
    private final BattleScreen screen;

    public CharacterManager(BattleScreen screen, int newEnemy) {
        this.screen = screen;
        this.roundsToIntroduceEnemy = newEnemy;
        this.newEnemyIn = 0;
        this.playerIndex = 0;
        this.enemyIndex = 0;
        enemyShuffler = new Array<>();

        for (int i = 0; i < CharacterCreator.ENEMY_CLASSES; i++) {
            enemyShuffler.add(i);
        }

        enemyShuffler.shuffle();
    }

    public Player newPlayer(br.cefetmg.move2play.model.Player playerModel) {
        Player player;

        if (playerIndex == CharacterCreator.PLAYER_CLASSES) {
            playerIndex = 0;
        }

        player = CharacterCreator.createPlayer(playerIndex, playerModel, screen);
        playerIndex++;

        return player;
    }

    public void newRound() {
        int aTeamMembers, bTeamMembers;
        int enemiesN;
        Random random;
        Array<Enemy> enemiesList;

        aTeamMembers = screen.teamA.size;
        bTeamMembers = screen.teamB.size;
        enemiesList = new Array<>();
        random = new Random();

        if (newEnemyIn == 0) {
            enemiesList.add(newEnemy(enemyIndex));
            
            if(enemyIndex < CharacterCreator.ENEMY_CLASSES - 1) {
                enemyIndex++;
                newEnemyIn = roundsToIntroduceEnemy;
            } else {
                newEnemyIn = -1;
            }
        } else {
            enemiesN = random.nextInt(aTeamMembers) + 1;
            
            for (int i = 0; i < enemiesN; i++) {
                enemiesList.add(newEnemy());
            }
            
            if(enemyIndex < CharacterCreator.ENEMY_CLASSES) {
                newEnemyIn--;
            }
        }
        
        screen.teamB.addAll(enemiesList);
        newEnemyIn--;
    }

    public Enemy newEnemy() {
        Enemy enemy;
        Random random;
        int classNumber;

        random = new Random();
        classNumber = random.nextInt(enemyIndex + 1);

        enemy = newEnemy(classNumber);
        return enemy;
    }

    public Enemy newEnemy(int classNumber) {
        Enemy enemy;

        enemy = CharacterCreator.createEnemy(enemyShuffler.get(classNumber), screen);

        return enemy;
    }
}
