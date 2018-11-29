package tiracryption.ui;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;
import tiracryption.keys.RSAKey;
import tiracryption.keys.RSAKeygen;
import tiracryption.methods.RSA;
import tiracryption.methods.Rot13;
import tiracryption.methods.Rot47;

public class TextUI {

    private Scanner reader;
    private RSAKey privateKey = null;
    private RSAKey publicKey = null;

    public TextUI() {
        this.reader = new Scanner(System.in);
    }

    public TextUI(Scanner reader) {
        this.reader = reader;
    }

    public void start() throws IOException {

        System.out.println("Welcome to tiracryption!");

        while (true) {
            System.out.print("\nPlease select an encryption method to use:\n1 - ROT13\n2 - ROT47\n3 - RSA\n");
            if (privateKey == null) {
                System.out.println("4 - generate RSA keys\n");
            } else {
                System.out.println("4 - RSA with generated keys\n");
            }
            //System.out.println("d - debug");
            System.out.print("q - Quit Tiracryption\n\n");
            System.out.print("> ");
            String entry = reader.nextLine();

            if (entry.equals("1")) {
                rot13UI();
            } else if (entry.equals("2")) {
                rot47UI();
            } else if (entry.equals("3")) {
                rsaUI();
            } else if (entry.equals("4")) {
                if (privateKey == null) {
                    rsagenUI();

                } else {
                    rsaWithKeys();
                }

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

    private void rsaUI() {
        String key;
        String mod;
        System.out.print("Do you want to encrypt or decrypt? [e/d]: ");
        String selection = reader.nextLine();

        if (selection.equals("e")) {
            System.out.println("\nEnter public key:");
            System.out.print("> ");
            key = reader.nextLine();
            System.out.println("Enter modulus:");
            System.out.print("> ");
            mod = reader.nextLine();

            RSA rsa = new RSA(new RSAKey(new BigInteger(key), new BigInteger(mod)));

            System.out.println("Enter integer to be encrypted:");
            System.out.print("> ");
            String message = reader.nextLine();

            System.out.println("Encrypted integer:");
            System.out.println(rsa.encrypt(new BigInteger(message)));

        } else if (selection.equals("d")) {
            System.out.println("\nEnter private key:");
            System.out.print("> ");
            key = reader.nextLine();
            System.out.println("Enter modulus:");
            System.out.print("> ");
            mod = reader.nextLine();

            RSA rsa = new RSA(new RSAKey(new BigInteger(key), new BigInteger(mod)));

            System.out.println("Enter integer to be decrypted:");
            System.out.print("> ");
            String message = reader.nextLine();

            System.out.println("Decrypted integer:");
            System.out.println(rsa.decrypt(new BigInteger(message)));

        } else {
            return;
        }
    }

    private void rsagenUI() {
        RSAKeygen gen = new RSAKeygen(new SecureRandom(), 2048);
        publicKey = gen.getPublic();
        privateKey = gen.getPrivate();

        System.out.println("Private key: " + gen.getPrivate().getKey());
        System.out.println("");
        System.out.println("Public key: " + gen.getPublic().getKey());
        System.out.println("");
        System.out.println("mod: " + gen.getPublic().getMod());
        System.out.println("");
    }

    private void rsaWithKeys() {
        System.out.print("Do you want to encrypt or decrypt? [e/d]: ");
        String selection = reader.nextLine();

        if (selection.equals("e")) {

            RSA rsa = new RSA(publicKey);

            System.out.println("Enter integer to be encrypted:");
            System.out.print("> ");
            String message = reader.nextLine();

            System.out.println("Encrypted integer:");
            System.out.println(rsa.encrypt(new BigInteger(message)));

        } else if (selection.equals("d")) {

            RSA rsa = new RSA(privateKey);

            System.out.println("Enter integer to be decrypted:");
            System.out.print("> ");
            String message = reader.nextLine();

            System.out.println("Decrypted integer:");
            System.out.println(rsa.decrypt(new BigInteger(message)));

        } else {
            return;
        }
    }

    private void debug() throws IOException {
        System.out.println("Nothing to see here!");
    }
}
