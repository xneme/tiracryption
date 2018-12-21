package tiracryption.methods;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Arrays;
import tiracryption.keys.AESKey;
import tiracryption.structures.MixColumnLookup;
import tiracryption.structures.SBox;

/**
 * AES encryption algorithm.
 */
public class AES {

    private AESKey key;
    private SBox sbox;
    private MixColumnLookup mc;

    public AES(AESKey key) {
        this.key = key;
        this.sbox = new SBox();
        this.mc = new MixColumnLookup();
    }

    /**
     * Encrypts a 16 byte block
     *
     * @param input byte[], length 16.
     * @return
     */
    public byte[] encrypt(byte[] input) {
        byte[][] state = this.splitBytesToState(input);
        byte[][][] roundKeys = this.key.getRoundKeys();
        state = addRoundKey(state, roundKeys[0]);

        for (int round = 1; round < roundKeys.length - 1; round++) {
            state = subBytes(state);
            state = shiftRows(state);
            state = mixColumns(state);
            state = addRoundKey(state, roundKeys[round]);
        }

        state = subBytes(state);
        state = shiftRows(state);
        state = addRoundKey(state, roundKeys[roundKeys.length - 1]);

        return mergeStateToBytes(state);
    }

    /**
     * Decrypts a 16 byte block
     *
     * @param input byte[], length 16.
     * @return
     */
    public byte[] decrypt(byte[] input) {
        byte[][] state = this.splitBytesToState(input);
        byte[][][] roundKeys = this.key.getRoundKeys();

        state = addRoundKey(state, roundKeys[roundKeys.length - 1]);

        for (int round = roundKeys.length - 2; round > 0; round--) {
            state = invShiftRows(state);
            state = invSubBytes(state);
            state = addRoundKey(state, roundKeys[round]);
            state = invMixColumns(state);
        }

        state = invShiftRows(state);
        state = invSubBytes(state);
        state = addRoundKey(state, roundKeys[0]);

        return this.mergeStateToBytes(state);
    }

