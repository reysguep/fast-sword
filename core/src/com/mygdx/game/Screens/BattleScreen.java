package com.mygdx.game.Screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Expo;
import br.cefetmg.move2play.game.Move2PlayGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.esotericsoftware.spine.SkeletonMeshRenderer;
import com.mygdx.game.managers.BackgroundManager;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Main;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Team;
import com.mygdx.game.VisualEffect;
import libgdxUtils.AnimationCode;
import libgdxUtils.DrawingCharacter;
import com.mygdx.game.accessors.CharacterAccessor;
import libgdxUtils.ColorsUtil;
import libgdxUtils.KeyboardUtil;
import com.mygdx.game.accessors.MusicAccessor;
import com.mygdx.game.accessors.SpriteAcessor;
import com.mygdx.game.managers.*;
import libgdxUtils.StateCode;
import static libgdxUtils.StateCode.*;

/**
 *
 * @author reysguep
 */
public class BattleScreen implements Screen, Move2PlayGame {

    public Team teamA, teamB;
    public Array<Character> allCharacters;
    private DrawingCharacter drawing;
    private final Main game;
    private PolygonSpriteBatch batch;
    private SkeletonMeshRenderer renderer;
    private Music music;

    //MANAGERS
    public MusicManager musicManager;
    public CharacterManager characterManager;
    public SoundEffectManager soundEffectManager;
    public VisualEffectManager visualEffectManager;
    public BackgroundManager backgroundManager;

    public TweenManager tweenManager;
    private Sprite transitionColor;

    private int battleState; // 0 = battle; 1 = transition; 2 = ...

    private int phase;

    public Array<VisualEffect> hits;

    public BattleScreen(Main game, Array<br.cefetmg.move2play.model.Player> waitingPlayers) {
        this.game = game;
        teamA = new Team('a');
        teamB = new Team('b');
        for (br.cefetmg.move2play.model.Player playerModel : waitingPlayers) {
            Player player;
        }
    }

    @Override
    public void show() {
        battleState = 0;
        phase = 1;

        music = musicManager.nextSong();
        music.play();

        tweenManager = new TweenManager();
        Tween.setCombinedAttributesLimit(5);
        Tween.registerAccessor(Character.class, new CharacterAccessor());
        Tween.registerAccessor(music.getClass(), new MusicAccessor());
        Tween.registerAccessor(Sprite.class, new SpriteAcessor());

//        System.out.println(Tween.getRegisteredAccessor(music.getClass()));
        teamB = new Team('b');
        allCharacters = new Array<>();

        allCharacters.addAll(teamA);
        batch = new PolygonSpriteBatch();
        renderer = new SkeletonMeshRenderer();
        transitionColor = new Sprite(ColorsUtil.BLACK);
        transitionColor.setSize(1280, 720);
        transitionColor.setAlpha(0);

        backgroundManager = new BackgroundManager();
        soundEffectManager = new SoundEffectManager();
        visualEffectManager = new VisualEffectManager();
        musicManager = new MusicManager();
        characterManager = new CharacterManager(this, 2);

        backgroundManager.nextBackground();
        drawing = new DrawingCharacter(batch, renderer);

        game.eventHandler = this;
        Gdx.input.setInputProcessor(new KeyboardUtil(this));
    }

    private void nextLevel() {
        float transitionTime = 2.7f;

        if (battleState == 0) {
            boolean allIdle = true;
            for (Character chr : teamA) {
                if (chr.getState() != WAITING) {
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

            battleState = 1;
        } else {
            if (!tweenManager.containsTarget(transitionColor)) {
                for (Character chr : teamA) {
                    tweenManager.killTarget(chr);
                    chr.setPosition(chr.getOrgX(), chr.getOrgY());
                    chr.setAnimation(AnimationCode.IDLE, true);
                    //chr.health = chr.getMaxHealth();
                    if (chr instanceof Player) {
                        Player player = (Player) chr;
                        player.takenSteps = 0;
                    }
                }
                battleState = 0;
                backgroundManager.nextBackground();
                characterManager.newRound();
                Tween.to(transitionColor, SpriteAcessor.ALPHA, 1.0f).target(0).delay(1).start(tweenManager);

                music = musicManager.nextSong();
                music.setVolume(0f);
                Tween.to(music, MusicAccessor.VOLUME, 2.7f).target(1.0f).ease(Expo.IN).start(tweenManager);
                music.play();
            }
        }
    }

    private void update(float delta) {
        tweenManager.update(delta);

        Array<Character> removeChrs = new Array<>();

        if (teamB.size == 0) {
            nextLevel();
        }

        for (Character character : allCharacters) {
            int moveDirection;
            Team allies;

            if (character.getTeam() == 'a') {
                allies = teamA;
                moveDirection = 1;
            } else {
                allies = teamB;
                moveDirection = -1;
            }

            switch (character.getState()) {
                case DEAD:
                    if (character instanceof Player) {
                        Player player = (Player) character;
                        long timeRemaining;

                        timeRemaining = 6000 - (TimeUtils.millis() - player.timeDied);
                        player.setAlpha(0.5f + 0.5f * (((6000 - (float) timeRemaining)) / 6000));

                        if (timeRemaining <= 0) {
                            player.setState(REVIVING);
                            player.setAlpha(1.0f);
                        }
                    }
                    break;

                case WAITING:
                    if (character.canAttack()) {
                        character.setState(GOING);
                    }
                    break;

                case GOING:
                    if (!character.canAttack()) {
                        character.setState(StateCode.RETURNING);
                    } else if (character.getX() == character.getOrgX() + 100 * moveDirection) {
                        character.setState(StateCode.ACTING);
                    }

                    break;

                case ACTING:
                    if (!character.canAttack()) {
                        character.setState(StateCode.RETURNING);
                    } else if (character.isAnimationFinished()) {
                        character.action();
                        character.setState(RETURNING);
                    }
                    break;

                case RETURNING:
                    if (character.getX() == character.getOrgX()) {
                        character.setState(WAITING);
                    }
                    break;

                case DYING:
                    if (character.isAnimationFinished()) {
                        if (character instanceof Enemy) {
                            allies.removeValue(character, false);
                            removeChrs.add(character);
                        } else if (character instanceof Player) {
                            Player plyr = (Player) character;
                            plyr.setAlpha(0.5f);
                        }

                        character.setState(DEAD);
                    }
                    break;

                case REVIVING:
                    if (character.isAnimationFinished()) {
                        character.setState(RETURNING);
                    }
                    break;
            }
        }

        allCharacters.removeAll(removeChrs, false);
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
                if (battleState == 0) {
                    if (player.getPedaladasRestantes() - n <= 0) {
                        player.addSteps(player.getPedaladasRestantes());
                    } else {
                        player.addSteps(n);
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
                if (player.getPlayerModel().getUUID().equals(str) || player.getName().equals(str)) {
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

        for (Character character : allCharacters) {
            drawing.draw(character);
        }

        for (VisualEffect hit : hits) {
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
        Array<Player> players = new Array<>();
        for (Character chr : allCharacters) {
            if (chr instanceof Player) {
                players.add((Player) chr);
            }
        }

        game.setScreen(new EndMatchScreen(game, players));
    }

    @Override
    public void addPlayer(br.cefetmg.move2play.model.Player playerModel) {
        Player player = characterManager.newPlayer(playerModel);

        allCharacters.add(player);
        teamA.add(player);
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
