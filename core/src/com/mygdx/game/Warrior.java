package com.mygdx.game;

/**
 *
 * @author reysguep
 */
public class Warrior extends Player {

    public Warrior(br.cefetmg.move2play.model.Player player) {
        super(250, 65, 15, "warrior", player);
    }
    
    public Warrior(String nome) {
        super(250, 65, 15, "warrior", nome);
    }
}
    
