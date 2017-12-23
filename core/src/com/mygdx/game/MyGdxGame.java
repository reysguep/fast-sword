package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {

    SpriteBatch batch;
    Background background;
    Warrior warrior1;
    Gunman gunwoman;
    Skeleton skeleton;
    float stateTime;
    FreeTypeFontGenerator freeType;
    TelaDeBatalha tdb;

    @Override
    public void create() {
        batch = new SpriteBatch();
        warrior1 = new Warrior("Lancelot");
        gunwoman = new Gunman("Ellie");
        skeleton = new Skeleton();
        ArrayList<Player> jogadores = new ArrayList<Player>();
        ArrayList<Enemy> inimigos = new ArrayList<Enemy>();
        
        
        warrior1.setPosition(50, 70);
        warrior1.setSize(400, 400);
        
        gunwoman.setPosition(200, 70);
        gunwoman.setSize(400, 400);
        
        skeleton.setPosition(800, 100);
        skeleton.setSize(400, 600);
        skeleton.flipAllAnimations(true, false); 
        
        jogadores.add(warrior1);
        jogadores.add(gunwoman);
        inimigos.add(skeleton);
        
        tdb = new TelaDeBatalha(jogadores, inimigos);
    }

    @Override
    public void render() {
        tdb.checarCondicoes();
        startBatch(); // Set and starts the batch //

        tdb.draw(batch);

        batch.end(); // Ends the batch //
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void update() {
        //tempoDaAnimacao += Gdx.graphics.getDeltaTime();

    }

    private void startBatch() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        batch.begin();
    }

    

}
