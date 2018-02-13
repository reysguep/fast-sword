package com.mygdx.game.Characters;

/**
 *
 * @author reysguep
 */
public class Gunman extends Player{
    public Gunman(br.cefetmg.move2play.model.Player player){
        super(200, 55, 8, "gunman", player);
    }
    
    public Gunman(String nome) {
        super(250, 65, 15, "gunman", nome);
    }
}
