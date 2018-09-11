package com.mygdx.game.Screens;

import br.cefetmg.move2play.game.Move2PlayGame;
import br.cefetmg.move2play.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Assets;
import com.mygdx.game.Main;
import java.util.ArrayList;
import libgdxUtils.AnimatedSprite;
import libgdxUtils.ColorsUtil;

/**
 *
 * @author reysguep
 */
public class SplashScreen implements Screen, Move2PlayGame {

    public SplashScreen(Main game) {
        this.game = game;
    }
    private static final int FRAMES_SPLASH = 51;
    private final Main game;
    private SpriteBatch batch;
    private AnimatedSprite animationSplash;
    private Sound sound;
    private boolean isAnimationLoaded;
    private AssetManager assets;

    private Sprite loadingBar;
    private Sprite loadingSword;
    private Sprite barBackground;

    @Override
    public void show() {
        game.eventHandler = (Move2PlayGame) this;
        isAnimationLoaded = false;
        batch = new SpriteBatch();

        loadingBar = new Sprite(ColorsUtil.generateColorTexture(Color.WHITE));
        loadingSword = new Sprite(new Texture(Gdx.files.internal("Animations/icons/loadingSword.png")));
        barBackground = new Sprite(ColorsUtil.generateColorTexture(Color.GRAY));

        loadingSword.setSize(100, 100);
        loadingSword.setOriginCenter();
        loadingSword.setPosition(640 - 50, 380);

        loadingBar.setSize(0, 20);
        loadingBar.setOriginCenter();
        loadingBar.setPosition(640, 320);

        barBackground.setSize(400, 20);
        barBackground.setPosition(640 - 200, 320);

        assets = new AssetManager();
        for(int i = 0; i < FRAMES_SPLASH; i++) {
            assets.load("Animations/splash/" + i + ".jpeg", Texture.class);
        }

        sound = Gdx.audio.newSound(Gdx.files.internal("Audios/sounds/splash.mp3"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if (isAnimationLoaded) {
            if (!animationSplash.isAnimationFinished()) {
                animationSplash.draw(batch);
            } else {
                game.setScreen(new WaitingScreen(game));
            }
        } else {
            if (assets.update()) {
                createSplashAnimation();
                isAnimationLoaded = true;
                sound.play(1.0f);
            } else {
                float width = assets.getProgress() * 400;
                loadingBar.setSize(width, loadingBar.getHeight());
                loadingBar.setPosition(640 - width / 2, 320);
                loadingSword.draw(batch);
                barBackground.draw(batch);
                loadingBar.draw(batch);
            }
        }

        batch.end();

    }

    private void createSplashAnimation() {
        TextureRegion[] frames = new TextureRegion[FRAMES_SPLASH];
        for (int i = 0; i < FRAMES_SPLASH; i++) {
            frames[i] = new TextureRegion(assets.get(
                    "Animations/splash/" + i + ".jpeg", Texture.class));
        }
        Animation<TextureRegion> animation = new Animation<TextureRegion>(0.05f, frames);
        animationSplash = new AnimatedSprite(animation);
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
        sound.dispose();
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

    }

    @Override
    public void finishMatch() {

    }

    @Override
    public void addPlayer(Player player) {

    }

    @Override
    public void removePlayer(Player player) {

    }

    @Override
    public void move(String string, int i) {

    }

}
