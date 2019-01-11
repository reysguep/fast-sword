package com.mygdx.game.Characters.actions;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.Team;
import com.mygdx.game.Characters.Character;

/**
 *
 * @author reysguep
 */
public class TargetSelection {
    public static Character randomEnemy(Character character, BattleScreen screen) {
        Array<Character> enemies;
        Character target;
        
        if(character.getTeam() == 'a') {
            enemies = screen.teamB.getLiveMembers();
        } else {
            enemies = screen.teamA.getLiveMembers();
        }
        
        target = enemies.random();
        
        return target;
    }
    
    public static Character randomAlly(Character character, BattleScreen screen) {
        Array<Character> allies;
        Character target;
        
        if(character.getTeam() == 'a') {
            allies = screen.teamA.getLiveMembers();
        } else {
            allies = screen.teamB.getLiveMembers();
        }
        
        target = allies.random();
        
        return target;
    }
    
    public static Array<Character> allEnemies(Character character, BattleScreen screen) {
        Array<Character> enemies;
        
        if(character.getTeam() == 'a') {
            enemies = screen.teamB.getLiveMembers();
        } else {
            enemies = screen.teamA.getLiveMembers();
        }
        
        return enemies;
    }
    
    public static Array<Character> allAllies(Character character, BattleScreen screen) {
        Array<Character> allies;
        
        if(character.getTeam() == 'a') {
            allies = screen.teamA.getLiveMembers();
        } else {
            allies = screen.teamB.getLiveMembers();
        }

        return allies;
    }
}
