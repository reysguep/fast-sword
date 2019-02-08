package com.mygdx.game.exceptions;

/**
 *
 * @author reysguep
 */
public class StateNotFoundException extends Exception{
    public StateNotFoundException(String msg) {
        super("Couldn't find the state " + msg);
    }
}
