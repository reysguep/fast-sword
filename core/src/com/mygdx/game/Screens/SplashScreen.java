package com.mygdx.game.Screens;

import br.cefetmg.move2play.game.Move2PlayGame;
import br.cefetmg.move2play.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Main;
import libgdxUtils.AnimatedSprite;

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

    private LoadingScreen loading;
    private boolean animationIsCreated;

    @Override
    public void show() {
        game.eventHandler = (Move2PlayGame) this;
        batch = new SpriteBatch();
        loading = new LoadingScreen(game.assetManager, batch);
        loading.show();
        animationIsCreated = false;

        for (int i = 0; i < FRAMES_SPLASH; i++) {
            loading.assetManager.load("Animations/splash/" + i + ".jpeg", Texture.class);
        }

        sound = Gdx.audio.newSound(Gdx.files.internal("Audios/sounds/splash.mp3"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if (loading.completed) {
            if (!animationIsCreated) {
                createSplashAnimation();
                sound.play();
            } else {
                if (animationSplash.isAnimationFinished()) {
                    game.setScreen(new WaitingScreen(game));
                } else {
                    animationSplash.draw(batch);
                }
            }
        } else {
            loading.render(delta);
        }

        batch.end();

    }

    private void createSplashAnimation() {
        TextureRegion[] frames = new TextureRegion[FRAMES_SPLASH];
        for (int i = 0; i < FRAMES_SPLASH; i++) {
            String frameName = "Animations/splash/" + i + ".jpeg";
            frames[i] = new TextureRegion(loading.assetManager.get(
                    frameName, Texture.class));
        }
        Animation<TextureRegion> animation = new Animation<TextureRegion>(0.04f, frames);
        animationSplash = new AnimatedSprite(animation);
        animationSplash.setSize(1280, 720);
        animationIsCreated = true;
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
