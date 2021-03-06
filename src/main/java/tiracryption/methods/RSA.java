package tiracryption.methods;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import tiracryption.keys.RSAKey;
import tiracryption.ui.TextUI;

public class RSA {

    private RSAKey key;

    public RSA(RSAKey key) {
        this.key = key;
    }

    public String encrypt(BigInteger input) {
        BigInteger output = input.modPow(key.getKey(), key.getMod());
        return output.toString();
    }

    public String decrypt(BigInteger input) {
        return this.encrypt(input);
    }

    public byte[] encrypt(byte[] input) {
        BigInteger data = new BigInteger(addPreEncryptPaddign(input));
        byte[] output = data.modPow(key.getKey(), key.getMod()).toByteArray();
        return addPostEncryptPadding(output);
    }

    public byte[] decrypt(byte[] input) {
        BigInteger data = new BigInteger(input);

        return removePadding(data.modPow(key.getKey(), key.getMod()).toByteArray());
    }

    public void encryptFile(Path input, Path output) {
        try {
            RandomAccessFile inputFile = new RandomAccessFile(new File(input.toUri()), "r");
            RandomAccessFile outputFile = new RandomAccessFile(new File(output.toUri()), "rw");

            int modLength = this.key.modByteLength();
            byte[] block = new byte[modLength - 2];
            int count;

            while ((count = inputFile.read(block)) > -1) {
                if (count < block.length) {
                    outputFile.write(encrypt(Arrays.copyOf(block, count)));
                } else {
                    outputFile.write(encrypt(block));
                }

            }
            
            inputFile.close();
            outputFile.close();
            
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void decryptFile(Path input, Path output) {
        try {
            File encryptedFile = new File(input.toUri());
            RandomAccessFile inputFile = new RandomAccessFile(encryptedFile, "r");
            RandomAccessFile outputFile = new RandomAccessFile(new File(output.toUri()), "rw");

            int modLength = this.key.getMod().toByteArray().length;
            byte[] block = new byte[modLength];
            int count;

            while ((count = inputFile.read(block)) > -1) {
                if (count < modLength) {
                    System.out.println("\n Error: Encrypted file length not a multiple of mod");
                    outputFile.write(decrypt(Arrays.copyOf(block, count)));
                } else {
                    outputFile.write(decrypt(block));
                }

            }
            
            inputFile.close();
            outputFile.close();
            encryptedFile.deleteOnExit();
            
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private byte[] addPreEncryptPaddign(byte[] input) {
        byte[] padded = new byte[input.length + 1];
        padded[0] = (byte) 0x01;

        for (int i = 0; i < input.length; i++) {
            padded[i + 1] = input[i];
        }

        return padded;
    }

    private byte[] addPostEncryptPadding(byte[] input) {
        int paddingAmount = this.key.modByteLength() - input.length;
        
        if (paddingAmount > 0) {
            byte[] padded = new byte[this.key.modByteLength()];

            for (int i = 0; i < input.length; i++) {
                padded[i + paddingAmount] = input[i];
            }

            return padded;
        }
        
        return input;
    }

    private byte[] removePadding(byte[] input) {
        return Arrays.copyOfRange(input, 1, input.length);
    }
}
