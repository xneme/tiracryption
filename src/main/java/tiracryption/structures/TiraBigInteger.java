package tiracryption.structures;

import java.util.Arrays;

/**
 * Arbitrary precision integer.
 */
public class TiraBigInteger {

    private final Integer[] value;
    private final Long UNSIGNED_INT_MAX_VAL = 4294967295L;

    public TiraBigInteger(String value) {
        this.value = new Integer[]{0};
    }

    public TiraBigInteger(byte[] bytes) {
        this.value = parseByteArray(bytes);
    }

    public TiraBigInteger(Integer[] value) {
        this.value = removeLeadingZeros(value);
    }

    public TiraBigInteger(int byteLength, TiraRandom random) {
        byte[] bytes = new byte[byteLength];
        random.nextBytes(bytes);
        this.value = parseByteArray(bytes);
    }

    public TiraBigInteger add(TiraBigInteger b) {
        return new TiraBigInteger(addArrays(this.value, b.toIntegerArray()));
    }

    public TiraBigInteger substract(TiraBigInteger b) {
        return new TiraBigInteger("0");
    }

    public TiraBigInteger multiply(TiraBigInteger b) {
        return new TiraBigInteger("0");
    }

    public byte[] toByteArray() {
        byte[] output;
        int index;

        if (value[0] >>> 24 > 0) {
            output = new byte[4 + (value.length - 1) * 4];
            output[0] = (byte) (value[0] >>> 24);
            output[1] = (byte) (value[0] >>> 16);
            output[2] = (byte) (value[0] >>> 8);
            output[3] = (byte) (value[0] >>> 0);
            index = 4;
        } else if (value[0] >>> 16 > 0) {
            output = new byte[3 + (value.length - 1) * 4];
            output[0] = (byte) (value[0] >>> 16);
            output[1] = (byte) (value[0] >>> 8);
            output[2] = (byte) (value[0] >>> 0);
            index = 3;
        } else if (value[0] >>> 8 > 0) {
            output = new byte[2 + (value.length - 1) * 4];
            output[0] = (byte) (value[0] >>> 8);
            output[1] = (byte) (value[0] >>> 0);
            index = 2;
        } else {
            output = new byte[1 + (value.length - 1) * 4];
            output[0] = (byte) (value[0] >>> 0);
            index = 1;
        }

        for (int i = 1; i < value.length; i++) {
            output[index + 0] = (byte) (value[i] >>> 24);
            output[index + 1] = (byte) (value[i] >>> 16);
            output[index + 2] = (byte) (value[i] >>> 8);
            output[index + 3] = (byte) (value[i] >>> 0);
            index += 4;
        }

        return output;
    }

    public Integer[] toIntegerArray() {
        return this.value;
    }

    public int compareTo(TiraBigInteger b) {
        Integer[] bValue = b.toIntegerArray();

        if (bValue.length != this.value.length) {
            return Integer.compare(this.value.length, bValue.length);
        }

        for (int i = 0; i < this.value.length; i++) {
            if (bValue[i] != this.value[i]) {
                return this.value[i].compareTo(bValue[i]);
            }
        }

        return 0;
    }

    public boolean equals(TiraBigInteger b) {
        Integer[] bValue = b.toIntegerArray();

        if (bValue.length != this.value.length) {
            return false;
        }

        for (int i = 0; i < this.value.length; i++) {
            if (bValue[i] != this.value[i]) {
                return false;
            }
        }

        return true;
    }

    public TiraBigInteger gcd(TiraBigInteger num) {
        return null;
    }

    public TiraBigInteger modInverse(TiraBigInteger num) {
        return null;
    }

    @Override
    public String toString() {
        return "";
    }

    public TiraBigInteger modpow(TiraBigInteger pow, TiraBigInteger mod) {
        return null;
    }

    private Integer[] parseByteArray(byte[] input) {
        Integer[] output = new Integer[(input.length - 1) / 4 + 1];
        int outputIndex = 0;
        int inputIndex = 0;

        if (input.length % 4 == 3) {
            output[0] = (input[inputIndex] & 0xff) << 16 | (input[inputIndex + 1] & 0xff) << 8 | (input[inputIndex + 2] & 0xff);
            outputIndex = 1;
            inputIndex = 3;
        } else if (input.length % 4 == 2) {
            output[0] = (input[inputIndex] & 0xff) << 8 | (input[inputIndex + 1] & 0xff);
            outputIndex = 1;
            inputIndex = 2;
        } else if (input.length % 4 == 1) {
            output[0] = input[inputIndex] & 0xff;
            outputIndex = 1;
            inputIndex = 1;
        }

        while (outputIndex < output.length) {
            output[outputIndex] = (input[inputIndex] & 0xff) << 24 | (input[inputIndex + 1] & 0xff) << 16 | (input[inputIndex + 2] & 0xff) << 8 | (input[inputIndex + 3] & 0xff);
            outputIndex++;
            inputIndex += 4;
        }

        return output;
    }

    private Integer[] removeLeadingZeros(Integer[] input) {
        if (input[0] != 0) {
            return input;
        }

        Integer[] output;
        int i;

        for (i = 0; i < input.length; i++) {
            if (input[i] != 0) {
                break;
            }
        }

        if (i == input.length) {
            return new Integer[]{0};
        }

        output = new Integer[input.length - i];
        int difference = i;

        for (; i < input.length; i++) {
            output[i - difference] = input[i];
        }

        return output;
    }
    
    private Integer[] addArrays(Integer[] a, Integer[] b) {
        Integer[] sumArray;
        Integer carry = 0;

        if (a.length >= b.length) {
            sumArray = new Integer[a.length + 1];
        } else {
            sumArray = new Integer[b.length + 1];
        }

        int i;
        for (i = sumArray.length - 1; i >= 0; i--) {
            long sum = (long) carry;
            carry = 0;

            if (i - (sumArray.length - a.length) >= 0) {
                sum += ((long) a[i - (sumArray.length - a.length)]) & 0xFFFFFFFFL;
            }

            if (i - (sumArray.length - b.length) >= 0) {
                sum += ((long) b[i - (sumArray.length - b.length)]) & 0xFFFFFFFFL;
            }

            if ((sum & 0x100000000L) > 0) {
                carry = 1;
            }

            sumArray[i] = (int) sum;
        }

        return sumArray;
    }
    
    private Integer[] shiftArrayLeft(Integer[] a, int shiftAmount) {
        Integer[] output = new Integer[a.length + shiftAmount];
        int i;
        return a;
    }
}
