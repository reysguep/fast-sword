package com.mygdx.game.Screens;

import br.cefetmg.move2play.game.Move2PlayGame;
import br.cefetmg.move2play.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Assets;
import com.mygdx.game.Main;
import java.util.ArrayList;
import libgdxUtils.ColorsUtil;

/**
 *
 * @author reysguep
 */
public class SplashScreen implements Screen, Move2PlayGame {

    public SplashScreen(Main game) {
        this.game = game;
    }

    private final Main game;
    private SpriteBatch batch;
    private Animation<Texture> animationSplash;
    private Sound sound;
    private boolean isAnimationLoaded;
    private Assets manager;

    private Sprite loadingBar;
    private Sprite loadingSword;
    private Sprite barBackground;

    private ArrayList<String> frameNames;

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

        manager = new Assets();
        frameNames = manager.load("Animations/splash");

        sound = Gdx.audio.newSound(Gdx.files.internal("Audios/sounds/splash.mp3"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if (isAnimationLoaded) {
            if (!animationSplash.isAnimationFinished(delta)) {
                batch.draw(animationSplash.getKeyFrame(delta), 0, 0);
            } else {
                game.setScreen(new WaitingScreen(game));
            }
        } else {
            if (manager.manager.update()) {
                System.out.println("2");
                createSplashAnimation();
                isAnimationLoaded = true;
                sound.play(1.0f);
            } else {
                float width = manager.manager.getProgress() * 400;
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
        Texture[] frames = new Texture[frameNames.size()];
        System.out.println("3");
        for (int i = 0; i < frameNames.size(); i++) {
            frames[i] = manager.manager.get("splash_000000.jpeg");
        }

        animationSplash = new Animation<Texture>(0.42f, frames);
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
