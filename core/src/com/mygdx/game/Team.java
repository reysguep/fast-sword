package com.mygdx.game;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Characters.Character;
import static libgdxUtils.StateCode.*;

/**
 *
 * @author reysguep
 */
public class Team extends Array<Character> {

    public Team(char team) {
        this.team = team;
    }

    private final char team;

    @Override
    public void add(Character character) {
        int nMembers;
        int x = 0;

        character.setTeam(team);
        
        super.add(character);
        nMembers = this.size;

        if (team == 'a') {
            switch (nMembers) {
                case 1:
                    x = 0;
                    break;
                case 2:
                    x = 160;
                    break;
                case 3:
                    x = 160 * 2;
                    break;
                case 4:
                    x = 160 * 3;
                    break;

            }
        } else {
            switch (nMembers) {
                case 1:
                    x = 160 * 6;
                    break;
                case 2:
                    x = 160 * 5;
                    break;
                case 3:
                    x = 160 * 4;
                    break;
                case 4:
                    x = (int) (4.5 * 160);
                    break;

            }
            character.flip(true, false);
        }
        character.setPosition(x, 100);
        character.setOrgX(x);
        character.setOrgY(100);
    }
    
    public Array<Character> getLiveMembers() {
        Array<Character> liveMembers;
        
        liveMembers = new Array<>();
        for(Character member : this) {
            switch(member.getState()) {
                case DEAD:
                case DYING:
                    break;
                default:
                    liveMembers.add(member);
            }
        }
        
        return liveMembers;
    }
}
