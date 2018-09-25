package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import libgdxUtils.ColorsUtil;

/**
 *
 * @author reysguep
 */
public class LoadingScreen implements Screen {

    public final AssetManager assetManager;
    private final Batch batch;
    private Sprite loadingBar;
    private Sprite loadingSword;
    private Sprite barBackground;
    
    public boolean completed;

    public LoadingScreen(AssetManager assetManager, Batch batch) {
        this.assetManager = assetManager;
        completed = false;
        this.batch = batch;
    }

    @Override
    public void show() {
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
    }

    @Override
    public void render(float f) {
        if (assetManager.update()) {
            completed = true;
        } else {
            float width = assetManager.getProgress() * 400;
            loadingBar.setSize(width, loadingBar.getHeight());
            loadingBar.setPosition(640 - width / 2, 320);
            loadingSword.draw(batch);
            barBackground.draw(batch);
            loadingBar.draw(batch);
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
    }
}
