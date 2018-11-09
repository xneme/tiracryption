package tiracryption.keys;

import java.math.BigInteger;

public class RSAKey {

    private BigInteger key;
    private BigInteger mod;

    public RSAKey(BigInteger key, BigInteger mod) {
        this.key = key;
        this.mod = mod;
    }

    public BigInteger getKey() {
        return key;
    }

    public BigInteger getMod() {
        return mod;
    }

}
