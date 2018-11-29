package tiracryption.keys;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;

public class RSAKeygen {
    
    private BigInteger mod;
    private BigInteger privateKey;
    private BigInteger publicKey;
    private SecureRandom random;
    private int bitLength;

    public RSAKeygen(SecureRandom random, int bitLength) {
        this.random = random;
        this.bitLength = bitLength;
        this.generate();
    }

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

    public RSAKey getPublic() {
        return new RSAKey(publicKey, mod);
    }

    public RSAKey getPrivate() {
        return new RSAKey(privateKey, mod);
    }

    public void writeKeyFiles(Path path) throws IOException {
        if (path.getParent() != null) {
            File directory = new File(path.getParent().toUri());
            directory.mkdirs();
        }
        Files.write(path, String.format("tiracryption private key:\n%s\n%s\n", this.privateKey.toString(), this.mod.toString()).getBytes());
        Files.write(path.resolveSibling(path.getFileName() + ".pub"), String.format("tiracryption public key:\n%s\n%s\n", this.publicKey.toString(), this.mod.toString()).getBytes());
    }

}
