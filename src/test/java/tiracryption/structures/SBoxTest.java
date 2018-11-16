package tiracryption.structures;

import java.security.SecureRandom;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SBoxTest {
    private SBox sbox= new SBox();
    
    @Test
    public void forwardHex00() {
        assertEquals((byte) 0x63, sbox.forward((byte) 0x00));
    }
    
    @Test
    public void forwardHexFF() {
        assertEquals((byte) 0x16, sbox.forward((byte) 0xff));
    }
    
    @Test
    public void forwardHex52() {
        assertEquals((byte) 0x00, sbox.forward((byte) 0x52));
    }
    
    @Test
    public void forwardHex7A() {
        assertEquals((byte) 0xda, sbox.forward((byte) 0x7a));
    }
    
    @Test
    public void reverseHex16() {
        assertEquals((byte) 0xff, sbox.reverse((byte) 0x16));
    }
    
    @Test
    public void randomKeyForwardReverse() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32];
        random.nextBytes(key);
        
        assertEquals(Arrays.toString(key), Arrays.toString(sbox.reverse(sbox.forward(key))));
    }
}
