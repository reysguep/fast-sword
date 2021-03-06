package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import br.cefetmg.move2play.game.Move2PlayGame;
import br.cefetmg.move2play.model.Player;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Main;

public class DesktopLauncher implements Move2PlayGame {

    private Main gameClass;
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

    public static void main(String[] arg) {
        DesktopLauncher x = new DesktopLauncher();
        x.config.forceExit = true;
        x.run();
    }

    public void run() {
        gameClass = new Main();
        config.title = gameClass.getSettings().getGameName();
        config.width = 1280;
        config.height = 720;
        new LwjglApplication(gameClass, config);
    }

    @Override
    public void startMatch() {
        System.out.println("startMatch from launcher");
        gameClass.startMatch();
    }

    @Override
    public void finishMatch() {
        System.out.println("finishMatch from launcher");
        gameClass.finishMatch();
    }

    @Override
    public void addPlayer(Player player) {
        System.out.println("add from launcher");
        gameClass.addPlayer(player);
    }

    @Override
    public void removePlayer(Player player) {
        System.out.println("rm from launcher");
        gameClass.removePlayer(player);
    }

    @Override
    public void move(String uuid, int i) {
        System.out.println("move from launcher");
        gameClass.move(uuid, i);
    }

    @Override
    public void initGame() {
        System.out.println("init from launcher");
        config.forceExit = true;
        run();
        //gameClass.getSettings().setRunningOnMove2Play(true);
        System.out.println("new thread");
    }

    @Override
    public void closeGame() {
        System.out.println("exitGame from launcher");
        gameClass.closeGame();
    }
}
