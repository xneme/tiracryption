package tiracryption.keys;

import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RSAKeyTest {
    private RSAKey key = new RSAKey(new BigInteger("5"), new BigInteger("14"));
    
    @Test
    public void getKey() {
        assertEquals("5", key.getKey().toString());
    }
    
    @Test
    public void getMod() {
        assertEquals("14", key.getMod().toString());
    }
    
    @Test
    public void pathConstructor() {
        Path privateKeyPath = Paths.get("./src/test/java/tiracryption/keys/testRSAkey");
        Path publicKeyPath = Paths.get("./src/test/java/tiracryption/keys/testRSAkey.pub");
        
        RSAKey privateKey = new RSAKey(privateKeyPath);
        RSAKey publicKey = new RSAKey(publicKeyPath);
        
        assertEquals(privateKey.getMod(), publicKey.getMod());
    }
}