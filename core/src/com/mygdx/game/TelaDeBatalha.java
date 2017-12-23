package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Random;
import libgdxUtils.AnimationCode;

/**
 *
 * @author reysguep
 */
public class TelaDeBatalha {

    public TelaDeBatalha(ArrayList jogadores, ArrayList inimigos) {
        this.jogadores = jogadores;
        this.inimigos = inimigos;

        fundo = new Background();
    }

    private ArrayList<Player> jogadores;
    private ArrayList<Enemy> inimigos;
    private Background fundo;

    public void checarCondicoes() {
        Random random = new Random();
        int targetNumber;
        Character target;

        for (Player personagem : jogadores) {
            if (personagem.animations.getAnimation() == personagem.animations.getAnimation(AnimationCode.ATTACKING)) {
                if (personagem.animations.getAnimation().isAnimationFinished(personagem.animations.getTime())) {
                    personagem.setAnimation(AnimationCode.IDLE);
                }
            }

            if (personagem.canAttack() && !inimigos.isEmpty()) {
                ArrayList<Enemy> tempInimigos = (ArrayList<Enemy>) inimigos.clone();
                do {
                    targetNumber = random.nextInt(tempInimigos.size());
                    target = tempInimigos.remove(targetNumber);
                    if (!target.isDead()) {
                        personagem.attack(target);
                    }
                } while (target.isDead() && !tempInimigos.isEmpty());
            }
        }

        for (Enemy personagem : inimigos) {
            if (!personagem.isDead()) {
                if (personagem.animations.getAnimation() == personagem.animations.getAnimation(AnimationCode.ATTACKING)) {
                    if (personagem.animations.getAnimation().isAnimationFinished(personagem.animations.getTime())) {
                        personagem.setAnimation(AnimationCode.IDLE);
                    }
                }

                if (personagem.canAttack() && !jogadores.isEmpty()) {
                    ArrayList<Player> tempJogadores = (ArrayList<Player>) jogadores.clone();
                    boolean atacou = true;
                    do {
                        targetNumber = random.nextInt(tempJogadores.size());
                        target = tempJogadores.remove(targetNumber);
                        if (!target.isDead()) {
                            personagem.attack(target);
                            atacou = false;
                        }
                    } while (atacou && jogadores.isEmpty());
                }
            }
        }
    }

    public void draw(SpriteBatch batch) {
        fundo.draw(batch);

        for (Player personagem : jogadores) {
            personagem.draw(batch);
        }
        for (Enemy personagem : inimigos) {
            personagem.draw(batch);
        }
    }
}
