package com.mygdx.game.Screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;
import br.cefetmg.move2play.game.Move2PlayGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Background;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Gunman;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Team;
import com.mygdx.game.Characters.Warrior;
import com.mygdx.game.EnemyGenerator;
import java.util.ArrayList;
import java.util.Random;
import libgdxUtils.AnimationCode;
import libgdxUtils.DrawingCharacter;
import libgdxUtils.CharacterAccessor;
import libgdxUtils.ColorsUtil;
import libgdxUtils.KeyboardUtil;
import libgdxUtils.SpriteAcessor;
import static libgdxUtils.StatusCode.*;

/**
 *
 * @author reysguep
 */
public class BattleScreen implements Screen, Move2PlayGame {

    public BattleScreen(MyGdxGame game, Team teamA) {
        this.game = game;
        this.teamA = teamA;
    }

    public Team teamA, teamB;
    public ArrayList<Character> allCharacters;
    private Background background;
    private DrawingCharacter drawing;
    private final MyGdxGame game;
    private SpriteBatch batch;
    private EnemyGenerator generator;
    private Music music;
    private static TweenManager tweenManager;
    private Sprite transitionColor;

    private int battleStatus; // 0 = battle; 1 = transition; 2 = ...

    @Override
    public void show() {
        battleStatus = 0;

        tweenManager = new TweenManager();
        Tween.setCombinedAttributesLimit(4);
        Tween.registerAccessor(Character.class, new CharacterAccessor());
        Tween.registerAccessor(Sprite.class, new SpriteAcessor());

        teamB = new Team('b');
        allCharacters = new ArrayList<Character>();

        allCharacters.addAll(teamA);
        batch = new SpriteBatch();
        transitionColor = new Sprite(ColorsUtil.BLACK);
        transitionColor.setSize(1280, 720);
        transitionColor.setAlpha(0);

        background = new Background();
        drawing = new DrawingCharacter(batch);

        generator = new EnemyGenerator(this);

        game.eventHandler = this;
        Gdx.input.setInputProcessor(new KeyboardUtil(this));

        for (Character chr : teamA) {
            chr.setSize(280, 350);
        }
        generator.newMatch();
    }

    private void nextLevel() {
        if (battleStatus == 0) {
            boolean allIdle = true;
            for (Character chr : teamA) {
                if (chr.getStatus() != WAITING) {
                    allIdle = false;
                    return;
                }
            }

            for (Character chr : teamA) {
                Tween.to(chr, CharacterAccessor.POS_X, 3.0f).target(chr.getX() + 750).start(tweenManager);
                chr.setAnimation(AnimationCode.RUNNING);
            }
            Tween.to(transitionColor, SpriteAcessor.ALPHA, 2.7f).target(1).start(tweenManager);
            battleStatus = 1;
        } else {
            if (!tweenManager.containsTarget(transitionColor)) {
                System.out.println("OPA");
                for (Character chr : teamA) {
                    tweenManager.killTarget(chr);
                    chr.setPosition(chr.orgX, chr.orgY);
                    chr.setAnimation(AnimationCode.IDLE);
                    generator.newMatch();
                    background = new Background();
                    Tween.to(transitionColor, SpriteAcessor.ALPHA, 1.0f).target(0).delay(1).start(tweenManager);
                    battleStatus = 0;
                }
            }
        }
    }

private void update(float delta) {
        tweenManager.update(delta);

        final Random random = new Random();
        int targetNumber;
        Character personagem;
        Character target;

        if (teamB.isEmpty()) {
            nextLevel();
        }

        for (int i = 0; i < allCharacters.size(); i++) {
            int moveDirection;
            personagem = allCharacters.get(i);
            Team targets;
            Team friends;
            if (teamA.contains(personagem)) {
                targets = teamB;
                friends = teamA;
                moveDirection = 1;
            } else {
                targets = teamA;
                friends = teamB;
                moveDirection = -1;
            }

            switch (personagem.getStatus()) {
                case DEAD:
                    //friends.remove(personagem);
                    break;

                case WAITING:
                    if (personagem.canAttack()) {
                        if (!targets.isEmpty()) {
                            personagem.setStatus(GOING);
                            Tween.to(personagem, CharacterAccessor.POS_X, 1.5f).target(personagem.getX() + 100 * moveDirection).delay(0).start(tweenManager);
                        }
                    }
                    break;

                case GOING:
                    if (targets.isEmpty()) {
                        tweenManager.killTarget(personagem, 1);
                        personagem.setStatus(RETURNING);
                        Tween.to(personagem, CharacterAccessor.POS_X, 1).target(personagem.orgX).delay(0).start(tweenManager);
                    } else if (personagem.getX() == personagem.orgX + 100 * moveDirection) {
                        personagem.setStatus(ATTACKING);
                        targetNumber = random.nextInt(targets.size());
                        target = targets.get(targetNumber);
                        personagem.attack(target);
                        if (target.isDead()) {
                            targets.remove(target);
                        }
                    }
                    break;

                case ATTACKING:
                    if (personagem.getAnimations().isAnimationFinished()) {
                        personagem.setStatus(RETURNING);
                        Tween.to(personagem, CharacterAccessor.POS_X, 1).target(personagem.orgX).delay(0).ease(Linear.INOUT).start(tweenManager);
                    }
                    break;

                case RETURNING:
                    if (personagem.getX() == personagem.orgX) {
                        personagem.getAnimations().flipFrames(true, false);
                        personagem.setStatus(WAITING);
                    }
                    break;

                case DYING:
                    //friends.remove(personagem);
                    if (personagem.getAnimations().isAnimationFinished()) {
                        if(teamB.contains(personagem)){
                            allCharacters.remove(personagem);
                        }
                        personagem.setStatus(DEAD);
                    }
                    break;
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
            if (!player.isDead() && battleStatus == 0) { // e o jogador não estiver morto.
                if (player.getPedaladasRestantes() - n <= 0) {
                    player.contarPedalada(player.getPedaladasRestantes());
                } else {
                    player.contarPedalada(n);
                }
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
        public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        background.draw(batch);

        for (Character personagem : allCharacters) {
            drawing.draw(personagem);
        }
        transitionColor.draw(batch);
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
