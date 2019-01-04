package com.mygdx.game.exceptions;

/**
 *
 * @author reysguep
 */
public class SoundEffectNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>SoundEffectNotFoundException</code>
     * without detail message.
     */
    public SoundEffectNotFoundException() {
        this("");
    }

    public SoundEffectNotFoundException(String msg) {
        super("Couldn't find the sound effect \"" + msg + "\".");
    }
}
