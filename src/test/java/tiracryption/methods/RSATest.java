package tiracryption.methods;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tiracryption.keys.RSAKey;
import tiracryption.keys.RSAKeygen;

public class RSATest {

    RSAKey mockPublicKey = new RSAKey(new BigInteger("65537"), new BigInteger("145906768007583323230186939349070635292401872375357164399581871019873438799005358938369571402670149802121818086292467422828157022922076746906543401224889672472407926969987100581290103199317858753663710862357656510507883714297115637342788911463535102712032765166518411726859837988672111837205085526346618740053"));
    RSAKey mockPrivateKey = new RSAKey(new BigInteger("89489425009274444368228545921773093919669586065884257445497854456487674839629818390934941973262879616797970608917283679875499331574161113854088813275488110588247193077582527278437906504015680623423550067240042466665654232383502922215493623289472138866445818789127946123407807725702626644091036502372545139713"), new BigInteger("145906768007583323230186939349070635292401872375357164399581871019873438799005358938369571402670149802121818086292467422828157022922076746906543401224889672472407926969987100581290103199317858753663710862357656510507883714297115637342788911463535102712032765166518411726859837988672111837205085526346618740053"));
    
    @Test
    public void encryptionWorksWithMockKeys() {
        RSA encrypt = new RSA(mockPublicKey);
        assertEquals("35052111338673026690212423937053328511880760811579981620642802346685810623109850235943049080973386241113784040794704193978215378499765413083646438784740952306932534945195080183861574225226218879827232453912820596886440377536082465681750074417459151485407445862511023472235560823053497791518928820272257787786",
                 encrypt.encrypt(new BigInteger("1976620216402300889624482718775150")));
    }

    @Test
    public void decryptionWorksWithMockKeys() {
        RSA decrypt = new RSA(mockPrivateKey);
        assertEquals("1976620216402300889624482718775150", decrypt.decrypt(new BigInteger("35052111338673026690212423937053328511880760811579981620642802346685810623109850235943049080973386241113784040794704193978215378499765413083646438784740952306932534945195080183861574225226218879827232453912820596886440377536082465681750074417459151485407445862511023472235560823053497791518928820272257787786")));
    }

    @Test
    public void fileEncryptionWorksWithMockKeys() throws IOException {
        SecureRandom rand = new SecureRandom();
        byte[] testmessage = new byte[500];
        rand.nextBytes(testmessage);
        RSA encrypt = new RSA(mockPublicKey);
        RSA decrypt = new RSA(mockPrivateKey);

        byte[] result = new byte[500];

        try {
            RandomAccessFile file1 = new RandomAccessFile(new File(Paths.get("./src/test/java/tiracryption/methods/testfile1").toUri()), "rw");
            file1.write(testmessage);
            file1.close();

            encrypt.encryptFile(Paths.get("./src/test/java/tiracryption/methods/testfile1"), Paths.get("./src/test/java/tiracryption/methods/testfile2"));
            decrypt.decryptFile(Paths.get("./src/test/java/tiracryption/methods/testfile2"), Paths.get("./src/test/java/tiracryption/methods/testfile3"));
            RandomAccessFile file2 = new RandomAccessFile(new File(Paths.get("./src/test/java/tiracryption/methods/testfile3").toUri()), "r");

            file2.readFully(result);

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        assertEquals(Arrays.toString(testmessage), Arrays.toString(result));

    }
    
    @Test
    public void fileEncryptionWorksWithGenerated512Keys() throws IOException {
        SecureRandom rand = new SecureRandom();
        RSAKeygen gen = new RSAKeygen(rand, 512);
        byte[] testmessage = new byte[1000];
        rand.nextBytes(testmessage);
        RSA encrypt = new RSA(gen.getPublic());
        RSA decrypt = new RSA(gen.getPrivate());

        byte[] result = new byte[1000];

        try {
            RandomAccessFile file1 = new RandomAccessFile(new File(Paths.get("./src/test/java/tiracryption/methods/testfile1").toUri()), "rw");
            file1.write(testmessage);
            file1.close();

            encrypt.encryptFile(Paths.get("./src/test/java/tiracryption/methods/testfile1"), Paths.get("./src/test/java/tiracryption/methods/testfile2"));
            decrypt.decryptFile(Paths.get("./src/test/java/tiracryption/methods/testfile2"), Paths.get("./src/test/java/tiracryption/methods/testfile3"));
            RandomAccessFile file2 = new RandomAccessFile(new File(Paths.get("./src/test/java/tiracryption/methods/testfile3").toUri()), "r");

            file2.readFully(result);

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        assertEquals(Arrays.toString(testmessage), Arrays.toString(result));

    }
    
    @Test
    public void fileEncryptionWorksWithGenerated2048Keys() throws IOException {
        SecureRandom rand = new SecureRandom();
        RSAKeygen gen = new RSAKeygen(rand, 2048);
        byte[] testmessage = new byte[10000];
        rand.nextBytes(testmessage);
        RSA encrypt = new RSA(gen.getPublic());
        RSA decrypt = new RSA(gen.getPrivate());

        byte[] result = new byte[10000];

        try {
            RandomAccessFile file1 = new RandomAccessFile(new File(Paths.get("./src/test/java/tiracryption/methods/testfile1").toUri()), "rw");
            file1.write(testmessage);
            file1.close();

            encrypt.encryptFile(Paths.get("./src/test/java/tiracryption/methods/testfile1"), Paths.get("./src/test/java/tiracryption/methods/testfile2"));
            decrypt.decryptFile(Paths.get("./src/test/java/tiracryption/methods/testfile2"), Paths.get("./src/test/java/tiracryption/methods/testfile3"));
            RandomAccessFile file2 = new RandomAccessFile(new File(Paths.get("./src/test/java/tiracryption/methods/testfile3").toUri()), "r");

            file2.readFully(result);

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        assertEquals(Arrays.toString(testmessage), Arrays.toString(result));

    }
}
