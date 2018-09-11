package libgdxUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author reysguep
 */
public class FileUtil {

    public static final short ALL = 0;
    public static final short IMAGE = 1;
    public static final short DOCUMENT = 2;
    public static final short VIDEO = 3;
    public static final short AUDIO = 4;
    public static final short UNKNOWN = -1;

    public static final String[] IMAGE_FORMATS = {"png", "jpeg", "jpg", "bmp"};
    public static final String[] AUDIO_FORMATS = {"mp3", "ogg", "wav"};
    public static final String[] DOCUMENT_FORMATS = {"txt", "html", "pdf"};

    public static ArrayList<String> allFileNames(String directoryName, int fileType, boolean withPath) {
        ArrayList<File> files = allFiles(directoryName, fileType);
        ArrayList<String> filesNames = new ArrayList<String>();
        
        for(File file : files) {
            String name;
            if(withPath == true){
                name = file.getAbsolutePath();
            } else {
                name = file.getName();
            }
            filesNames.add(name);
        }
        
        return filesNames;
    }
    
    public static ArrayList<File> allFiles(String directoryName, int fileType) {
        ArrayList<File> files = new ArrayList<File>();
        allFiles(directoryName, files, fileType);
        
        return files;
    }
    
    public static void allFiles(String directoryName, List<File> files, int fileType) {
        File directory = new File(directoryName);

        // Get all the files from a directory.
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                if (fileType == 0 || getFileType(file) == fileType) {
                    files.add(file);
                }
            } else if (file.isDirectory()) {
                allFiles(file.getAbsolutePath(), files, fileType);
            }
        }
    }

    public static short getFileType(File file) {
        for (String format : IMAGE_FORMATS) {
            if(format.equalsIgnoreCase(getFileExtension(file))) {
                return IMAGE;
            }
        }
        
        for (String format : AUDIO_FORMATS) {
            if(format.equalsIgnoreCase(getFileExtension(file))) {
                return AUDIO;
            }
        }
        
        return UNKNOWN;
    }
    
    public static String getFileExtension(File file) {
        String fileName = file.getName();
        //String extension = fileName.split(".")[1];
        
        return "jpeg";
    }
}
