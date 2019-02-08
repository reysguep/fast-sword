package com.mygdx.game;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Screens.BattleScreen;
import libgdxUtils.StateCode;
import static libgdxUtils.StateCode.*;

/**
 *
 * @author reysguep
 */
public class Team extends Array<Character> {

    public Team(char team, BattleScreen screen) {
        this.team = team;
        this.screen = screen;
    }

    private final char team;
    private final BattleScreen screen;

    @Override
    public void add(Character character) {
        int nMembers;
        int x = 0;

        character.setTeam(team);
        screen.allCharacters.add(character);

        super.add(character);
        nMembers = this.size;

        if (team == 'a') {
            switch (nMembers) {
                case 1:
                    x = 75;
                    break;
                case 2:
                    x = 225;
                    break;
                case 3:
                    x = 375;
                    break;
                case 4:
                    x = 525;
                    break;

            }
        } else {
            switch (nMembers) {
                case 1:
                    x = 1205;
                    break;
                case 2:
                    x = 1055;
                    break;
                case 3:
                    x = 905;
                    break;
                case 4:
                    x = 755;
                    break;

            }
        }
        character.setOrgX(x);
        character.setOrgY(150);
        
        character.setState(StateCode.WAITING);
    }

    @Override
    public void addAll(Array<? extends Character> array) {
        for (Character character : array) {
            add(character);
        }
    }

    public Array<Character> getLiveMembers() {
        Array<Character> liveMembers;

        liveMembers = new Array<>();
        for (Character member : this) {
            switch (member.getState()) {
                case DEAD:
                case DYING:
                case REVIVING:
                    break;
                default:
                    liveMembers.add(member);
            }
        }

        return liveMembers;
    }
}
