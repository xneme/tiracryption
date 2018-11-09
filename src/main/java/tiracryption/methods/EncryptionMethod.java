package tiracryption.methods;

public interface EncryptionMethod {
    
    String encrypt(String input);
    
    String decrypt(String input);
    
}
