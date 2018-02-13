package com.mygdx.game.Screens;

import br.cefetmg.move2play.game.Move2PlayGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Background;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Characters.Gunman;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Team;
import com.mygdx.game.Characters.Warrior;
import java.util.ArrayList;
import java.util.Random;
import libgdxUtils.AnimationCode;
import libgdxUtils.DrawingCharacter;
import static libgdxUtils.AnimationCode.*;
import libgdxUtils.KeyboardUtil;

/**
 *
 * @author reysguep
 */
public class BattleScreen implements Screen, Move2PlayGame {

    public BattleScreen(MyGdxGame game, Team teamA) {
        this.game = game;
        this.teamA = teamA;
    }

    private final Team teamA;
    private Team teamB;
    private ArrayList<Character> allCharacters;
    private Background fundo;
    private DrawingCharacter drawing;
    private final MyGdxGame game;
    private SpriteBatch batch;

    @Override
    public void show() {

        teamB = new Team('b');
        teamB.addMember(new Enemy("Skeleton", 1000, 20, 3.0f, "blob"));
        allCharacters = new ArrayList<Character>();
        
        allCharacters.addAll(teamA);
        allCharacters.addAll(teamB);

        batch = new SpriteBatch();

        fundo = new Background();
        drawing = new DrawingCharacter(batch);

        game.eventHandler = this;
        Gdx.input.setInputProcessor(new KeyboardUtil(this));
    }
    
    public void checarCondicoes() {
        Random random = new Random();
        int targetNumber;
        Character target;

        for (Character personagem : allCharacters) {
            Team targets;
            if (teamA.contains(personagem)) {
                targets = teamB;
            } else {
                targets = teamA;
            }
            if (!personagem.isDead()) {
                if (personagem.compareAnimation(ATTACKING)) {
                    if (personagem.getAnimations().getAnimation().isAnimationFinished(personagem.getAnimations().getTime())) {
                        personagem.setAnimation(AnimationCode.IDLE);
                    }
                }

                if (personagem.canAttack()) {

                    boolean naoAtacou = true;

                    while (naoAtacou && !targets.isEmpty()) { //Enquanto não atacou e a lista de alvos não é vazia
                        
                        targetNumber = random.nextInt(targets.size());
                        target = targets.get(targetNumber);

                        if (!target.isDead()) { //Se o alvo não está morto
                            personagem.attack(target);
                            naoAtacou = false;
                        } else {
                            targets.remove(target);
                        }
                    } 
                }
            }
        }

    }

    @Override
    public void dispose() {
        drawing.dispose();
        batch.dispose();

    }

    @Override
    public void move(String uuid, int n) {
        Player player = searchPlayer(uuid);
        if (player != null) { //Se a referência não for nula
            if (!player.isDead()) { // e o jogador não estiver morto.
                player.contarPedalada(n);
            }
        }
    }

    public Player searchPlayer(String str) {
        for (Character character : allCharacters) {
            if (character instanceof Player) {
                Player player = (Player) character;
                if (player.getUuid().equals(str) || player.getName().equals(str)) {
                    return player;
                }
            }
        }

        return null;
    }

    

    @Override
    public void render(float f) {
        checarCondicoes();
        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        fundo.draw(batch);

        for (Character personagem : allCharacters) {
            drawing.draw(personagem);
        }

        batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void initGame() {

    }

    @Override
    public void closeGame() {

    }

    @Override
    public void startMatch() {

    }

    @Override
    public void finishMatch() {

    }

    @Override
    public void addPlayer(br.cefetmg.move2play.model.Player player) {
        Random random = new Random();
        int sortedN = random.nextInt(2);
        Player plyr = new Warrior(player);

        switch (sortedN) {
            case 0:
                plyr = new Warrior(player);
                break;
            case 1:
                plyr = new Gunman(player);
                break;
            case 2:
                //plyr = new Archer(player);
                break;
        }

        allCharacters.add(plyr);
        teamA.add(plyr);
    }

    public void addPlayer(String name) {
        Random random = new Random();
        int sortedN = random.nextInt(2);
        Player plyr = new Warrior(name);

        switch (sortedN) {
            case 0:
                plyr = new Warrior(name);
                break;
            case 1:
                plyr = new Gunman(name);
                break;
            case 2:
                //plyr = new Archer(player);
                break;
        }

        allCharacters.add(plyr);
        teamA.add(plyr);
    }

    @Override
    public void removePlayer(br.cefetmg.move2play.model.Player player) {
        Player plyr = searchPlayer(player.getUUID());
        if (plyr != null) {
            allCharacters.remove(plyr);
            if (teamA.contains(plyr)) {
                teamA.remove(plyr);
            } else {
                teamB.remove(plyr);
            }
        }
    }

    public void removePlayer(String name) {
        Player plyr = searchPlayer(name);
        if (plyr != null) {
            allCharacters.remove(plyr);
            if (teamA.contains(plyr)) {
                teamA.remove(plyr);
            } else {
                teamB.remove(plyr);
            }
        }
    }

}
