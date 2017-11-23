package com.mygdx.game;

import java.awt.Point;

/**
 *
 * @author reysguep
 */
public abstract class Player extends Character {

    public Player(int health, int maxHealth, int strength, Point position, float pedaladasMinimas, String folderAnim) {
        super(health, maxHealth, strength, position, "player/" + folderAnim);
        pedaladasDadas = 0;
        this.pedaladasMinimas = pedaladasMinimas;
    }

    protected float pedaladasDadas;
    protected float pedaladasMinimas;

    public void upgradeStrength() {
        strength *= (120 / 100);
    }

    public void upgradeHealth() {
        health *= (120 / 100);
    }

    public void upgradeSpeed() {
        pedaladasMinimas *= (80/100);
    }
    
    
}
