package tiracryption.keys;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AESKeyTest {
    private byte[] testKeyBytes = { (byte) 0x60, (byte) 0x3d, (byte) 0xeb, (byte) 0x10,
                           (byte) 0x15, (byte) 0xca, (byte) 0x71, (byte) 0xbe,
                           (byte) 0x2b, (byte) 0x73, (byte) 0xae, (byte) 0xf0,
                           (byte) 0x85, (byte) 0x7d, (byte) 0x77, (byte) 0x81,
                           (byte) 0x1f, (byte) 0x35, (byte) 0x2c, (byte) 0x07,
                           (byte) 0x3b, (byte) 0x61, (byte) 0x08, (byte) 0xd7,
                           (byte) 0x2d, (byte) 0x98, (byte) 0x10, (byte) 0xa3,
                           (byte) 0x09, (byte) 0x14, (byte) 0xdf, (byte) 0xf4 };
    private AESKey testkey = new AESKey(testKeyBytes);
    
    @Test
    public void byteConstructor() {
        assertEquals(Arrays.toString(testKeyBytes), Arrays.toString(testkey.getKey()));
    }
    
    @Test
    public void expanding128BitKey() throws IOException {
        testkey = new AESKey(Paths.get("./src/test/java/tiracryption/keys/aes128key"));
        byte[][][] expandedKeys = testkey.getRoundKeys();
        assertEquals("2b7e1516", String.format("%02x%02x%02x%02x", expandedKeys[0][0][0], expandedKeys[0][1][0], expandedKeys[0][2][0], expandedKeys[0][3][0]));
        assertEquals("3d80477d", String.format("%02x%02x%02x%02x", expandedKeys[3][0][0], expandedKeys[3][1][0], expandedKeys[3][2][0], expandedKeys[3][3][0]));
        assertEquals("ead27321", String.format("%02x%02x%02x%02x", expandedKeys[8][0][0], expandedKeys[8][1][0], expandedKeys[8][2][0], expandedKeys[8][3][0]));
        assertEquals("b6630ca6", String.format("%02x%02x%02x%02x", expandedKeys[10][0][3], expandedKeys[10][1][3], expandedKeys[10][2][3], expandedKeys[10][3][3]));
    }
    
    @Test
    public void expanding192BitKey() throws IOException {
        testkey = new AESKey(Paths.get("./src/test/java/tiracryption/keys/aes192key"));
        byte[][][] expandedKeys = testkey.getRoundKeys();
        assertEquals("8e73b0f7", String.format("%02x%02x%02x%02x", expandedKeys[0][0][0], expandedKeys[0][1][0], expandedKeys[0][2][0], expandedKeys[0][3][0]));
        assertEquals("ec12068e", String.format("%02x%02x%02x%02x", expandedKeys[2][0][0], expandedKeys[2][1][0], expandedKeys[2][2][0], expandedKeys[2][3][0]));
        assertEquals("a25e7ed5", String.format("%02x%02x%02x%02x", expandedKeys[6][0][0], expandedKeys[6][1][0], expandedKeys[6][2][0], expandedKeys[6][3][0]));
        assertEquals("01002202", String.format("%02x%02x%02x%02x", expandedKeys[12][0][3], expandedKeys[12][1][3], expandedKeys[12][2][3], expandedKeys[12][3][3]));
    }
    
    @Test
    public void expanding256BitKey() throws IOException {
        testkey = new AESKey(Paths.get("./src/test/java/tiracryption/keys/aes256key"));
        byte[][][] expandedKeys = testkey.getRoundKeys();
        assertEquals("603deb10", String.format("%02x%02x%02x%02x", expandedKeys[0][0][0], expandedKeys[0][1][0], expandedKeys[0][2][0], expandedKeys[0][3][0]));
        assertEquals("9ba35411", String.format("%02x%02x%02x%02x", expandedKeys[2][0][0], expandedKeys[2][1][0], expandedKeys[2][2][0], expandedKeys[2][3][0]));
        assertEquals("b5a9328a", String.format("%02x%02x%02x%02x", expandedKeys[5][0][0], expandedKeys[5][1][0], expandedKeys[5][2][0], expandedKeys[5][3][0]));
        assertEquals("706c631e", String.format("%02x%02x%02x%02x", expandedKeys[14][0][3], expandedKeys[14][1][3], expandedKeys[14][2][3], expandedKeys[14][3][3]));
    }
}