    /**
     * Encrypts a file.
     * @param input Path to input file.
     * @param output Path to output file.
     */
    public void encryptFile(Path input, Path output) {
        try {
            RandomAccessFile inputFile = new RandomAccessFile(new File(input.toUri()), "r");
            RandomAccessFile outputFile = new RandomAccessFile(new File(output.toUri()), "rw");

            byte[] block = new byte[16];
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

    /**
     * Decrypts a file.
     * @param input Path to input file.
     * @param output Path to output file.
     */
    public void decryptFile(Path input, Path output) {
        try {
            File encryptedFile = new File(input.toUri());
            RandomAccessFile inputFile = new RandomAccessFile(encryptedFile, "r");
            RandomAccessFile outputFile = new RandomAccessFile(new File(output.toUri()), "rw");

            byte[] block = new byte[16];
            int count;

            while ((count = inputFile.read(block)) > -1) {
                if (count < 16) {
                    System.out.println("\n Error: Encrypted file corrupted");
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
    
    /**
     * Splits a 16 byte array to 4x4 byte array.
     * 
     * @param input 16 byte array.
     * @return 4x4 byte array.
     */
    private byte[][] splitBytesToState(byte[] input) {
        byte[][] newState = new byte[4][4];

        for (int i = 0; i < input.length; i++) {
            newState[i % 4][i / 4] = input[i];
        }

        return newState;
    }

    /**
     * Merges a 4x4 byte array to 16 byte array.
     * 
     * @param input 4x4 byte array.
     * @return 16 byte array.
     */
    private byte[] mergeStateToBytes(byte[][] input) {
        byte[] newBytes = new byte[16];

        for (int i = 0; i < 16; i++) {
            newBytes[i] = input[i % 4][i / 4];
        }

        return newBytes;
    }

    /**
     * Performs s-box substitution to a 4x4 byte array.
     * 
     * @param state 4x4 byte array.
     * @return 4x4 byte array.
     */
    private byte[][] subBytes(byte[][] state) {
        byte[][] newState = state;

        for (int i = 0; i < 4; i++) {
            newState[i] = this.sbox.forward(state[i]);
        }

        return newState;
    }

    private byte[][] invSubBytes(byte[][] state) {
        byte[][] newState = state;

        for (int i = 0; i < 4; i++) {
            newState[i] = this.sbox.reverse(state[i]);
        }

        return newState;
    }

    private byte[][] shiftRows(byte[][] state) {
        byte[][] newState = new byte[4][4];

        newState[0] = state[0];

        newState[1][0] = state[1][1];
        newState[1][1] = state[1][2];
        newState[1][2] = state[1][3];
        newState[1][3] = state[1][0];

        newState[2][0] = state[2][2];
        newState[2][1] = state[2][3];
        newState[2][2] = state[2][0];
        newState[2][3] = state[2][1];

        newState[3][0] = state[3][3];
        newState[3][1] = state[3][0];
        newState[3][2] = state[3][1];
        newState[3][3] = state[3][2];

        return newState;
    }

    private byte[][] invShiftRows(byte[][] state) {
        byte[][] newState = new byte[4][4];

        newState[0] = state[0];

        newState[1][0] = state[1][3];
        newState[1][1] = state[1][0];
        newState[1][2] = state[1][1];
        newState[1][3] = state[1][2];

        newState[2][0] = state[2][2];
        newState[2][1] = state[2][3];
        newState[2][2] = state[2][0];
        newState[2][3] = state[2][1];

        newState[3][0] = state[3][1];
        newState[3][1] = state[3][2];
        newState[3][2] = state[3][3];
        newState[3][3] = state[3][0];

        return newState;
    }

    private byte[][] mixColumns(byte[][] state) {
        byte[][] newState = new byte[4][4];

        for (int i = 0; i < 4; i++) {
            newState[0][i] = (byte) (mc.mul2[(state[0][i] & 0xF0) >>> 4][state[0][i] & 0xF] ^ mc.mul3[(state[1][i] & 0xF0) >>> 4][state[1][i] & 0xF] ^ state[2][i] ^ state[3][i]);
            newState[1][i] = (byte) (state[0][i] ^ mc.mul2[(state[1][i] & 0xF0) >>> 4][state[1][i] & 0xF] ^ mc.mul3[(state[2][i] & 0xF0) >>> 4][state[2][i] & 0xF] ^ state[3][i]);
            newState[2][i] = (byte) (state[0][i] ^ state[1][i] ^ mc.mul2[(state[2][i] & 0xF0) >>> 4][state[2][i] & 0xF] ^ mc.mul3[(state[3][i] & 0xF0) >>> 4][state[3][i] & 0xF]);
            newState[3][i] = (byte) (mc.mul3[(state[0][i] & 0xF0) >>> 4][state[0][i] & 0xF] ^ state[1][i] ^ state[2][i] ^ mc.mul2[(state[3][i] & 0xF0) >>> 4][state[3][i] & 0xF]);
        }

        return newState;
    }

    private byte[][] invMixColumns(byte[][] state) {
        byte[][] newState = new byte[4][4];

        for (int i = 0; i < 4; i++) {
            newState[0][i] = (byte) (mc.mul14[(state[0][i] & 0xF0) >>> 4][state[0][i] & 0xF] ^ mc.mul11[(state[1][i] & 0xF0) >>> 4][state[1][i] & 0xF] ^ mc.mul13[(state[2][i] & 0xF0) >>> 4][state[2][i] & 0xF] ^ mc.mul9[(state[3][i] & 0xF0) >>> 4][state[3][i] & 0xF]);
            newState[1][i] = (byte) (mc.mul9[(state[0][i] & 0xF0) >>> 4][state[0][i] & 0xF] ^ mc.mul14[(state[1][i] & 0xF0) >>> 4][state[1][i] & 0xF] ^ mc.mul11[(state[2][i] & 0xF0) >>> 4][state[2][i] & 0xF] ^ mc.mul13[(state[3][i] & 0xF0) >>> 4][state[3][i] & 0xF]);
            newState[2][i] = (byte) (mc.mul13[(state[0][i] & 0xF0) >>> 4][state[0][i] & 0xF] ^ mc.mul9[(state[1][i] & 0xF0) >>> 4][state[1][i] & 0xF] ^ mc.mul14[(state[2][i] & 0xF0) >>> 4][state[2][i] & 0xF] ^ mc.mul11[(state[3][i] & 0xF0) >>> 4][state[3][i] & 0xF]);
            newState[3][i] = (byte) (mc.mul11[(state[0][i] & 0xF0) >>> 4][state[0][i] & 0xF] ^ mc.mul13[(state[1][i] & 0xF0) >>> 4][state[1][i] & 0xF] ^ mc.mul9[(state[2][i] & 0xF0) >>> 4][state[2][i] & 0xF] ^ mc.mul14[(state[3][i] & 0xF0) >>> 4][state[3][i] & 0xF]);
        }

        return newState;
    }

    private byte[][] addRoundKey(byte[][] state, byte[][] roundKey) {
        byte[][] newState = new byte[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newState[i][j] = (byte) (state[i][j] ^ roundKey[i][j]);
            }
        }

        return newState;
    }

}
