package tiracryption.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import tiracryption.keys.RSAKey;
import tiracryption.keys.RSAKeygen;
import tiracryption.methods.RSA;
import tiracryption.ui.TextUI;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            TextUI ui = new TextUI();
            ui.start();

        } else if (args.length == 3 && args[0].equals("encrypt")) {
            System.out.print("reading key.. ");
            RSA rsa = new RSA(new RSAKey(Paths.get(args[1])));
            System.out.println("done.");

            System.out.print("encrypting.. ");

            rsa.encrypt(Paths.get(args[2]), Paths.get(args[2] + ".encrypted"));
            System.out.println("done.");

        } else if (args.length == 3 && args[0].equals("decrypt")) {
            System.out.print("reading key.. ");
            RSA rsa = new RSA(new RSAKey(Paths.get(args[1])));
            System.out.println("done.");

            System.out.print("decrypting.. ");

            if (args[2].contains("encrypted")) {
                rsa.decrypt(Paths.get(args[2]), Paths.get(args[2].replace("encrypted", "decrypted")));
            } else {
                rsa.decrypt(Paths.get(args[2]), Paths.get(args[2] + ".decrypted"));
            }

            System.out.println("done.");

        } else if (args.length > 0 && args[0].equals("generate")) {

            RSAKeygen gen = new RSAKeygen();
            if (args.length > 1) {
                gen.writeKeyFiles(Paths.get(args[1]));
            } else {
                gen.writeKeyFiles(Paths.get("keys/rsakey"));
            }
        }
    }
}
