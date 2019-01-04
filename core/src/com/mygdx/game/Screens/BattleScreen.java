package com.mygdx.game.Screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Expo;
import aurelienribon.tweenengine.equations.Linear;
import br.cefetmg.move2play.game.Move2PlayGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.esotericsoftware.spine.SkeletonMeshRenderer;
import com.mygdx.game.managers.BackgroundManager;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Main;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Team;
import com.mygdx.game.EnemyGenerator;
import com.mygdx.game.HitAnimation;
import com.mygdx.game.PlayerGenerator;
import java.util.ArrayList;
import java.util.Random;
import libgdxUtils.AnimationCode;
import libgdxUtils.DrawingCharacter;
import com.mygdx.game.accessors.CharacterAccessor;
import libgdxUtils.ColorsUtil;
import libgdxUtils.Commands;
import libgdxUtils.KeyboardUtil;
import com.mygdx.game.accessors.MusicAccessor;
import libgdxUtils.MusicUtils;
import com.mygdx.game.accessors.SpriteAcessor;
import static libgdxUtils.StatusCode.*;

/**
 *
 * @author reysguep
 */
public class BattleScreen implements Screen, Move2PlayGame {

    public BattleScreen(Main game, Array<br.cefetmg.move2play.model.Player> waitingPlayers) {
        this.game = game;
        teamA = new Team('a');
        plyGenerator = new PlayerGenerator();
        Array<Player> players = plyGenerator.sortClasses(waitingPlayers);
        for(Player player : players) {
            teamA.addMember(player);
        }
    }

    public Team teamA, teamB;
    public Array<Character> allCharacters;
    private BackgroundManager backgroundManager;
    private DrawingCharacter drawing;
    private final Main game;
    private PolygonSpriteBatch batch;
    private SkeletonMeshRenderer renderer;
    private EnemyGenerator generator;
    private PlayerGenerator plyGenerator;
    private MusicUtils musicUtils;
    private Music music;
    public TweenManager tweenManager;
    private Sprite transitionColor;
    private Commands commands;

    private int battleStatus; // 0 = battle; 1 = transition; 2 = ...

    private int phase;

    public Array<HitAnimation> hits;

    @Override
    public void show() {
        battleStatus = 0;
        phase = 1;
        
        musicUtils = new MusicUtils("Audios/musics/battle screen");
        music = musicUtils.nextSong();
        music.play();

        tweenManager = new TweenManager();
        Tween.setCombinedAttributesLimit(5);
        Tween.registerAccessor(Character.class, new CharacterAccessor());
        Tween.registerAccessor(music.getClass(), new MusicAccessor());
        Tween.registerAccessor(Sprite.class, new SpriteAcessor());
        
//        System.out.println(Tween.getRegisteredAccessor(music.getClass()));

        teamB = new Team('b');
        allCharacters = new Array<Character>();

        allCharacters.addAll(teamA);
        batch = new PolygonSpriteBatch();
        renderer = new SkeletonMeshRenderer();
        transitionColor = new Sprite(ColorsUtil.BLACK);
        transitionColor.setSize(1280, 720);
        transitionColor.setAlpha(0);

        backgroundManager = new BackgroundManager();
        backgroundManager.nextBackground();
        drawing = new DrawingCharacter(batch, renderer);

        generator = new EnemyGenerator(this, 1);

        game.eventHandler = this;
        Gdx.input.setInputProcessor(new KeyboardUtil(this));

        commands = new Commands(this);
        generator.newMatch();

        hits = new Array<HitAnimation>();

    }

    private void nextLevel() {
        float transitionTime = 2.7f;

        if (battleStatus == 0) {
            boolean allIdle = true;
            for (Character chr : teamA) {
                if (chr.getStatus() != WAITING) {
                    allIdle = false;
                    return;
                }
            }

            for (Character chr : teamA) {
                if (!chr.isDead()) {
                    Tween.to(chr, CharacterAccessor.POS_X, 3.0f).target(chr.getX() + 750).start(tweenManager);
                    chr.setAnimation(AnimationCode.RUNNING, true);
                    chr.flip(false, false);
                    if (chr instanceof Player) {
                        Player player = (Player) chr;
                    }
                }
            }
            Tween.to(transitionColor, SpriteAcessor.ALPHA, 2.7f).target(1).start(tweenManager);
            Tween.to(music, MusicAccessor.VOLUME, 2.7f).target(0.0f).start(tweenManager);

            battleStatus = 1;
        } else {
            if (!tweenManager.containsTarget(transitionColor)) {
                for (Character chr : teamA) {
                    tweenManager.killTarget(chr);
                    chr.setPosition(chr.orgX, chr.orgY);
                    chr.setAnimation(AnimationCode.IDLE, true);
                    //chr.health = chr.getMaxHealth();
                    if (chr instanceof Player) {
                        Player player = (Player) chr;
                        player.pedaladasDadas = 0;
                    }
                }
                battleStatus = 0;
                backgroundManager.nextBackground();
                generator.newMatch();
                Tween.to(transitionColor, SpriteAcessor.ALPHA, 1.0f).target(0).delay(1).start(tweenManager);

                music = musicUtils.nextSong();
                music.setVolume(0f);
                Tween.to(music, MusicAccessor.VOLUME, 2.7f).target(1.0f).ease(Expo.IN).start(tweenManager);
                music.play();
            }
        }
    }

