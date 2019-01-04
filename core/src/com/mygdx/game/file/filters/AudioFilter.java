package com.mygdx.game.file.filters;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author reysguep
 */
public class AudioFilter implements FileFilter {

    @Override
    public boolean accept(File file) {
        String fileName, extension;
        String[] splitedName;

        fileName = file.getName();
        splitedName = fileName.split(".");
        extension = splitedName[splitedName.length - 1];

        switch (extension) {
            case "wav":
            case "mp3":
            case "ogg":
                return true;
        }

        return false;
    }

}
