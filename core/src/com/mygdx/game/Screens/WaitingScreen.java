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
import com.mygdx.game.Characters.Gunman;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Team;
import com.mygdx.game.Characters.Warrior;
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

    public WaitingScreen(MyGdxGame game) {
        this.game = game;

    }

    private final MyGdxGame game;
    private Music music;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont waitingMessage;
    private CircularLinkedList<String> cll;
    private long startTime;
    private String dot = "";
    private GlyphLayout layout;
    private PlayersWaiting players;

    @Override
    public void show() {
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

    }

    public void addPlayer(String nome) {
        Random random = new Random();
        int sortedN = random.nextInt(2);
        Player plyr = new Warrior(nome);

        switch (sortedN) {
            case 0:
                plyr = new Warrior(nome);
                break;
            case 1:
                plyr = new Gunman(nome);
                break;
            case 2:
                //plyr = new Archer(player);
                break;
        }

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
