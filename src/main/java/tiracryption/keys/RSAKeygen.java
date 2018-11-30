package tiracryption.keys;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;

/**
 * Generator for RSA key pairs
 */
public class RSAKeygen {
    
    private BigInteger mod;
    private BigInteger privateKey;
    private BigInteger publicKey;
    private SecureRandom random;
    private int bitLength;

    /**
     * Creates new RSA key generator
     * 
     * @param random random for randomness
     * @param bitLength Key length in bits, usually 1024, 2048 or 4096
     */
    public RSAKeygen(SecureRandom random, int bitLength) {
        this.random = random;
        this.bitLength = bitLength;
        this.generate();
    }

    /**
     * Generates new public/private RSA key pair
     */
    public void generate() {
        BigInteger p = BigInteger.probablePrime(bitLength / 2, random);
        BigInteger q = BigInteger.probablePrime(bitLength / 2, random);
        this.mod = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
        this.publicKey = new BigInteger(phi.bitLength(), random);
        
        while (this.publicKey.compareTo(BigInteger.ONE) <= 0
                || this.publicKey.compareTo(phi) >= 0
                || !this.publicKey.gcd(phi).equals(BigInteger.ONE)) {
            this.publicKey = new BigInteger(phi.bitLength(), random);
        }
        
        this.privateKey = this.publicKey.modInverse(phi);
    }

    /**
     * Get public key
     * 
     * @return
     */
    public RSAKey getPublic() {
        return new RSAKey(publicKey, mod);
    }

    /**
     * Get private key
     * 
     * @return
     */
    public RSAKey getPrivate() {
        return new RSAKey(privateKey, mod);
    }

    /**
     * Writes both public and private keys to files.
     * Private key is written to given path, 
     * public key is written to same path, but with filename ending in .pub
     * 
     * @param path Path for private key
     * @throws IOException
     */
    public void writeKeyFiles(Path path) throws IOException {
        if (path.getParent() != null) {
            File directory = new File(path.getParent().toUri());
            directory.mkdirs();
        }
        Files.write(path, String.format("tiracryption private key:\n%s\n%s\n", this.privateKey.toString(), this.mod.toString()).getBytes());
        Files.write(path.resolveSibling(path.getFileName() + ".pub"), String.format("tiracryption public key:\n%s\n%s\n", this.publicKey.toString(), this.mod.toString()).getBytes());
    }

}
