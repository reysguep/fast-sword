package com.mygdx.game;


/**
 *
 * @author reysguep
 */
public abstract class Player extends Character {

    public Player(String nome, int maxHealth, int strength, float pedaladasMinimas, String folder) {
        super(nome, maxHealth, strength, "player/" + folder);
        pedaladasDadas = 0;
        this.pedaladasMinimas = pedaladasMinimas;
    }

    private float pedaladasDadas;
    private float pedaladasMinimas;

    @Override
    public void attack(Character target){
        super.attack(target);
        pedaladasDadas -= pedaladasMinimas;
    }
    
    
    public void upgradeStrength() {
        strength *= (120 / 100);
    }

    public void upgradeHealth() {
        health *= (120 / 100);
    }

    public void upgradeSpeed() {
        pedaladasMinimas *= (80/100);
    }
    
    public void contarPedalada(float nPedaladas){
        pedaladasDadas += nPedaladas;
    }
    
    public boolean canAttack(){
        return pedaladasDadas >= pedaladasMinimas;
    }
    
}
