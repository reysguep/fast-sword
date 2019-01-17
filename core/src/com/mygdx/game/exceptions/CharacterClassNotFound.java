package com.mygdx.game.exceptions;

/**
 *
 * @author reysguep
 */
public class CharacterClassNotFound extends Exception{
    
    public CharacterClassNotFound(String type, int classNumber) {
        super("Couldn't find the " + type + " class with number " + classNumber);
    }
}
