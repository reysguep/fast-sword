package com.mygdx.game;

import com.mygdx.game.Screens.SplashScreen;
import br.cefetmg.move2play.game.Move2PlayGame;
import br.cefetmg.move2play.model.Player;
import br.cefetmg.move2play.settings.GameSettings;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;

public class Main extends Game implements Move2PlayGame, ApplicationListener {

    public static final short LOADING_SCREEN = 0;
    public static final short SPLASH_SCREEN = 1;
    public static final short WAITING_SCREEN = 2;
    public static final short BATTLE_SCREEN = 3;
    public static final short FINAL_SCREEN = 4;
    
    public Move2PlayGame eventHandler;
    private final GameSettings gs;
    private Screen previousScreen = null;
    
    
    //private Assets resources;
    
    public Main(){
        eventHandler = this;
        gs = new GameSettings(Main.class);
        //gs.loadSettings();
    }
    
    public void changeScreen() {
        
    }
    
    @Override
    public void create() {
        Assets manager = new Assets();
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
    
    public static Player createPlayer(int index, String name){
        Player player;
        player = new Player();
        
        player.setName(name);
        player.setUUID("test00" + index);
        //player.setColor(color);
        
        return player;
    }
}

