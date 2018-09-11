package com.mygdx.game.Screens;

import br.cefetmg.move2play.game.Move2PlayGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Main;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.PlayerGenerator;
import com.mygdx.game.Team;
import java.util.Random;

/**
 *
 * @author reysguep
 */
import libgdxUtils.CircularLinkedList;
import libgdxUtils.KeyboardUtil;
import libgdxUtils.PlayersWaiting;
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
    private CircularLinkedList<String> cll;
    private long startTime;
    private String dot = "";
    private GlyphLayout layout;
    
    private PlayersWaiting players;
    private PlayerGenerator plyGenerator;

    @Override
    public void show() {
        plyGenerator = new PlayerGenerator();
        
        layout = new GlyphLayout();
        music = Gdx.audio.newMusic(Gdx.files.internal("Audios/musics/evocation.mp3"));
        background = new Texture(Gdx.files.internal("Animations/waitingBG.jpg"));
        batch = new SpriteBatch();

        FreeTypeFontGenerator generatorW = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/VeniceClassic.ttf"));
        FreeTypeFontParameter parameterW = new FreeTypeFontParameter();
        parameterW.size = 60;
        parameterW.borderWidth = 5;
        waitingMessage = generatorW.generateFont(parameterW);

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
        game.eventHandler = (Move2PlayGame) this;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(background, 0, 0, 1280, 720);
        drawWaitingMessage();
        players.draw(batch);

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
        if (players.size() >= 1) {
            Team teamA = new Team('a');
            for (WaitingNode wn : players) {
                teamA.addMember(wn.getCharacter());
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
        Player plyr = plyGenerator.newPlayer(player);
        players.addPlayer(plyr);
    }

    public void addPlayer(String name) {
        Player plyr = plyGenerator.newPlayer(name);
        players.addPlayer(plyr);
    }

    @Override
    public void removePlayer(br.cefetmg.move2play.model.Player player) {
        players.removePlayer(player.getName());
    }

    public void removePlayer(String name) {
        players.removePlayer(name);
    }

}
