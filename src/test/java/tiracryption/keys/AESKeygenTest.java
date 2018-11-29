package tiracryption.keys;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AESKeygenTest {
    private SecureRandom random = new SecureRandom();
    private AESKeygen gen = new AESKeygen(random); 
    
    @Test
    public void generatedKeyIsRightLength() {
        byte[] key = gen.generateAESKey().getKey();
        
        assertEquals(32, key.length);
    }
    
    @Test
    public void generatedKeyIsSpecifiedLength() {
        byte[] key = gen.generateAESKey(192).getKey();
        
        assertEquals(24, key.length);
    }
    
    @Test
    public void writtenKeyIsRightLength() throws IOException {
        byte[] key = gen.generateAESKey().getKey();
        Path keyPath = Paths.get("./src/test/java/tiracryption/keys/testAESkey");
        
        gen.writeKeyFile(keyPath);
        
        byte[] readKey = Files.readAllBytes(keyPath);
        
        assertEquals(32, key.length);
    }
    
    @Test
    public void writtenKeyIsSpecifiedLength() throws IOException {
        Path keyPath = Paths.get("./src/test/java/tiracryption/keys/testAESkey");
        
        gen.writeKeyFile(keyPath, 192);
        
        byte[] readKey = Files.readAllBytes(keyPath);
        
        assertEquals(24, readKey.length); 
    }
}
