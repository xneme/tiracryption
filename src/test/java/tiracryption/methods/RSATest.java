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
import tiracryption.keys.RSAKeygen;

public class RSATest {

    private RSAKeygen gen = new RSAKeygen();

    @Test
    public void encryptionWorks() {
        RSA encrypt = new RSA(gen.getPublic());
        assertEquals("35052111338673026690212423937053328511880760811579981620642802346685810623109850235943049080973386241113784040794704193978215378499765413083646438784740952306932534945195080183861574225226218879827232453912820596886440377536082465681750074417459151485407445862511023472235560823053497791518928820272257787786",
                 encrypt.encrypt(new BigInteger("1976620216402300889624482718775150")));
    }

    @Test
    public void decryptionWorks() {
        RSA decrypt = new RSA(gen.getPrivate());
        assertEquals("1976620216402300889624482718775150", decrypt.decrypt(new BigInteger("35052111338673026690212423937053328511880760811579981620642802346685810623109850235943049080973386241113784040794704193978215378499765413083646438784740952306932534945195080183861574225226218879827232453912820596886440377536082465681750074417459151485407445862511023472235560823053497791518928820272257787786")));
    }

    @Test
    public void fileEncryptionWorks() throws IOException {
        SecureRandom rand = new SecureRandom();
        byte[] testmessage = new byte[500];
        rand.nextBytes(testmessage);
        RSA encrypt = new RSA(gen.getPublic());
        RSA decrypt = new RSA(gen.getPrivate());

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
}
