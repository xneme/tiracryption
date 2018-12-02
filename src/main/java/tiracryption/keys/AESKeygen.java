package tiracryption.keys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import tiracryption.structures.TiraRandom;

/**
 * Generator for AES keys.
 */
public class AESKeygen {
    
    private TiraRandom random;

    /**
     *
     * @param random Random generator for randomness
     */
    public AESKeygen(TiraRandom random) {
        this.random = random;
    }
    
    /**
     * Generates a 256 bit AES key
     * 
     * @return
     */
    public AESKey generateAESKey() {
        byte[] key = new byte[32];
        random.nextBytes(key);
        return new AESKey(key);
    }
    
    /**
     * Generates an AES key of specified length
     * 
     * @param bitLength Key length in bits, either 128, 192 or 256
     * @return
     */
    public AESKey generateAESKey(int bitLength) {
        byte[] key = new byte[bitLength / 8];
        random.nextBytes(key);
        return new AESKey(key);
    }
    
    /**
     * Generates a 256 bit AES key and writes it to file
     * 
     * @param path Path to target file
     * @throws IOException
     */
    public void writeKeyFile(Path path) throws IOException {
        if (path.getParent() != null) {
            File directory = new File(path.getParent().toUri());
            directory.mkdirs();
        }
        Files.write(path, this.generateAESKey().getKey());
    }
    
    /**
     * Generates an AES key of specified length and writes it to file
     * 
     * @param path Path to target file
     * @param bitLength Key length in bits, either 128, 192 or 256
     * @throws IOException
     */
    public void writeKeyFile(Path path, int bitLength) throws IOException {
        if (path.getParent() != null) {
            File directory = new File(path.getParent().toUri());
            directory.mkdirs();
        }
        Files.write(path, this.generateAESKey(bitLength).getKey());
    }
}
