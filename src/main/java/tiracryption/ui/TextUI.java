package tiracryption.ui;

import java.util.Scanner;
import tiracryption.methods.EncryptionMethod;
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
            System.out.print("\nPlease select an encryption method to use:\n1 - ROT13\n2 - ROT47\nq - Quit Tiracryption\n\n");
            System.out.print("> ");
            String entry = reader.nextLine();
            if (entry.equals("1")) {
                encryptor = new Rot13();
                break;
            } else if (entry.equals("2")) {
                encryptor = new Rot47();
                break;
            } else if (entry.equals("q")) {
                return;
            } else {
                System.out.print("Invalid selection.\n> ");
            }
        }
        
        System.out.println("\nEnter message to be encrypted/decrypted:");
        System.out.print("> ");
        String message = reader.nextLine();

        System.out.println("\nEncrypted/decrypted message:");
        System.out.println(encryptor.encrypt(message));
    }
}
