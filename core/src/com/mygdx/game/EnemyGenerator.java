package com.mygdx.game;

import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Characters.EnemyPreset;
import com.mygdx.game.Screens.BattleScreen;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import libgdxUtils.TextureUtil;

/**
 *
 * @author reysguep
 */
public class EnemyGenerator {

    public EnemyGenerator(BattleScreen screen) {
        this.screen = screen;

        presets = getPresets();
    }

    private final BattleScreen screen;
    private final ArrayList<EnemyPreset> presets;

    public void newMatch() {
        Random random;
        int nEnemies;
        int nPlayers;

        nPlayers = screen.teamA.size();
        random = new Random();
        nEnemies = random.nextInt(nPlayers) + 1;

        for (int i = 0; i < nEnemies; i++) {
            generate();
        }
    }

    private void generate() {
        Random random;
        random = new Random();
        int sortedPst;
        EnemyPreset preset;
        int health, strength;
        float timeToAttack;
        Enemy enemy;

        sortedPst = random.nextInt(presets.size());
        preset = presets.get(sortedPst);
        
        health = random.nextInt(preset.maxHealth - preset.minHealth) + preset.minHealth;
        strength = random.nextInt(preset.maxStr - preset.minStr) + preset.minStr;
        timeToAttack = random.nextInt((int)(preset.maxTTA - preset.minTTA) * 10) / 10 + preset.maxTTA;
        
        enemy = new Enemy("Skeleton", health, strength, timeToAttack, preset.folder);
        
        enemy.setSize(preset.width, preset.height);
        
        screen.teamB.addMember(enemy);
        screen.allCharacters.add(enemy);
    }

    private static ArrayList<EnemyPreset> getPresets() {
        ArrayList<EnemyPreset> presets = new ArrayList<EnemyPreset>();
        
        File folder = new File("EnemiesPresets");
        File[] listOfFiles = folder.listFiles();
        
        for (File file : listOfFiles) {
            String[][] strPst = TextureUtil.splitFile("EnemiesPresets/" + file.getName(), ";");
            EnemyPreset enemyPst = new EnemyPreset();
            enemyPst.folder = strPst[0][0];
            System.out.println(strPst[0][0]);
            enemyPst.height = Integer.parseInt(strPst[1][0]);
            enemyPst.width = Integer.parseInt(strPst[1][1]);
            enemyPst.minStr = Integer.parseInt(strPst[2][0]);
            enemyPst.maxStr = Integer.parseInt(strPst[2][1]);
            enemyPst.minHealth = Integer.parseInt(strPst[3][0]);
            enemyPst.maxHealth = Integer.parseInt(strPst[3][1]);
            enemyPst.minTTA = Float.parseFloat(strPst[4][0]);
            enemyPst.maxTTA = Float.parseFloat(strPst[4][1]);
            presets.add(enemyPst);
        }
        
        
        
        return presets;
    }

}
