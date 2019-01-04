package com.mygdx.game.file.filters;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author reysguep
 */
public class DirectoryFilter implements FileFilter{

    @Override
    public boolean accept(File file) {
        return file.isDirectory();
    }
    
}
