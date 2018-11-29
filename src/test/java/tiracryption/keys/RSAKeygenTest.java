package tiracryption.keys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class RSAKeygenTest {

    private RSAKeygen keygen = new RSAKeygen(new SecureRandom(), 2048);

    @Test
    public void generatedKeyPairHasSameMod() {
        assertEquals(true, keygen.getPrivate().getMod().equals(keygen.getPublic().getMod()));
    }

    @Test
    public void generatedKeyPairWorks() {
        byte[] message = "Tiracryption rocks!".getBytes();

        BigInteger encryptedMessage = new BigInteger(message).modPow(keygen.getPublic().getKey(), keygen.getPublic().getMod());
        BigInteger decryptedMessage = encryptedMessage.modPow(keygen.getPrivate().getKey(), keygen.getPrivate().getMod());
        assertEquals(Arrays.toString(message), Arrays.toString(decryptedMessage.toByteArray()));
    }

    @Test
    public void writingToFileWorks() {

        keygen.generate();
        Path privateKeyPath = Paths.get("./src/test/java/tiracryption/keys/testRSAkey");
        Path publicKeyPath = Paths.get("./src/test/java/tiracryption/keys/testRSAkey.pub");

        try {
            keygen.writeKeyFiles(privateKeyPath);
        } catch (IOException ex) {
            System.out.println("Error writing key files: " + ex.getMessage());
        }

        try {
            File privateKeyfile = new File(privateKeyPath.toUri());
            File publicKeyFile = new File(publicKeyPath.toUri());
            Scanner filereader = new Scanner(privateKeyfile);
            filereader.nextLine();
            BigInteger readPrivateKey = new BigInteger(filereader.nextLine());
            BigInteger readPrivateMod = new BigInteger(filereader.nextLine());
            
            filereader = new Scanner(publicKeyFile);
            
            filereader.nextLine();
            BigInteger readPublicKey = new BigInteger(filereader.nextLine());
            BigInteger readPublicMod = new BigInteger(filereader.nextLine());
            
            assertEquals(keygen.getPrivate().getKey(), readPrivateKey);
            assertEquals(keygen.getPrivate().getMod(), readPrivateMod);
            assertEquals(keygen.getPublic().getKey(), readPublicKey);
            assertEquals(keygen.getPublic().getMod(), readPublicMod);
            
        } catch (FileNotFoundException ex) {
            System.out.println("Error reading file: " + ex.getMessage());
        }
    }
}
