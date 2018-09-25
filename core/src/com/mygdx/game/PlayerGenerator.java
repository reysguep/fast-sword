package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Characters.CharacterPreset;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Characters.PlayerPreset;
import java.io.File;
import java.util.Random;
import libgdxUtils.SoundUtil;
import libgdxUtils.TextureUtil;

/**
 *
 * @author reysguep
 */
public class PlayerGenerator {

    public PlayerGenerator() {
        presets = getPresets();
    }

    private final Array<PlayerPreset> presets;

    private static Array getPresets() {
        Array psts = new Array();

        File folder = new File("Presets/players");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {        
            String[][] strPst = TextureUtil.splitFile("Presets/players/" + file.getName(), ";");
            PlayerPreset pyrPst = new PlayerPreset();

            pyrPst.folder = "player/" + strPst[0][0];
            pyrPst.hitSounds = SoundUtil.getSounds("Audios/sounds/" + strPst[0][1]);
            pyrPst.deathSounds = SoundUtil.getSounds("Audios/sounds/" + strPst[0][2]);
            pyrPst.targetAnimation = strPst[0][3];
            pyrPst.actionCode = strPst[1][0];
            pyrPst.deathCode = strPst[1][1];
            pyrPst.pedaladasMinimas = Integer.parseInt(strPst[2][0]);
            pyrPst.width = Integer.parseInt(strPst[3][0]);
            pyrPst.height = Integer.parseInt(strPst[3][1]);
            pyrPst.maxHealth = Integer.parseInt(strPst[4][0]);
            pyrPst.maxStr = Integer.parseInt(strPst[5][0]);
            pyrPst.speed = Integer.parseInt(strPst[6][0]);
            
            psts.add(pyrPst);
        }
        return psts;
    }
    
    public Player newRandomPlayer(br.cefetmg.move2play.model.Player player){
        PlayerPreset choosedPreset = presets.random();
        Player plyr = new Player(player, choosedPreset);

        return plyr;
    }
    
    public Player newRandomPlayer(br.cefetmg.move2play.model.Player player, int pClass){
        Player plyr = new Player(player, presets.get(pClass));
        
        return plyr;
    }
    
    public Array<Player> sortClasses(Array<br.cefetmg.move2play.model.Player> players) {
        Array<Player> sortedPlayers = new Array<Player>();
        for(br.cefetmg.move2play.model.Player plyr : players) {
            int chosenClass = classesDiagram(sortedPlayers);
            Player player = newRandomPlayer(plyr, chosenClass);
            sortedPlayers.add(player);
        }
        
        return sortedPlayers;
    }
    
    private int classesDiagram(Array<Player> sortedPlayers) {
        Random random = new Random();
        int chosenN;
        int chosenClass;
        Array<Integer> possiblePresets = new Array<Integer>();
        
        for(CharacterPreset preset : presets) {
            int n = 0; //players who use this preset
            int presetNumber = presetToNumber(preset);
            
            for(Player player : sortedPlayers) {
                int playerPreset = presetToNumber(player.preset);
                if(presetNumber == playerPreset) {
                    n++;
                }
            }
            
            if(presetNumber == 1 && sortedPlayers.size == 3 && n == 0) {
                return 1;
            }
            if((n < 2 && presetNumber != 1) || presetNumber == 1 && sortedPlayers.size > 0 && n == 0) {
                possiblePresets.add(presetNumber);
            }
        }

        chosenN = random.nextInt(possiblePresets.size);
        chosenClass = possiblePresets.get(chosenN);
        
        return chosenClass;
    }
    
    public int presetToNumber(CharacterPreset preset) {
        for(int i = 0; i < presets.size; i++) {
            if(preset.folder.equals(presets.get(i).folder)) {
                return i;
            }
        }
        return 0;
    }
}
