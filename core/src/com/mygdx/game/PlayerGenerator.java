package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Characters.PlayerPreset;
import java.io.File;
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

    private Array getPresets() {
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
            
            psts.add(pyrPst);
        }
        return psts;
    }
    
    public Player newPlayer(br.cefetmg.move2play.model.Player player){
        Player plyr = new Player(player, presets.random());
        
        return plyr;
    }
    public Player newPlayer(String name){
        Player plyr = new Player(name, presets.random());
        
        return plyr;
    }
}
