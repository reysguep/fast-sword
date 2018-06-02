package libgdxUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reysguep
 */
public class TextureUtil {

    //Creates all character animations
    public static MultiAnimatedSprite createAnimationsCharacter(String folder) {

        MultiAnimatedSprite multiAnimations;
        Animation<TextureRegion> idle;
        Animation<TextureRegion> attacking;
        Animation<TextureRegion> dying;
        Animation<TextureRegion> running;
        Texture textura;
        Map<String, Animation> animations = new HashMap<String, Animation>();

        String nomeDaAnimacao;

        String dados[][] = splitFile(folder + "/data.txt", "/");

        idle = getRespectiveAnimation(AnimationCode.IDLE, dados, folder);
        running = getRespectiveAnimation(AnimationCode.IDLE, dados, folder);
        attacking = getRespectiveAnimation(AnimationCode.ATTACKING, dados, folder);
        dying = getRespectiveAnimation(AnimationCode.DYING, dados, folder);

        animations.put(AnimationCode.IDLE, idle);
        animations.put(AnimationCode.ATTACKING, attacking);
        animations.put(AnimationCode.DYING, dying);
        animations.put(AnimationCode.RUNNING, running);

        idle.setPlayMode(Animation.PlayMode.LOOP);
        running.setPlayMode(Animation.PlayMode.LOOP);
        multiAnimations = new MultiAnimatedSprite(animations, AnimationCode.IDLE);

        return multiAnimations;
    }

    public static String[][] splitFile(String folder, String spliter) {

        FileReader fr;
        BufferedReader br;
        ArrayList<String[]> array = new ArrayList<String[]>();

        String[] temp;
        String line;
        try {
            fr = new FileReader(folder);
            br = new BufferedReader(fr);
            
            line = br.readLine();
            while (line != null) {
                temp = line.split(spliter);
                array.add(temp);
                line = br.readLine();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextureUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TextureUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String[][] strArray = new String[array.size()][];
        for(int i = 0; i < array.size(); i ++){
            strArray[i] = array.get(i);
        }
        return strArray;
    }

    public static Animation spriteSheetToAnimation(Texture sprite, int nFrames, float time) {
        TextureRegion[][] frames;
        TextureRegion[] animationFrames;
        int frameHeight, frameWidth;
        Animation<TextureRegion> animacao;

        frameHeight = sprite.getHeight();
        frameWidth = sprite.getWidth() / nFrames;

        frames = TextureRegion.split(sprite, frameWidth, frameHeight);

        animationFrames = new TextureRegion[nFrames];

        System.arraycopy(frames[0], 0, animationFrames, 0, nFrames);

        animacao = new Animation<TextureRegion>(time, animationFrames);

        return animacao;
    }

    private static Animation getRespectiveAnimation(String nomeDaAnimacao, String[][] dados, String folder) {
        String[] dadosDaAnimacao = null;
        int tamanho = dados.length;
        int numeroDeFrames;
        float tempoEntreFrames;
        Texture textura;
        Animation animacao;

        for (int i = 0; i < tamanho; i++) {
            if (nomeDaAnimacao.equals(dados[i][2])) {
                dadosDaAnimacao = dados[i];
                break;
            }
        }

        if (dadosDaAnimacao == null) {
            System.err.println("Animação não encontrada!");
            Runtime.getRuntime().exit(-1);
            return null;
        }

        numeroDeFrames = Integer.parseInt(dadosDaAnimacao[0]);
        tempoEntreFrames = Float.parseFloat(dadosDaAnimacao[1]);
        textura = new Texture(Gdx.files.internal(folder + "/" + nomeDaAnimacao + ".png"));

        animacao = spriteSheetToAnimation(textura, numeroDeFrames, tempoEntreFrames);

        return animacao;
    }

}
