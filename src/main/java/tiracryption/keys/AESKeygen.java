package tiracryption.keys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;

public class AESKeygen {
    
    private SecureRandom random;

    public AESKeygen(SecureRandom random) {
        this.random = random;
    }
    
    public AESKey generateAESKey() {
        byte[] key = new byte[32];
        random.nextBytes(key);
        return new AESKey(key);
    }
    
    public AESKey generateAESKey(int bitLength) {
        byte[] key = new byte[bitLength / 8];
        random.nextBytes(key);
        return new AESKey(key);
    }
    
    public void writeKeyFile(Path path) throws IOException {
        if (path.getParent() != null) {
            File directory = new File(path.getParent().toUri());
            directory.mkdirs();
        }
        Files.write(path, this.generateAESKey().getKey());
    }
    
    public void writeKeyFile(Path path, int bitLength) throws IOException {
        if (path.getParent() != null) {
            File directory = new File(path.getParent().toUri());
            directory.mkdirs();
        }
        Files.write(path, this.generateAESKey(bitLength).getKey());
    }
}
