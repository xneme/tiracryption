package tiracryption.structures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created randomness.
 */
public class TiraRandom {
    public Path pathToRandomness;

    public TiraRandom(Path pathToRandomness) {
        this.pathToRandomness = pathToRandomness;
    }

    public TiraRandom() {
        this.pathToRandomness = Paths.get("/dev/urandom");
    }
    
    public int nextBytes(byte[] target) {
        int read = 0;
        try {
            RandomAccessFile randomness = new RandomAccessFile(new File(pathToRandomness.toUri()), "r");
            read = randomness.read(target);
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " + ex.toString());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.toString());
        }
        
        return read;
    }
}
