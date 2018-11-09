package tiracryption.methods;

import static org.junit.Assert.*;
import org.junit.Test;

public class Rot13Test {
    private Rot13 rot13 = new Rot13();
    
    @Test
    public void encryptToimii() {
        assertEquals("GVEN orfg? Vaqrrq!", rot13.encrypt("TIRA best? Indeed!"));
    }
    
    @Test
    public void decryptToimii() {
        assertEquals("TIRA best? Indeed!", rot13.decrypt("GVEN orfg? Vaqrrq!"));
    }

}
