package com.mygdx.game.Characters.players;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Characters.presets.PlayerPreset;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Characters.actions.TargetSelection;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.actions.CharacterActions;

/**
 *
 * @author reysguep
 */
public class Viking extends Player {

    public Viking(br.cefetmg.move2play.model.Player playerModel, BattleScreen screen) {
        super(playerModel, createPreset(), screen);
    }

    private static PlayerPreset createPreset() {
        PlayerPreset preset = new PlayerPreset();

        preset.actionSound = "hit1";
        preset.deathSound = "death1";
        preset.hitAnimation = "slash";
        preset.folder = "viking";
        preset.maxHealth = 300;
        preset.strength = 45;
        preset.scale = 0.5f;
        preset.speed = 75;
        preset.steps = 15;

        return preset;
    }

    @Override
    public void action() {
        super.action();
        Array<Character> targets;

        targets = TargetSelection.allEnemies(this, screen);
        for (int i = 0; i < targets.size; i++) {
            screen.visualEffectManager.addEffect(this, targets.get(i));
            score += strength;
        }
        CharacterActions.normalAttack(this, targets);
    }
}
