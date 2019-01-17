package com.mygdx.game.file.filters;

import java.io.File;
import java.io.FileFilter;
import libgdxUtils.FileUtils;

/**
 *
 * @author reysguep
 */
public class AudioFilter implements FileFilter {

    @Override
    public boolean accept(File file) {
        String fileName, extension;
        String[] splitedName;

        if(file.isDirectory()) {
            return false;
        }
        
        extension = FileUtils.getFileExtension(file);

        switch (extension) {
            case "wav":
            case "mp3":
            case "ogg":
                return true;
        }

        return false;
    }

}
