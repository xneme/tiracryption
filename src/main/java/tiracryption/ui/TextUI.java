package tiracryption.ui;

import java.math.BigInteger;
import java.util.Scanner;
import tiracryption.keys.RSAKey;
import tiracryption.keys.RSAKeygen;
import tiracryption.methods.EncryptionMethod;
import tiracryption.methods.RSA;
import tiracryption.methods.Rot13;
import tiracryption.methods.Rot47;

public class TextUI {

    private Scanner reader;

    public TextUI() {
        this.reader = new Scanner(System.in);
    }

    public TextUI(Scanner reader) {
        this.reader = reader;
    }

    public void start() {
        EncryptionMethod encryptor;
        
        System.out.println("Welcome to tiracryption!");
        
        while (true) {
            System.out.print("\nPlease select an encryption method to use:\n1 - ROT13\n2 - ROT47\n3 - RSA\n4 - generate RSA keys\nq - Quit Tiracryption\n\n");
            System.out.print("> ");
            String entry = reader.nextLine();
            if (entry.equals("1")) {
                rotUI(new Rot13());
            } else if (entry.equals("2")) {
                rotUI(new Rot47());
            } else if (entry.equals("3")) {
                rsaUI();
            } else if (entry.equals("4")) {
                rsagenUI();
            } else if (entry.equals("q")) {
                return;
            } else {
                System.out.print("Invalid selection.\n> ");
            }
        }
        
        
    }
    
    private void rotUI(EncryptionMethod encryptor) {
        System.out.println("\nEnter message to be encrypted/decrypted:");
        System.out.print("> ");
        String message = reader.nextLine();

        System.out.println("\nEncrypted/decrypted message:");
        System.out.println(encryptor.encrypt(message));
    }
    
    private void rsaUI() {
        System.out.println("\nEnter key (public for encrypting, private for decrypting):");
        System.out.print("> ");
        String key = reader.nextLine();
        System.out.println("Enter modulus:");
        System.out.print("> ");
        RSA rsa = new RSA(new RSAKey(new BigInteger(key), new BigInteger(reader.nextLine())));
        System.out.println("Enter message to be encrypted/decrypted in integer form:");
        System.out.print("> ");
        String message = reader.nextLine();

        System.out.println("Encrypted/decrypted message:");
        System.out.println(rsa.encrypt(new BigInteger(message)));
    }
    
    private void rsagenUI() {
        RSAKeygen gen = new RSAKeygen();
        System.out.println("Private key: " + gen.getPrivate().getKey());
        System.out.println("mod: " + gen.getPrivate().getMod());
        System.out.println("");
        System.out.println("Public key: " + gen.getPublic().getKey());
        System.out.println("mod: " + gen.getPublic().getMod());
        System.out.println("");
    }
}
