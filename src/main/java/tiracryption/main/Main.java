package tiracryption.main;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.SecureRandom;
import tiracryption.keys.AESKey;
import tiracryption.keys.AESKeygen;
import tiracryption.keys.RSAKey;
import tiracryption.keys.RSAKeygen;
import tiracryption.methods.AES;
import tiracryption.methods.RSA;
import tiracryption.structures.TiraRandom;
import tiracryption.ui.TextUI;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Use command line arguments for actual encrypting, see manual for details!\n");
            TextUI ui = new TextUI();
            ui.start();

        } else if (args.length == 3 && args[0].equals("RSAencrypt")) {
            System.out.print("reading key.. ");
            RSA rsa = new RSA(new RSAKey(Paths.get(args[1])));
            System.out.println("done.");

            System.out.print("encrypting.. ");

            rsa.encryptFile(Paths.get(args[2]), Paths.get(args[2] + ".encrypted"));
            System.out.println("done.");

        } else if (args.length == 3 && args[0].equals("RSAdecrypt")) {
            System.out.print("reading key.. ");
            RSA rsa = new RSA(new RSAKey(Paths.get(args[1])));
            System.out.println("done.");

            System.out.print("decrypting.. ");

            if (args[2].contains("encrypted")) {
                rsa.decryptFile(Paths.get(args[2]), Paths.get(args[2].replace("encrypted", "decrypted")));
            } else {
                rsa.decryptFile(Paths.get(args[2]), Paths.get(args[2] + ".decrypted"));
            }

            System.out.println("done.");

        } else if (args.length == 2 && args[0].equals("AESencrypt")) {
            System.out.print("generating key.. ");
            AESKeygen gen = new AESKeygen(new TiraRandom());
            gen.writeKeyFile(Paths.get(args[1] + ".key"));
            System.out.println("done.");

            System.out.print("reading key.. ");
            AES aes = new AES(new AESKey(Paths.get(args[1] + ".key")));
            System.out.println("done.");

            System.out.print("encrypting.. ");
            aes.encryptFile(Paths.get(args[1]), Paths.get(args[1] + ".encrypted"));
            System.out.println("done.");

        } else if (args.length == 2 && args[0].equals("AESdecrypt")) {
            System.out.print("reading key.. ");
            AES aes;
            if (args[1].contains("encrypted")) {
                aes = new AES(new AESKey(Paths.get(args[1].replace("encrypted", "key"))));
            } else {
                aes = new AES(new AESKey(Paths.get(args[1] + ".key")));
            }
            System.out.println("done.");

            System.out.print("decrypting.. ");

            if (args[1].contains("encrypted")) {
                aes.decryptFile(Paths.get(args[1]), Paths.get(args[1].replace("encrypted", "decrypted")));
            } else {
                aes.decryptFile(Paths.get(args[1]), Paths.get(args[1] + ".decrypted"));
            }

            System.out.println("done.");

        } else if (args.length > 0 && args[0].equals("RSAgenerate")) {

            RSAKeygen gen = new RSAKeygen(new SecureRandom(), 2048);
            System.out.print("generating key.. ");
            if (args.length > 1) {
                gen.writeKeyFiles(Paths.get(args[1]));
            } else {
                gen.writeKeyFiles(Paths.get("keys/rsakey"));
            }
            System.out.println("done.");
        }
    }
}
