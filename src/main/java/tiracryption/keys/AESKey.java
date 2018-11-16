package tiracryption.keys;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AESKey {
    
    private byte[] key;

    public AESKey(byte[] key) {
        this.key = key;
    }

    public AESKey(Path path) throws IOException {
        this.key = Files.readAllBytes(path);
        
    }
    
    public byte[] getKey() {
        return key;
    }
    
}
