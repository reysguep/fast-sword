package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Main;
import static com.mygdx.game.Screens.SplashScreen.FRAMES_SPLASH;
import com.mygdx.game.loaders.SoundEffectLoader;
import com.mygdx.game.loaders.SpineLoader;
import com.mygdx.game.loaders.VisualEffectLoader;
import libgdxUtils.AnimatedSprite;
import libgdxUtils.ColorsUtil;

/**
 *
 * @author reysguep
 */
public class LoadingScreen implements Screen {

    public final AssetManager assetManager;
    private final SpriteBatch batch;
    private Sprite loadingBar;
    private Sprite loadingSword;
    private Sprite barBackground;
    private final Main game;

    private boolean splashLoaded, soundsLoaded, spineLoaded, visualLoaded;

    public LoadingScreen(Main game) {
        this.batch = new SpriteBatch();
        this.assetManager = new AssetManager();
        this.game = game;

        this.spineLoaded = false;
        this.soundsLoaded = false;
        this.splashLoaded = false;
        this.visualLoaded = false;
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

        for (int i = 0; i < SplashScreen.FRAMES_SPLASH; i++) {
            assetManager.load("Animations/splash/" + i + ".jpeg", Texture.class);
        }

    }

    @Override
    public void render(float f) {
        if (assetManager.update()) {
            if (!splashLoaded) {
                splashLoaded = true;
                System.out.println("Splash animation loaded!");
                
            } else if (!spineLoaded) {
                SpineLoader.load();
                spineLoaded = true;
                System.out.println("Spine animations loaded!");
                
            } else if(!soundsLoaded) {
                SoundEffectLoader.load();
                soundsLoaded = true;
                System.out.println("Sound effects loaded!");
                
            } else if(!visualLoaded) {
                VisualEffectLoader.load();
                visualLoaded = true;
                System.out.println("Visual effects loaded!");
                
            } else {
                game.setScreen(new SplashScreen(game, createSplashAnimation()));
            }
        }

        Gdx.gl.glClearColor(0f, 0f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        float width = getBarWidth();
        loadingBar.setSize(width, loadingBar.getHeight());
        loadingBar.setPosition(640 - width / 2, 320);
        loadingSword.draw(batch);
        barBackground.draw(batch);
        loadingBar.draw(batch);

        batch.end();
    }
    
    private float getBarWidth() {
        float width, i;
        int loaders;
        
        loaders = 4;
        i = 0;
        
        i += assetManager.getProgress();
        
        if(spineLoaded)
            i++;
        if(soundsLoaded)
            i++;
        if(visualLoaded)
            i++;
        
        width = (i / loaders) * 400;
        
        return width;
    }

    private AnimatedSprite createSplashAnimation() {
        AnimatedSprite splash;
        Animation<TextureRegion> preAnim;
        
        TextureRegion[] frames = new TextureRegion[FRAMES_SPLASH];
        for (int i = 0; i < FRAMES_SPLASH; i++) {
            String frameName = "Animations/splash/" + i + ".jpeg";
            frames[i] = new TextureRegion(assetManager.get(
                    frameName, Texture.class));
        }
        preAnim = new Animation<>(0.04f, frames);
        splash = new AnimatedSprite(preAnim);
        splash.setSize(1280, 720);
        
        return splash;
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
