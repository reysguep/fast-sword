package com.mygdx.game.Characters;

/**
 *
 * @author reysguep
 */

public abstract class Player extends Character implements Comparable<Player>{

    public Player(br.cefetmg.move2play.model.Player player, PlayerPreset preset, char team) {
        super(player.getName());
        pedaladasDadas = 0;
        this.pedaladasMinimas = preset.steps;
        this.playerModel = player;
        
    }

    public Player(String name) {
        super(name);
        playerModel = new br.cefetmg.move2play.model.Player();
        playerModel.setUUID("1111");
    }

    public int pedaladasDadas, pedaladasMinimas;
    public long timeDied;

    private br.cefetmg.move2play.model.Player playerModel;
    public int score;

    @Override
    public void action() {
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

    public br.cefetmg.move2play.model.Player getPlayerModel() {
        return playerModel;
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
