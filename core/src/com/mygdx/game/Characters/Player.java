package com.mygdx.game.Characters;

import com.mygdx.game.Characters.Character;

/**
 *
 * @author reysguep
 */

public abstract class Player extends Character {

    public Player(int maxHealth, int strength, int pedaladasMinimas, String folder, br.cefetmg.move2play.model.Player player) {
        super(player.getName(), maxHealth, strength, "player/" + folder);
        pedaladasDadas = 0;
        this.pedaladasMinimas = pedaladasMinimas;
        uuid = player.getUUID();
    }
    
    public Player(int maxHealth, int strength, int pedaladasMinimas, String folder, String name) {
        super(name, maxHealth, strength, "player/" + folder);
        pedaladasDadas = 0;
        this.pedaladasMinimas = pedaladasMinimas;
        uuid = "1111";
    }

    private int pedaladasDadas;
    private int pedaladasMinimas;

    private final String uuid;

    @Override
    public void attack(Character target) {
        super.attackMessage(target);
        pedaladasDadas -= pedaladasMinimas;
    }

    public void upgradeStrength() {
        strength *= (120 / 100);
    }

    public void upgradeHealth() {
        health *= (120 / 100);
    }

    public void upgradeSpeed() {
        pedaladasMinimas *= (80 / 100);
    }

    public void contarPedalada(float nPedaladas) {
        pedaladasDadas += nPedaladas;
    }

    @Override
    public boolean canAttack() {
        return pedaladasDadas >= pedaladasMinimas;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public float getProgress(){
        float progress;
        
        progress = (float)pedaladasDadas / pedaladasMinimas; // 1 = pode atacar
        return progress;
    }
}
