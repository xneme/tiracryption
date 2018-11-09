package tiracryption.methods;

import java.math.BigInteger;
import tiracryption.keys.RSAKey;

public class RSA implements EncryptionMethod {
    
    private RSAKey key;

    public RSA(RSAKey key) {
        this.key = key;
    }

    @Override
    public String encrypt(String input) {
        BigInteger output = new BigInteger(input.getBytes()).modPow(key.getKey(), key.getMod());
        return output.toString();
    }
    
    public String encrypt(BigInteger input) {
        BigInteger output = input.modPow(key.getKey(), key.getMod());
        return output.toString();
    }

    @Override
    public String decrypt(String input) {
        return this.encrypt(input);
    }
    
    public String decrypt(BigInteger input) {
        return this.encrypt(input);
    }
}
