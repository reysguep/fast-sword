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
import libgdxUtils.KeyboardUtil;

/**
 *
 * @author reysguep
 */
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
    private long startTime;
    private String dot = "";
    private GlyphLayout layout;
    private Animation<TextureRegion> star;

    private Array<br.cefetmg.move2play.model.Player> waitingPlayers;

    @Override
    public void show() {
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

        music.setLooping(true);
        music.play();
        startTime = TimeUtils.millis();

        createStar();

        game.eventHandler = (Move2PlayGame) this;
    }

    private void createStar() {
        for (int i = 1; i <= 26; i++) {
            game.assetManager.load("Animations/star/" + i + ".png", Texture.class);
        }
        game.assetManager.finishLoading();
        TextureRegion[] frames = new TextureRegion[26];
        for (int i = 1; i <= 26; i++) {
            String frameName = "Animations/star/" + i + ".png";
            frames[i - 1] = new TextureRegion(game.assetManager.get(
                    frameName, Texture.class));
        }
        Animation<TextureRegion> animation = new Animation<TextureRegion>(0.04f, frames);

        star = new Animation<TextureRegion>(0.01f, frames);
        star.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void updateDots() {
        if ("".equals(dot)) {
            dot = ".";
        } else if (".".equals(dot)) {
            dot = "..";
        } else if("..".equals(dot)) {
            dot = "...";
        } else if("...".equals(dot)) {
            dot = "";
        }
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
            updateDots();
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
        for (br.cefetmg.move2play.model.Player player : waitingPlayers) {
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
            game.setScreen(new BattleScreen(game, waitingPlayers));
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
