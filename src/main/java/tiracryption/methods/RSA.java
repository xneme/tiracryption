package tiracryption.methods;

import java.math.BigInteger;
import java.util.Arrays;
import tiracryption.keys.RSAKey;

public class RSA implements EncryptionMethod {
    
    private RSAKey key;

    public RSA(RSAKey key) {
        this.key = key;
    }
    
    // STRINGS ARE NOT WORKING YET!
    @Override
    public String encrypt(String input) {
        System.out.println("Input bytearray: " + Arrays.toString(input.getBytes()));
        BigInteger output = new BigInteger(input.getBytes()).modPow(key.getKey(), key.getMod());
        System.out.println("Output bytearray: " + Arrays.toString(output.toByteArray()));
        System.out.println(new String(output.toByteArray()));
        return output.toString();
    }
    
    @Override
    public String decrypt(String input) {
        System.out.println("Input bytearray: " + Arrays.toString(input.getBytes()));
        BigInteger output = new BigInteger(input.getBytes()).modPow(key.getKey(), key.getMod());
        System.out.println("Output bytearray: " + Arrays.toString(output.toByteArray()));
        return new String(output.toByteArray());
    }
    // STRINGS ARE NOT WORKING YET!
    
    public String encrypt(BigInteger input) {
        BigInteger output = input.modPow(key.getKey(), key.getMod());
        return output.toString();
    }
    
    public String decrypt(BigInteger input) {
        return this.encrypt(input);
    }
    
    public byte[] encrypt(byte[] input) {
        BigInteger output = new BigInteger(input);
        output = output.modPow(key.getKey(), key.getMod());
        return output.toByteArray();
    }
    
    public byte[] decrypt(byte[] input) {
        return this.encrypt(input);
    }
}
