package com.mygdx.game.Characters;

import libgdxUtils.Commands;

/**
 *
 * @author reysguep
 */

public class Player extends Character implements Comparable<Player>{

    public Player(br.cefetmg.move2play.model.Player player, PlayerPreset preset) {
        super(player.getName(), preset);
        pedaladasDadas = 0;
        this.pedaladasMinimas = preset.pedaladasMinimas;
        uuid = player.getUUID();
    }

    public Player(String name, PlayerPreset pst) {
        super(name, pst);
        pedaladasDadas = 0;
        this.pedaladasMinimas = pst.pedaladasMinimas;
        uuid = "1111";
    }

    public int pedaladasDadas, pedaladasMinimas;
    
    public long timeDied;

    private final String uuid;
    
    public int score;

    @Override
    public void act() {
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
    public float getProgress() {
        float progress;

        progress = (float) pedaladasDadas / pedaladasMinimas; // >=1 -> pode atacar
        return progress;
    }

    public float getPedaladasRestantes() {
        return pedaladasMinimas - pedaladasDadas;
    }

    @Override
    public int compareTo(Player t) {
        return Integer.compare(t.score, this.score);
    }
}
