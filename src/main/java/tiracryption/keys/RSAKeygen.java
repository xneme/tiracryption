package tiracryption.keys;

import java.math.BigInteger;

public class RSAKeygen {

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger privateKey;
    private BigInteger publicKey;

    public RSAKeygen(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
        this.n = p.multiply(q);
        this.generate();
    }

    public RSAKeygen() {
        this.generate();
    }
    
    private void generate() {
        this.privateKey = BigInteger.valueOf(11);
        this.publicKey = BigInteger.valueOf(5);
        this.n = BigInteger.valueOf(14);
    }

    public RSAKey getPublic() {
        return new RSAKey(publicKey, n);
    }

    public RSAKey getPrivate() {
        return new RSAKey(privateKey, n);
    }

}
