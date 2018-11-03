package tiracryption.ui;

import java.util.Scanner;
import tiracryption.methods.RotN;


public class TextUI {
    private Scanner reader;
    private RotN encryptor;
    
    public TextUI() {
        this.reader = new Scanner(System.in);
        this.encryptor = new RotN();
    }

    public TextUI(Scanner reader) {
        this.reader = reader;
        this.encryptor = new RotN();
    }
    
    public void start() {
        System.out.print("Welcome to tiracryption!\nPlease enter message to be encrypted or decrypted with ROT13.\n");
        System.out.print("> ");
        String message = reader.nextLine();
        System.out.println("Encrypted/decrypted message:");
        System.out.println(encryptor.encrypt(message));
    }
}
