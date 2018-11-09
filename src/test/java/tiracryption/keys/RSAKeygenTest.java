package tiracryption.keys;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RSAKeygenTest {
    
    private RSAKeygen keygen = new RSAKeygen();
    
    @Test
    public void generatedKeyPairHasSameMod() {
        assertEquals(true, keygen.getPrivate().getMod().equals(keygen.getPublic().getMod()));
    }
    
    @Test
    public void generatedKeyPairWorks() {
        String message = "3";
        BigInteger encryptedMessage = new BigInteger(message).modPow(keygen.getPublic().getKey(), keygen.getPublic().getMod());
        BigInteger decryptedMessage = encryptedMessage.modPow(keygen.getPrivate().getKey(), keygen.getPrivate().getMod());
        assertEquals(message, decryptedMessage.toString());
    }
}