package com.mygdx.game.exceptions;

/**
 *
 * @author mackg
 */
public class EmptyCharacterClassesException extends Exception{

    public EmptyCharacterClassesException() {
        super();
    }

    public EmptyCharacterClassesException(String string) {
        super(string + " classes array is empty!");
    }
    
}
