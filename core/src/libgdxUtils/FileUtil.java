package libgdxUtils;

import com.badlogic.gdx.Gdx;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reysguep
 * @param <T>
 */
public class FileUtil<T extends Serializable> {
    public static void writeFile(Serializable object, String fileName){
        try{
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
            os.writeObject(object);
            os.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public T readFile(String fileName) throws ClassNotFoundException{
        T object = null;
        try {
            ObjectInputStream is;
            is = new ObjectInputStream(new FileInputStream(Gdx.files.internal(fileName).file()));
            object = (T)is.readObject();
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return object;
    }
}
