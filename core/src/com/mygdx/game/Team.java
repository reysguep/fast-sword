package com.mygdx.game;

import com.mygdx.game.Characters.Character;
import java.util.ArrayList;

/**
 *
 * @author reysguep
 */
public class Team extends ArrayList<Character>{

    public Team(char team) {
        this.team = team;
    }

    private final char team;


    public void addMember(Character character) {
        int nMembers;
        int x = 0;
        
        super.add(character);
        nMembers = size();
        
        character.resizeLockWidth(300);
        
        if (team == 'A' || team == 'a') {
            switch(nMembers){
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
            switch(nMembers){
                case 1:
                    x = 160 * 6;
                    break;
                case 2:
                    x = 160 * 6;
                    break;
                case 3:
                    x = 160 * 5;
                    break;
                case 4:
                    x = 160 * 4;
                    break;
                    
            }
            character.flipAllAnimations(true, false);
        }
        character.setPosition(x, 100);
    }
}