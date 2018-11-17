package tiracryption.keys;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.Scanner;

public class RSAKey {

    private BigInteger key;
    private BigInteger mod;

    public RSAKey(BigInteger key, BigInteger mod) {
        this.key = key;
        this.mod = mod;
    }
    
    public RSAKey(Path path) throws IOException {
        File keyfile = new File(path.toUri());
        Scanner filereader = new Scanner(keyfile);
        String keytype = filereader.nextLine();
        if (keytype.equals("tiracryption public key:") || keytype.equals("tiracryption private key:")) {
            this.key = new BigInteger(filereader.nextLine());
            this.mod = new BigInteger(filereader.nextLine());
        }
        
    }

    public BigInteger getKey() {
        return key;
    }

    public BigInteger getMod() {
        return mod;
    }

    public int modByteLength() {
        return mod.toByteArray().length;
    }
}
