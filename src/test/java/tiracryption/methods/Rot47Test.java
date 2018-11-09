package tiracryption.methods;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Rot47Test {
    private Rot47 rot47 = new Rot47();
    
    @Test
    public void encryptToimii() {
        assertEquals("%96 \"F:4< qC@H? u@I yF>AD ~G6C %96 {2KJ s@8", rot47.encrypt("The Quick Brown Fox Jumps Over The Lazy Dog"));
    }
    
    @Test
    public void decryptToimii() {
        assertEquals("The Quick Brown Fox Jumps Over The Lazy Dog", rot47.decrypt("%96 \"F:4< qC@H? u@I yF>AD ~G6C %96 {2KJ s@8"));
    }
}
