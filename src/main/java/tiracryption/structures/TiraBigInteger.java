package tiracryption.structures;

/**
 * Arbitrary precision integer.
 */
public class TiraBigInteger {

    private final Integer[] value;

    public TiraBigInteger(String value) {
        this.value = new Integer[]{0};
    }

    public TiraBigInteger(byte[] bytes) {
        this.value = parseByteArray(bytes);
    }

    public TiraBigInteger(Integer[] value) {
        this.value = value;
    }

    public TiraBigInteger(int byteLength, TiraRandom random) {
        byte[] bytes = new byte[byteLength];
        random.nextBytes(bytes);
        this.value = parseByteArray(bytes);
    }

    public TiraBigInteger add(TiraBigInteger num) {
        return new TiraBigInteger("0");
    }

    public TiraBigInteger substract(TiraBigInteger num) {
        return new TiraBigInteger("0");
    }

    public TiraBigInteger multiply(TiraBigInteger num) {
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

    public int compareTo(TiraBigInteger num) {
        return 0;
    }

    public boolean equals(TiraBigInteger num) {
        return false;
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
}
