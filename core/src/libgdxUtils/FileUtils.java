package libgdxUtils;

import java.io.File;

/**
 *
 * @author reysguep
 */
public class FileUtils {

    public static String getFileExtension(File file) {
        String extension = "", fileName;
        
        fileName = file.getName();
        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p) {
            extension = fileName.substring(i + 1);
        }
        
        return extension;
    }
    
    
}
