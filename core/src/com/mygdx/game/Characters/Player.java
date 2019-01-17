package com.mygdx.game.Characters;

import com.mygdx.game.Characters.presets.PlayerPreset;
import com.mygdx.game.Screens.BattleScreen;

/**
 *
 * @author reysguep
 */
public abstract class Player extends Character implements Comparable<Player> {

    public int takenSteps, minimumSteps;
    public long timeDied;

    private final br.cefetmg.move2play.model.Player playerModel;
    public int score;

    public Player(br.cefetmg.move2play.model.Player player,
            PlayerPreset preset, BattleScreen screen) {
        super(player.getName(), preset, screen);
        takenSteps = 0;
        this.minimumSteps = preset.steps;
        this.playerModel = player;

    }

    public static br.cefetmg.move2play.model.Player createPlayerModel(String name,
            String uuid) {
        br.cefetmg.move2play.model.Player playerModel;
        playerModel = new br.cefetmg.move2play.model.Player();

        playerModel.setUUID(uuid);
        playerModel.setName(name);
        //playerModel.setColor(color);

        return playerModel;
    }

    @Override
    public void action() {
        super.action();
        takenSteps -= minimumSteps;
    }

    public void addSteps(int steps) {
        if (takenSteps + steps > minimumSteps) {
            takenSteps = minimumSteps;
        } else if (takenSteps + steps < 0) {
            takenSteps = 0;
        } else {
            takenSteps += steps;
        }
    }

    @Override
    public boolean canAttack() {
        boolean factor1;

        factor1 = takenSteps >= minimumSteps;
        return super.canAttack(factor1);
    }

    public br.cefetmg.move2play.model.Player getPlayerModel() {
        return playerModel;
    }

    @Override
    public float getProgress() {
        float progress;

        progress = (float) takenSteps / minimumSteps; // >=1 -> pode atacar
        return progress;
    }

    public int getPedaladasRestantes() {
        return minimumSteps - takenSteps;
    }

    @Override
    public int compareTo(Player t) {
        return Integer.compare(t.score, this.score);
    }

}
