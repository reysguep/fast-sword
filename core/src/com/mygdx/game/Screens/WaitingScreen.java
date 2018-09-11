package com.mygdx.game.Screens;

import br.cefetmg.move2play.game.Move2PlayGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Main;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.PlayerGenerator;
import com.mygdx.game.Team;
import java.util.Random;
import libgdxUtils.AnimatedSprite;

/**
 *
 * @author reysguep
 */
import libgdxUtils.CircularLinkedList;
import libgdxUtils.KeyboardUtil;
import libgdxUtils.PlayersWaiting;
import libgdxUtils.VideoUtil;
import libgdxUtils.WaitingNode;

public class WaitingScreen implements Screen, Move2PlayGame {

    public WaitingScreen(Main game) {
        this.game = game;

    }

    private final Main game;
    private Music music;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont waitingMessage;
    private BitmapFont playerMessage;
    private CircularLinkedList<String> cll;
    private long startTime;
    private String dot = "";
    private GlyphLayout layout;
    private Animation<TextureRegion> star;
    
    private Array<br.cefetmg.move2play.model.Player> waitingPlayers;
    
    private PlayersWaiting players;
    private PlayerGenerator plyGenerator;

    @Override
    public void show() {
        plyGenerator = new PlayerGenerator();
        waitingPlayers = new Array<br.cefetmg.move2play.model.Player>();
        
        layout = new GlyphLayout();
        music = Gdx.audio.newMusic(Gdx.files.internal("Audios/musics/evocation.mp3"));
        background = new Texture(Gdx.files.internal("Animations/waitingBG.jpg"));
        batch = new SpriteBatch();

        FreeTypeFontGenerator generatorW = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/VeniceClassic.ttf"));
        FreeTypeFontParameter parameterW = new FreeTypeFontParameter();
        parameterW.size = 60;
        parameterW.borderWidth = 3;
        waitingMessage = generatorW.generateFont(parameterW);
        
        FreeTypeFontGenerator generatorP = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/VeniceClassic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        
        parameterP.size = 40;
        parameterP.borderWidth = 3;
        playerMessage = generatorP.generateFont(parameterP);

        Gdx.input.setInputProcessor(new KeyboardUtil(this));

        players = new PlayersWaiting(layout);

        cll = new CircularLinkedList<String>();
        cll.add("");
        cll.add(".");
        cll.add("..");
        cll.add("...");

        music.setLooping(true);

        music.play();
        startTime = TimeUtils.millis();
        
        star = VideoUtil.imageSequenceToAnimation("Animations/star", 0.025f);
        star.setPlayMode(Animation.PlayMode.LOOP);
        
        game.eventHandler = (Move2PlayGame) this;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(background, 0, 0, 1280, 720);
        drawWaitingMessage();
        drawWP(delta);

        batch.end();
    }

    private void drawWaitingMessage() {
        int x, y;
        if (TimeUtils.timeSinceMillis(startTime) >= 0.5 * 1000) {
            dot = cll.get();
            startTime = TimeUtils.millis();
        }
        CharSequence text = "Waiting players";
        layout.setText(waitingMessage, text);
        y = 650;
        x = (int) ((1280 / 2) - (layout.width / 2));
        waitingMessage.draw(batch, text + dot, x, y);
    }
    
    private void drawWP(float delta) {
        int y = 500;
        for(br.cefetmg.move2play.model.Player player : waitingPlayers) {
            batch.draw(star.getKeyFrame(delta), 460, y - 25, 30f, 30f);
            layout.setText(playerMessage, player.getName());
            playerMessage.draw(batch, player.getName(), 500, y);
            y -= 50;
        }
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
    public void dispose() {
        background.dispose();
        waitingMessage.dispose();
        playerMessage.dispose();
        music.dispose();
        batch.dispose();
        players.dispose();
    }

    @Override
    public void initGame() {
        
    }

    @Override
    public void closeGame() {

    }

    @Override
    public void startMatch() {
        if (waitingPlayers.size >= 1) {
            Team teamA = new Team('a');
            for (br.cefetmg.move2play.model.Player player : waitingPlayers) {
                teamA.addMember(plyGenerator.newRandomPlayer(player));
            }

            game.setScreen(new BattleScreen(game, teamA));
        }
    }

    @Override
    public void finishMatch() {

    }

    @Override
    public void move(String string, int i) {

    }

    @Override
    public void addPlayer(br.cefetmg.move2play.model.Player player) {
        waitingPlayers.add(player);
    }

    @Override
    public void removePlayer(br.cefetmg.move2play.model.Player player) {
        waitingPlayers.removeValue(player, false);
    }

}
