package tiracryption.methods;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tiracryption.keys.RSAKeygen;

public class RSATest {
    
    private RSAKeygen gen = new RSAKeygen();
    
    @Test
    public void encryptionWorks() {
        RSA encrypt = new RSA(gen.getPublic());
        assertEquals("4", encrypt.encrypt(new BigInteger("2")));
    }
    
    @Test
    public void decryptionWorks() {
        RSA decrypt = new RSA(gen.getPrivate());
        assertEquals("2", decrypt.decrypt(new BigInteger("4")));
    }
    
}