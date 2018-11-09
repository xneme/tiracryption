package tiracryption.methods;

public class Rot13 implements EncryptionMethod {
    
    @Override
    public String encrypt(String input) {
        char c[] = input.toCharArray();
        
        for (int i = 0; i < c.length; i++) {
            if (c[i] >= 'a' && c[i] <= 'z') {
                c[i] = (char) ('a' + (c[i] - 'a' + 13) % 26);
            }
            if (c[i] >= 'A' && c[i] <= 'Z') {
                c[i] = (char) ('A' + (c[i] - 'A' + 13) % 26);
            }
        }
        return new String(c);
    }
    
    @Override
    public String decrypt(String input) {
        return encrypt(input);
    }
}
 