    private void update(float delta) {
        tweenManager.update(delta);

        final Random random = new Random();
        int targetNumber;
        Character target;

        Array<Character> removeChrs = new Array<Character>();

        if (teamB.size == 0) {
            nextLevel();
        }

        for (Character personagem : allCharacters) {
            int moveDirection;
            Team targets;
            Team friends;
            if (personagem.team == 'a') {
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
                    if (personagem instanceof Player) {
                        Player player = (Player) personagem;
                        long timeRemaining;

                        timeRemaining = 6000 - (TimeUtils.millis() - player.timeDied);
                        player.setAlpha(0.5f + 0.5f * (((6000 - (float) timeRemaining)) / 6000));

                        if (timeRemaining <= 0) {
                            player.setStatus(REVIVING);
                            friends.add(personagem);
                            player.setAlpha(1.0f);
                        }
                    }
                    break;

                case WAITING:
                    if (personagem.canAttack()) {
                        if (!(targets.size == 0)) {
                            personagem.setStatus(GOING);
                            personagem.move(GOING, tweenManager, moveDirection);
                        }
                    }
                    break;

                case GOING:
                    if (targets.size == 0) {
                        tweenManager.killTarget(personagem, 1);
                        personagem.setStatus(RETURNING);
                        personagem.move(RETURNING, tweenManager, moveDirection);
                    } else if (personagem.getX() == personagem.orgX + 100 * moveDirection) {
                        int score;

                        personagem.act(commands);
                    }

                    break;

                case ACTING:
                    if (personagem.isAnimationFinished()) {
                        personagem.setStatus(RETURNING);
                        personagem.move(RETURNING, tweenManager, moveDirection);
                    }
                    break;

                case RETURNING:
                    if (personagem.getX() == personagem.orgX) {
                        personagem.setStatus(WAITING);

                    }
                    break;

                case DYING:
                    if (personagem.isAnimationFinished()) {
                        if (personagem instanceof Enemy) {
                            removeChrs.add(personagem);
                        } else {

                            if (personagem instanceof Player) {
                                Player plyr = (Player) personagem;
                                plyr.setAlpha(0.5f);
                            }
                        }

                        personagem.setStatus(DEAD);
                    }
                    break;

                case REVIVING:
                    if (personagem.isAnimationFinished()) {
                        personagem.setPlayMode(Animation.PlayMode.NORMAL);
                        personagem.setStatus(RETURNING);
                        personagem.move(RETURNING, tweenManager, moveDirection);
                    }
                    break;
            }
        }

        allCharacters.removeAll(removeChrs, true);
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
                if (battleStatus == 0) {
                    if (player.getPedaladasRestantes() - n <= 0) {
                        player.contarPedalada(player.getPedaladasRestantes());
                    } else {
                        player.contarPedalada(n);
                    }
                }
            } else {
                player.timeDied -= 500;
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

        backgroundManager.draw(batch);

        for (Character personagem : allCharacters) {
            drawing.draw(personagem);
        }

        for (HitAnimation hit : hits) {
            hit.draw(batch);
        }

        transitionColor.draw(batch);
        drawing.drawScores(allCharacters);
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
        Array<Player> players = new Array<Player>();
        for (Character chr : allCharacters) {
            if (chr instanceof Player) {
                players.add((Player) chr);
            }
        }

        game.setScreen(new EndMatchScreen(game, players));
    }

    @Override
    public void addPlayer(br.cefetmg.move2play.model.Player player) {
        Player plyr = plyGenerator.newRandomPlayer(player);

        allCharacters.add(plyr);
        teamA.add(plyr);
    }

    @Override
    public void removePlayer(br.cefetmg.move2play.model.Player player) {
        Player plyr = searchPlayer(player.getUUID());
        if (plyr != null) {
            allCharacters.removeValue(plyr, true);
            if (teamA.contains(plyr, true)) {
                teamA.removeValue(plyr, true);
            } else {
                teamB.removeValue(plyr, true);
            }
        }
    }

    public void removePlayer(String name) {
        Player plyr = searchPlayer(name);
        if (plyr != null) {
            allCharacters.removeValue(plyr, true);
            if (teamA.contains(plyr, true)) {
                teamA.removeValue(plyr, true);
            } else {
                teamB.removeValue(plyr, true);
            }
        }
    }

}
