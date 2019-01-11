package com.mygdx.game.loaders;

import com.badlogic.gdx.utils.Array;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reysguep
 */
public class CharacterLoader {

    private static final String[] PLAYERS = new String[]{
        "Viking",
        "Archer"
    };

    private static final String[] ENEMIES = new String[]{
        "Skeleton",
        "Slime"
    };

    private static final String PLAYERS_PACKAGE_PATH
            = "com.mygdx.game.Characters.players";

    private static final String ENEMIES_PACKAGE_PATH
            = "com.mygdx.game.Characters.enemies";

    public static Array<Constructor<?>> getEnemyConstructors() {
        Array<Constructor<?>> enemyConstructors;
        Array<Class<?>> enemyClasses;
        Class<?>[] types;

        types = new Class<?>[1];
        types[0] = com.mygdx.game.Screens.BattleScreen.class;
        enemyClasses = createCharacterClasses(ENEMIES_PACKAGE_PATH, ENEMIES);
        enemyConstructors = createCharacterConstructors(enemyClasses, types);

        return enemyConstructors;
    }
    
    public static Array<Constructor<?>> getPlayerConstructors() {
        Array<Constructor<?>> playerConstructors;
        Array<Class<?>> playerClasses;
        Class<?>[] types;

        types = new Class<?>[2];
        types[0] = br.cefetmg.move2play.model.Player.class;
        types[1] = com.mygdx.game.Screens.BattleScreen.class;
        playerClasses = createCharacterClasses(PLAYERS_PACKAGE_PATH, PLAYERS);
        playerConstructors = createCharacterConstructors(playerClasses, types);

        return playerConstructors;
    }

    private static Array<Constructor<?>> createCharacterConstructors(
            Array<Class<?>> characterClasses, Class<?>[] types) {

        Array<Constructor<?>> characterConstructors;

        characterConstructors = new Array<>();
        for (Class<?> characterClass : characterClasses) {
            Constructor<?> characterConstructor;

            characterConstructor = createCharacterConstructor(characterClass, types);
            characterConstructors.add(characterConstructor);
        }

        return characterConstructors;
    }

    private static Constructor<?> createCharacterConstructor(Class<?> characterClass, Class<?>[] types) {
        Constructor<?> characterConstructor = null;

        try {
            characterConstructor = characterClass.getConstructor(types);
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(CharacterLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return characterConstructor;
    }

    private static Array<Class<?>> createCharacterClasses(String classPath, String[] classesNames) {
        Array<Class<?>> characterClasses;

        characterClasses = new Array<>();

        for (String className : classesNames) {
            Class<?> characterClass;
            String classCompleteName;

            classCompleteName = classPath + className;
            characterClass = createCharacterClass(classCompleteName);
            characterClasses.add(characterClass);
        }

        return characterClasses;
    }

    private static Class<?> createCharacterClass(String classCompletePath) {
        Class<?> characterClass = null;

        try {
            characterClass = Class.forName(classCompletePath);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CharacterLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return characterClass;
    }

    
}
