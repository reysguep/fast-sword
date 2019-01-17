package com.mygdx.game.Screens;

import br.cefetmg.move2play.game.Move2PlayGame;
import br.cefetmg.move2play.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Main;
import libgdxUtils.AnimatedSprite;

/**
 *
 * @author reysguep
 */
public class SplashScreen implements Screen, Move2PlayGame {

    public SplashScreen(Main game, AnimatedSprite animationSplash) {
        this.game = game;
        this.animationSplash = animationSplash;
    }
    public static final int FRAMES_SPLASH = 51;
    private final Main game;
    private SpriteBatch batch;
    private AnimatedSprite animationSplash;
    private Sound sound;

    @Override
    public void show() {
        game.eventHandler = (Move2PlayGame) this;
        batch = new SpriteBatch();

        sound = Gdx.audio.newSound(Gdx.files.internal("Audios/sounds/splash.mp3"));
        sound.play();
    }

    @Override
    public void render(float delta) {
        if(animationSplash.isAnimationFinished()) {
            game.setScreen(new WaitingScreen(game));
        }
        
        Gdx.gl.glClearColor(0f, 0f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        animationSplash.draw(batch);

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
