package libgdxUtils;

import com.mygdx.game.Screens.BattleScreen;

/**
 *
 * @author reysguep
 */
public class Commands {
    public Commands(BattleScreen screen){
        this.screen = screen;
    }
    
    private final BattleScreen screen;
    
    
    public void call(String command){
        String[] parts = command.split(" ");
        
        if(parts[0].equals("summon")){
            summon(parts);
        }
    }
    
    private void summon(String[] parts){
        boolean isBoss;
        int width, height, strength;
        String name, team;
        float timeToAttack;
        
        
    }
}
