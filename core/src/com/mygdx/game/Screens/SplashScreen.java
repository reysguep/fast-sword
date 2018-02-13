package com.mygdx.game.Screens;

import com.mygdx.game.Screens.WaitingScreen;
import br.cefetmg.move2play.game.Move2PlayGame;
import br.cefetmg.move2play.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.google.gson.Gson;
import com.mygdx.game.MyGdxGame;
import libgdxUtils.AnimatedSprite;
import libgdxUtils.EnemyPreset;
import libgdxUtils.FileUtil;
import libgdxUtils.VideoUtil;

/**
 *
 * @author reysguep
 */
public class SplashScreen implements Screen, Move2PlayGame {

    public SplashScreen(MyGdxGame game) {
        this.game = game;
        animationSplash = new AnimatedSprite(VideoUtil.imageSequenceToAnimation("Animations/splash", 24));
        
        EnemyPreset ep = new EnemyPreset();
        Gson gson = new Gson();
        
        ep.name = "Skeleton1";
        ep.fileName = "skeleton";
        ep.maxHealth = 5000;
        ep.strength = 25;
        ep.timeToAttack = 3.5f;
        ep.width = 100;
        ep.height = 200;
        
        String tst = gson.toJson(ep);
        
        FileUtil.writeString(tst);
    }

    private final MyGdxGame game;
    private SpriteBatch batch;
    private final AnimatedSprite animationSplash;
    private Sound sound;
    private long time;

    @Override
    public void show() {
        
        game.eventHandler = (Move2PlayGame) this;
        
        
        
        
        sound = Gdx.audio.newSound(Gdx.files.internal("Audios/sounds/splash.mp3"));
        batch = new SpriteBatch();
        time = TimeUtils.millis();
        sound.play(1.0f);
    }

    @Override
    public void render(float delta) {
        if (! animationSplash.isAnimationFinished()) {

            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            
            animationSplash.draw(batch);
            
            batch.end();
        } else {
            game.setScreen(new WaitingScreen(game));
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