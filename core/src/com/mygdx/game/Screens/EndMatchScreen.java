package com.mygdx.game.Screens;

import br.cefetmg.move2play.game.Move2PlayGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main;
import com.mygdx.game.Characters.Player;

/**
 *
 * @author reysguep
 */
public class EndMatchScreen implements Screen, Move2PlayGame{

    public EndMatchScreen(Main game, Array<Player> players) {
        this.game = game;
        this.players = players;
    }
    
    private final Main game;
    private Texture background;
    private final Array<Player> players;
    
    private BitmapFont gameOverMessage, playerMessage;
    private GlyphLayout layout;
    private Music music;
    private SpriteBatch batch;
    
    @Override
    public void show() {
        players.sort();
        
        layout = new GlyphLayout();
        music = Gdx.audio.newMusic(Gdx.files.internal("Audios/musics/evocation.mp3"));
        background = new Texture(Gdx.files.internal("Animations/endMatchBG.jpg"));
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/VeniceClassic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        parameter.borderWidth = 5;
        gameOverMessage = generator.generateFont(parameter);
        
        FreeTypeFontGenerator generatorP = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/VeniceClassic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        
        parameterP.size = 45;
        parameterP.borderWidth = 3;
        playerMessage = generatorP.generateFont(parameterP);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(background, 0, 0, 1280, 720);
        drawGOMessage();
        
        int x = 50, y;
        for(int i = 0; i < players.size; i++) {
            Player player = players.get(i);
            y = 400 - 60 * i;
            playerMessage.draw(batch, player.getName() + ": " + player.score + " pts", x, y);
        }

        batch.end();
    }
    
    private void drawGOMessage(){
        int x, y;
        CharSequence text = "Game Over!";
        layout.setText(gameOverMessage, text);
        y = 650;
        x = (int) ((1280 / 2) - (layout.width / 2));
        gameOverMessage.draw(batch, text, x, y);
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

    @Override
    public void initGame() {
        game.setScreen(new WaitingScreen(game));
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
    public void addPlayer(br.cefetmg.move2play.model.Player player) {
    }

    @Override
    public void removePlayer(br.cefetmg.move2play.model.Player player) {
    }

    @Override
    public void move(String string, int i) {
    }

}
