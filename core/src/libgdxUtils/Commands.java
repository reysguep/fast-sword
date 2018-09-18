package libgdxUtils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Screens.BattleScreen;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.HitAnimation;
import libgdxUtils.exceptions.CommandException;

/**
 *
 * @author reysguep
 */
public class Commands {

    /*
    @Commands:
        attack; heal; summon
    @Selections:
        random; all;
        lowest/greatest: life, strength
    @Groups:
        enemy; ally; character

    
    #Examples:
        attack random enemy
        heal lowest life ally
        attack all enemy
        heal all ally
     */
    public Commands(BattleScreen screen) {
        this.screen = screen;
    }

    private final BattleScreen screen;

    public void call(String command, Character chr) throws CommandException {
        if (command.equals("nothing")) {
            return;
        }
        Array<Character> targets, allies, enemies;
        int n;
        String[] parts = command.split(" ");

        if (chr.team == 'a') {
            enemies = screen.teamB;
            allies = screen.teamA;
        } else {
            allies = screen.teamB;
            enemies = screen.teamA;
        }

        if (parts[2].equals("enemy")) {
            targets = enemies;
        } else if (parts[2].equals("ally")) {
            targets = allies;
            chr.getAnimations().flipFrames(true, false, true);
        } else if (parts[2].equals("character")) {
            targets = screen.allCharacters;
        } else {
            throw new CommandException("groups", chr.getName());
        }

        if (parts[0].equals("summon")) {
            if (parts[1].equals("random")) {

            } else {

            }
            return;
        }

        if (parts[1].equals("random")) {
            Character target;
            target = targets.random();
            if (parts[0].equals("attack")) {
                attack(chr, target);
            } else if (parts[0].equals("heal")) {
                heal(chr, target);
            } else if (parts[0].equals("kill")) {
                kill(chr, target);
            } else {
                throw new CommandException("commands", chr.getName());
            }
        } else if (parts[1].equals("all")) {
            if (parts[0].equals("attack")) {
                attack(chr, targets);
            } else if (parts[0].equals("heal")) {
                heal(chr, targets);
            } else if (parts[0].equals("kill")) {
                kill(chr, targets);
            } else {
                throw new CommandException("commands", chr.getName());
            }
        } else if (parts[1].equals("lowest_health")) {
            Character target = targets.get(0);
            for(Character tgt : targets){
                if(tgt.health < target.health) {
                    target = tgt;
                }
            }
            if (parts[0].equals("attack")) {
                attack(chr, target);
            } else if (parts[0].equals("heal")) {
                heal(chr, target);
            } else if (parts[0].equals("kill")) {
                kill(chr, target);
            } else {
                throw new CommandException("commands", chr.getName());
            }
        } else {
            throw new CommandException("sections", chr.getName());
        }
    }

    private void summon(Character chr, Array<Character> targets) {
        for (Character target : targets) {
            summon(chr, target);
        }
    }

    private void summon(Character chr, Character target) {

    }

    private void attack(Character chr, Array<Character> targets) {
        for(int i = 0; i < targets.size; i++){
            Character target = targets.get(i);
            attack(chr, target);
        }
    }

    private void attack(Character chr, Character target) {
        int multiplier = 1;

        target.beAttacked(chr.getStrength(), this);
        System.out.println(chr.getName() + " attacked " + target.getName() + ".");

        if (target.isDead()) {
            multiplier = 2;
            if (target.team == 'a') {
                screen.teamA.removeValue(target, true);
                if (screen.teamA.size == 0) {
                    multiplier = 3;
                }
            } else {
                screen.teamB.removeValue(target, true);
            }
            screen.tweenManager.killTarget(target, 1);
        }

        if (chr instanceof Player) {
            Player player = (Player) chr;
            player.score += player.getStrength() * multiplier;
        }
        addHitAnimation(chr, target);
    }

    private void heal(Character chr, Array<Character> targets) {
        for (Character target : targets) {
            heal(chr, target);
        }
    }

    private void heal(Character chr, Character target) {
        int score = target.getMaxHealth() - target.getHealth();
        
        if (!target.isDead()) {
            target.health += chr.getStrength();
            if (target.health > target.getMaxHealth()) {
                target.health = target.getMaxHealth();
            }
            System.out.println(chr.getName() + " healed " + target.getName() + ".");
        }
        
        if(chr instanceof Player) {
            Player player = (Player) chr;
            player.score += score / 3;
        }
        addHitAnimation(chr, target);
    }

    private void kill(Character chr, Array<Character> targets) {
        for (Character target : targets) {
            kill(chr, target);
        }
    }

    private void kill(Character chr, Character target) {
        target.setStatus(StatusCode.DYING);
        if (target.team == 'a') {
            screen.teamA.removeValue(target, true);
        } else {
            screen.teamB.removeValue(target, true);
        }
        screen.tweenManager.killTarget(target, 1);

        System.out.println(chr.getName() + " killed " + target.getName() + " instantly.");
        addHitAnimation(chr, target);
    }
    
    private void addHitAnimation(Character chr, Character target) {
        Animation<TextureRegion> anim = chr.targetAnimation;
        screen.hits.add(new HitAnimation(target, anim, screen.hits));
    }
}
