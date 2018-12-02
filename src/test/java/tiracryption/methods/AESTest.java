package tiracryption.methods;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Paths;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tiracryption.keys.AESKey;
import tiracryption.keys.AESKeygen;
import tiracryption.structures.TiraRandom;

public class AESTest {

    @Test
    public void encryptionDecryptionWorksWith128BitKey() throws IOException {
        AESKey testKey128 = new AESKey(Paths.get("./src/test/java/tiracryption/keys/aes128key"));
        AES aes = new AES(testKey128);
        byte[] testMessage = {(byte) 0x32, (byte) 0x43, (byte) 0xf6, (byte) 0xa8, (byte) 0x88, (byte) 0x5a, (byte) 0x30, (byte) 0x8d, (byte) 0x31, (byte) 0x31, (byte) 0x98, (byte) 0xa2, (byte) 0xe0, (byte) 0x37, (byte) 0x07, (byte) 0x34};

        assertEquals(Arrays.toString(testMessage), Arrays.toString(aes.decrypt(aes.encrypt(testMessage))));
    }

    @Test
    public void encryptionDecryptionWorksWith192BitKey() throws IOException {
        AESKey testKey128 = new AESKey(Paths.get("./src/test/java/tiracryption/keys/aes192key"));
        AES aes = new AES(testKey128);
        byte[] testMessage = {(byte) 0x32, (byte) 0x43, (byte) 0xf6, (byte) 0xa8, (byte) 0x88, (byte) 0x5a, (byte) 0x30, (byte) 0x8d, (byte) 0x31, (byte) 0x31, (byte) 0x98, (byte) 0xa2, (byte) 0xe0, (byte) 0x37, (byte) 0x07, (byte) 0x34};

        assertEquals(Arrays.toString(testMessage), Arrays.toString(aes.decrypt(aes.encrypt(testMessage))));
    }
    
    @Test
    public void encryptionDecryptionWorksWith256BitKey() throws IOException {
        AESKey testKey128 = new AESKey(Paths.get("./src/test/java/tiracryption/keys/aes256key"));
        AES aes = new AES(testKey128);
        byte[] testMessage = {(byte) 0x32, (byte) 0x43, (byte) 0xf6, (byte) 0xa8, (byte) 0x88, (byte) 0x5a, (byte) 0x30, (byte) 0x8d, (byte) 0x31, (byte) 0x31, (byte) 0x98, (byte) 0xa2, (byte) 0xe0, (byte) 0x37, (byte) 0x07, (byte) 0x34};

        assertEquals(Arrays.toString(testMessage), Arrays.toString(aes.decrypt(aes.encrypt(testMessage))));
    }
    
    @Test
    public void encryptionWorksWith128BitKey() throws IOException {
        AESKey testKey128 = new AESKey(Paths.get("./src/test/java/tiracryption/keys/aes128key"));
        AES aes = new AES(testKey128);
        byte[] testMessage = {(byte) 0x32, (byte) 0x43, (byte) 0xf6, (byte) 0xa8, (byte) 0x88, (byte) 0x5a, (byte) 0x30, (byte) 0x8d, (byte) 0x31, (byte) 0x31, (byte) 0x98, (byte) 0xa2, (byte) 0xe0, (byte) 0x37, (byte) 0x07, (byte) 0x34};
        byte[] expectedOutcome = {(byte) 0x39, (byte) 0x25, (byte) 0x84, (byte) 0x1d, (byte) 0x02, (byte) 0xdc, (byte) 0x09, (byte) 0xfb, (byte) 0xdc, (byte) 0x11, (byte) 0x85, (byte) 0x97, (byte) 0x19, (byte) 0x6a, (byte) 0x0b, (byte) 0x32};
        byte[] encryptedMessage = aes.encrypt(testMessage);

        assertEquals(Arrays.toString(expectedOutcome), Arrays.toString(encryptedMessage));
    }
    
    @Test
    public void fileEncryptionWorks() throws IOException {
        TiraRandom rand = new TiraRandom();
        byte[] testmessage = new byte[10000];
        rand.nextBytes(testmessage);
        AESKeygen gen = new AESKeygen(rand);
        AES aes = new AES(gen.generateAESKey());
        byte[] result = new byte[10000];
        
        try {
            RandomAccessFile file1 = new RandomAccessFile(new File(Paths.get("./src/test/java/tiracryption/methods/testfile1").toUri()), "rw");
            file1.write(testmessage);
            file1.close();
            
            aes.encryptFile(Paths.get("./src/test/java/tiracryption/methods/testfile1"), Paths.get("./src/test/java/tiracryption/methods/testfile2"));
            aes.decryptFile(Paths.get("./src/test/java/tiracryption/methods/testfile2"), Paths.get("./src/test/java/tiracryption/methods/testfile3"));
            RandomAccessFile file2 = new RandomAccessFile(new File(Paths.get("./src/test/java/tiracryption/methods/testfile3").toUri()), "r");

            file2.readFully(result);

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        assertEquals(Arrays.toString(testmessage), Arrays.toString(result));
        
    }
}
