package com.mygdx.game.exceptions;

/**
 *
 * @author reysguep
 */
public class PlayerNotFound extends Exception{
    public PlayerNotFound(String uuid) {
        super("Couldn't find the player with UUID " + uuid);
    }
}
