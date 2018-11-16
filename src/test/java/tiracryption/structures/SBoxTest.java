package tiracryption.structures;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tiracryption.methods.RSA;

public class SBoxTest {
    private SBox sbox= new SBox();
    
    @Test
    public void hex00Works() {
        assertEquals((byte) 0x63, sbox.convert((byte) 0x00));
    }
    
    @Test
    public void hexFFWorks() {
        assertEquals((byte) 0x16, sbox.convert((byte) 0xff));
    }
    
    @Test
    public void hex52Works() {
        assertEquals((byte) 0x00, sbox.convert((byte) 0x52));
    }
    
    @Test
    public void hex7AWorks() {
        assertEquals((byte) 0xda, sbox.convert((byte) 0x7a));
    }
}
