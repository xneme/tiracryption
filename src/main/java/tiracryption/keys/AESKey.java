package tiracryption.keys;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import tiracryption.structures.SBox;

/**
 * Key used in AES encryption, expands itself to multiple round keys.
 */
public class AESKey {

    private byte[] key;
    private byte[][][] roundKeys;

    /**
     * Create AES key using random bytes
     * 
     * @param key byte array of length 16, 24 or 32
     */
    public AESKey(byte[] key) {
        this.key = key;
        this.roundKeys = this.generateRoundKeys();
    }

    /**
     * Create AES key from file
     * 
     * @param path Path to file containing 16, 24 or 32 byte key
     * @throws IOException
     */
    public AESKey(Path path) throws IOException {
        this.key = Files.readAllBytes(path);
        this.roundKeys = this.generateRoundKeys();
    }

    /**
     * Get key as byte array
     * 
     * @return
     */
    public byte[] getKey() {
        return key;
    }

    /**
     * Get round keys expanded from key
     * 11 round keys for AES-128,
     * 13 round keys for AES-192 and
     * 15 round keys for AES-256
     * 
     * @return 
     */
    public byte[][][] getRoundKeys() {
        return roundKeys;
    }

    private byte[][][] generateRoundKeys() {
        if (key.length == 16) {
            return this.expandKey(4, 11); // AES-128
        } else if (key.length == 24) {
            return this.expandKey(6, 13); // AES-192
        } else if (key.length == 32) {
            return this.expandKey(8, 15); // AES-256
        }

        return null;
    }

    private byte[][][] expandKey(int words, int rounds) {
        byte[] rc = {(byte) 0x01, (byte) 0x02, (byte) 0x04, (byte) 0x08, (byte) 0x10, (byte) 0x20, (byte) 0x40, (byte) 0x80, (byte) 0x1B, (byte) 0x36};
        SBox sbox = new SBox();
        byte[][] keyWords = this.splitKey();
        byte[][] expandedWords = new byte[rounds * 4][4];

        for (int i = 0; i < rounds * 4; i++) {
            if (i < words) {
                expandedWords[i] = keyWords[i];
            } else if (i % words == 0) {
                byte[] roundConstant = {rc[i / words - 1], (byte) 0x00, (byte) 0x00, (byte) 0x00};
                expandedWords[i] = this.xor(this.xor(expandedWords[i - words], this.rotWord(sbox.forward(expandedWords[i - 1]))), roundConstant);
            } else if (words > 6 && i % words == 4) {
                expandedWords[i] = this.xor(expandedWords[i - words], sbox.forward(expandedWords[i - 1]));
            } else {
                expandedWords[i] = this.xor(expandedWords[i - words], expandedWords[i - 1]);
            }
        }

        return this.groupKeys(expandedWords);
    }

    private byte[][] splitKey() {
        byte[][] keyAsWords = new byte[key.length / 4][4];

        for (int i = 0; i < key.length; i++) {
            keyAsWords[i / 4][i % 4] = key[i];
        }

        return keyAsWords;
    }

    private byte[][][] groupKeys(byte[][] expandedWords) {
        byte[][][] groupedKeys = new byte[expandedWords.length / 4][4][4];

        for (int i = 0; i < expandedWords.length; i++) {
            for (int j = 0; j < 4; j++) {
                groupedKeys[i / 4][j][i % 4] = expandedWords[i][j];
            }

        }

        return groupedKeys;
    }

    private byte[] rotWord(byte[] word) {
        byte[] newWord = new byte[4];

        newWord[0] = word[1];
        newWord[1] = word[2];
        newWord[2] = word[3];
        newWord[3] = word[0];

        return newWord;
    }

    private byte[] xor(byte[] b1, byte[] b2) {
        byte[] output = new byte[b1.length];

        for (int i = 0; i < b1.length; i++) {
            output[i] = (byte) (b1[i] ^ b2[i]);
        }

        return output;
    }
}
