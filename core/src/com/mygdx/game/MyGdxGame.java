package com.mygdx.game;

import com.mygdx.game.Screens.SplashScreen;
import br.cefetmg.move2play.game.Move2PlayGame;
import br.cefetmg.move2play.settings.GameSettings;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class MyGdxGame extends Game implements Move2PlayGame {

    public Move2PlayGame eventHandler;
    private final GameSettings gs;
    private Screen previousScreen = null;
    
    //private Assets resources;
    
    public MyGdxGame(){
        eventHandler = this;
        gs = new GameSettings(MyGdxGame.class);
        //gs.loadSettings();
    }
    
    @Override
    public void create() {
        setScreen(new SplashScreen(this));
    }
    
    public GameSettings getSettings() {
        return gs;
    }

    @Override
    public void render() {
        super.render();
        
        if(previousScreen != null){
            previousScreen.dispose();
            previousScreen = null;
        } 
    }
    
    @Override
    public void setScreen(Screen screen){
        previousScreen = this.screen;
        super.setScreen(screen);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
    
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void startMatch() {
        if(eventHandler!=this)
            eventHandler.startMatch();
        else
            System.out.println("StartMatch from Main");
    }

    @Override
    public void finishMatch() {
        if(eventHandler!=this)
            eventHandler.finishMatch();
        else
            System.out.println("AddPlayer from Main");
    }

    @Override
    public void addPlayer(br.cefetmg.move2play.model.Player player) {
        if(eventHandler!=this)
            eventHandler.addPlayer(player);
        else
            System.out.println("AddPlayer from Main");
    }

    @Override
    public void removePlayer(br.cefetmg.move2play.model.Player player) {
        if(eventHandler!=this)
            eventHandler.removePlayer(player);
        else
            System.out.println("RemomvePlayer from Main");
    }

    @Override
    public void initGame() {
        if(eventHandler!=this)
            eventHandler.initGame();
        else
            System.out.println("InitGame from Main");
    }

    @Override
    public void closeGame() {
        if(eventHandler!=this)
            eventHandler.closeGame();
        else
            System.out.println("ExitGame from Main");
    }

    @Override
    public void move(String uuid, int i) {
        if(eventHandler!=this)
            eventHandler.move(uuid,i);
        else
            System.out.println("Move from Main");
    }
}

