package tiracryption.keys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Key used in RSA encryption.
 */
public class RSAKey {

    private BigInteger key;
    private BigInteger mod;

    /**
     * Create key from given values
     * 
     * @param key Key value as BigInteger
     * @param mod Modulus used with key as BigInteger
     */
    public RSAKey(BigInteger key, BigInteger mod) {
        this.key = key;
        this.mod = mod;
    }

    /**
     * Create RSA key from file
     * 
     * @param path Path to key file
     */
    public RSAKey(Path path) {
        try {
            File keyfile = new File(path.toUri());
            Scanner filereader = new Scanner(keyfile);

            String keytype = filereader.nextLine();
            if (keytype.equals("tiracryption public key:") || keytype.equals("tiracryption private key:")) {
                this.key = new BigInteger(filereader.nextLine());
                this.mod = new BigInteger(filereader.nextLine());
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("Error reading file: " + ex.getMessage());
        }
    }

    /**
     * Get the key as BigInteger
     * 
     * @return
     */
    public BigInteger getKey() {
        return key;
    }

    /**
     * Get the modulus as BigInteger
     * 
     * @return
     */
    public BigInteger getMod() {
        return mod;
    }

    /**
     * Modulus length in bytes, chunks to be encrypted cannot exceed this
     * 
     * @return
     */
    public int modByteLength() {
        return mod.toByteArray().length;
    }
}
