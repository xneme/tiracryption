package tiracryption.keys;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RSAKeyTest {
    private RSAKey key = new RSAKey(new BigInteger("5"), new BigInteger("14"));
    
    @Test
    public void getKeyWorks() {
        assertEquals("5", key.getKey().toString());
    }
    
    @Test
    public void getModWorks() {
        assertEquals("14", key.getMod().toString());
    }
}