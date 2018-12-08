package tiracryption.ui;

import java.io.IOException;
import java.util.Scanner;
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

    public void start() throws IOException {

        System.out.println("Welcome to tiracryption!");
        
        while (true) {
            System.out.print("\nPlease select an encryption method to use:\n1 - ROT13\n2 - ROT47\n");
            //System.out.println("d - debug");
            System.out.print("q - Quit Tiracryption\n\n");
            System.out.print("> ");
            String entry = reader.nextLine();

            if (entry.equals("1")) {
                rot13UI();
            } else if (entry.equals("2")) {
                rot47UI();
            } else if (entry.equals("q")) {
                return;
            } else if (entry.equals("d")) {
                debug();
            } else {
                System.out.print("Invalid selection.\n> ");
            }
        }

    }

    private void rot13UI() {
        Rot13 encryptor = new Rot13();
        System.out.println("\nEnter message to be encrypted/decrypted:");
        System.out.print("> ");
        String message = reader.nextLine();

        System.out.println("\nEncrypted/decrypted message:");
        System.out.println(encryptor.encrypt(message));
    }
    
    private void rot47UI() {
        Rot47 encryptor = new Rot47();
        System.out.println("\nEnter message to be encrypted/decrypted:");
        System.out.print("> ");
        String message = reader.nextLine();

        System.out.println("\nEncrypted/decrypted message:");
        System.out.println(encryptor.encrypt(message));
    }

    private void debug() throws IOException {
        System.out.println("Nothing to see here!");
    }
}