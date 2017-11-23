package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import java.awt.Point;

public class MyGdxGame extends ApplicationAdapter {

    SpriteBatch batch;
    Background background;
    Warrior warrior1;
    float stateTime;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Background();
        Point pt = new Point(100,100);
        warrior1 = new Warrior(pt);
        
        stateTime = 0;

    }

    @Override
    public void render() {
        startBatch(); // Set and starts the batch //

        background.draw(batch, stateTime); //Draws the background
        warrior1.draw(batch, stateTime);

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
