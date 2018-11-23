package tiracryption.methods;

import tiracryption.keys.AESKey;
import tiracryption.structures.SBox;

public class AES {

    private AESKey key;
    private SBox sbox;

    public AES(AESKey key) {
        this.key = key;
        this.sbox = new SBox();
    }

    private byte[][] subBytes(byte[][] state) {
        byte[][] newState = state;

        for (byte[] row : newState) {
            row = this.sbox.forward(row);
        }

        return newState;
    }

    private byte[][] invSubBytes(byte[][] state) {
        byte[][] newState = state;

        for (byte[] row : newState) {
            row = this.sbox.reverse(row);
        }

        return newState;
    }

    private byte[][] shiftRows(byte[][] state) {
        byte[][] newState = new byte[4][4];

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
            newState[0][i] = (byte) (state[0][i] * 0x02 + state[1][i] * 0x03 + state[2][i] + state[3][i]);
            newState[1][i] = (byte) (state[0][i] + state[1][i] * 0x02 + state[2][i] * 0x03 + state[3][i]);
            newState[2][i] = (byte) (state[0][i] + state[1][i] + state[2][i] * 0x02 + state[3][i] * 0x03);
            newState[3][i] = (byte) (state[0][i] * 0x03 + state[1][i] + state[2][i] + state[3][i] * 0x02);
        }

        return newState;
    }

    private byte[][] invMixColumns(byte[][] state) {
        byte[][] newState = new byte[4][4];

        for (int i = 0; i < 4; i++) {
            newState[0][i] = (byte) (state[0][i] * 0x0e + state[1][i] * 0x0b + state[2][i] * 0x0d + state[3][i] * 0x09);
            newState[1][i] = (byte) (state[0][i] * 0x09 + state[1][i] * 0x0e + state[2][i] * 0x0b + state[3][i] * 0x0d);
            newState[2][i] = (byte) (state[0][i] * 0x0d + state[1][i] * 0x09 + state[2][i] * 0x0e + state[3][i] * 0x0b);
            newState[3][i] = (byte) (state[0][i] * 0x0b + state[1][i] * 0x0d + state[2][i] * 0x09 + state[3][i] * 0x0e);
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
