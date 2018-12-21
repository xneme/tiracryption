package tiracryption.structures;

import java.util.Arrays;

/**
 * Arbitrary precision integer.
 */
public class TiraBigInteger {

    private final Integer[] value;
    private final Long BASE = 4294967296L;
    private final long UINT32_MASK = 0xFFFFFFFFL;
    private final int UINT8_MASK = 0xFF;
    public static final TiraBigInteger ONE = new TiraBigInteger("01");
    public static final TiraBigInteger ZERO = new TiraBigInteger("00");

    public TiraBigInteger(String value) {
        byte[] bytes = new byte[value.length() / 2];
        for (int i = 0; i < value.length(); i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(value.charAt(i), 16) << 4) + (Character.digit(value.charAt(i + 1), 16)));
        }

        this.value = parseByteArray(bytes);
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
        if (this.compareTo(b) <= 0) {
            return TiraBigInteger.ZERO;
        }

        return new TiraBigInteger(substractArrays(this.value, b.toIntegerArray()));
    }

    public TiraBigInteger multiply(TiraBigInteger b) {
        return new TiraBigInteger(multiplyArrays(this.value, b.toIntegerArray()));
    }

    public TiraBigInteger divide(TiraBigInteger b) {
        return this.divideAndRemainder(b)[0];
    }

    public TiraBigInteger mod(TiraBigInteger b) {
        return this.divideAndRemainder(b)[1];
    }

    public TiraBigInteger gcd(TiraBigInteger b) {
        return null;
    }

    public TiraBigInteger modInverse(TiraBigInteger b) {
        return null;
    }

    public TiraBigInteger modpow(TiraBigInteger pow, TiraBigInteger mod) {
        return null;
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
        return compareArrays(this.value, b.toIntegerArray());
    }

    private int compareArrays(Integer[] a, Integer[] b) {

        if (a.length != b.length) {
            return Integer.compare(a.length, b.length);
        }

        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b[i])) {
                return Integer.compareUnsigned(a[i], b[i]);
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

    @Override
    public String toString() {
        String output = "";

        for (byte b : this.toByteArray()) {
            output = output + String.format("%02X", b);
        }

        return output;
    }

    private Integer[] parseByteArray(byte[] input) {
        Integer[] output = new Integer[(input.length - 1) / 4 + 1];
        int outputIndex = 0;
        int inputIndex = 0;

        if (input.length % 4 == 3) {
            output[0] = (input[inputIndex] & UINT8_MASK) << 16 | (input[inputIndex + 1] & UINT8_MASK) << 8 | (input[inputIndex + 2] & UINT8_MASK);
            outputIndex = 1;
            inputIndex = 3;
        } else if (input.length % 4 == 2) {
            output[0] = (input[inputIndex] & UINT8_MASK) << 8 | (input[inputIndex + 1] & UINT8_MASK);
            outputIndex = 1;
            inputIndex = 2;
        } else if (input.length % 4 == 1) {
            output[0] = input[inputIndex] & UINT8_MASK;
            outputIndex = 1;
            inputIndex = 1;
        }

        while (outputIndex < output.length) {
            output[outputIndex] = (input[inputIndex] & UINT8_MASK) << 24 | (input[inputIndex + 1] & UINT8_MASK) << 16 | (input[inputIndex + 2] & UINT8_MASK) << 8 | (input[inputIndex + 3] & UINT8_MASK);
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
                sum += ((long) a[i - (sumArray.length - a.length)]) & UINT32_MASK;
            }

            if (i - (sumArray.length - b.length) >= 0) {
                sum += ((long) b[i - (sumArray.length - b.length)]) & UINT32_MASK;
            }

            if ((sum & 0x100000000L) > 0) {
                carry = 1;
            }

            sumArray[i] = (int) sum;
        }

        return sumArray;
    }

    private Integer[] substractArrays(Integer[] a, Integer[] b) {
        b = leftPadArray(b, a.length);
        int borrow = 0;

        for (int i = a.length - 1; i >= 0; i--) {
            long sub = (a[i] & UINT32_MASK) - (b[i] & UINT32_MASK) - borrow;
            if (sub < 0) {
                sub += BASE;
                borrow = 1;
            } else {
                borrow = 0;
            }

            a[i] = (int) sub;

        }

        return a;
    }

    private Integer[] leftPadArray(Integer[] a, int newLength) {
        Integer[] output = new Integer[newLength];
        int difference = output.length - a.length;

        int i;
        for (i = 0; i < difference; i++) {
            output[i] = 0;
        }

        for (; i < output.length; i++) {
            output[i] = a[i - difference];
        }

        return output;
    }

    private Integer[] rightPadArray(Integer[] a, int newLength) {
        Integer[] output = new Integer[newLength];
        int i;
        for (i = 0; i < a.length; i++) {
            output[i] = a[i];
        }
        for (; i < newLength; i++) {
            output[i] = 0;
        }

        return output;
    }

    private Integer[] multiplyArrays(Integer[] a, Integer[] b) {

        Integer[] output = new Integer[a.length + b.length];

        long carry = 0;
        for (int j = b.length - 1, k = a.length + b.length - 1; j >= 0; j--, k--) {
            long product = (b[j] & UINT32_MASK) * (a[a.length - 1] & UINT32_MASK) + carry;
            output[k] = (int) product;
            carry = product >>> 32;
        }
        output[a.length - 1] = (int) carry;

        for (int i = a.length - 2; i >= 0; i--) {
            carry = 0;
            for (int j = b.length - 1, k = b.length + i; j >= 0; j--, k--) {
                long product = (b[j] & UINT32_MASK) * (a[i] & UINT32_MASK) + (output[k] & UINT32_MASK) + carry;
                output[k] = (int) product;
                carry = product >>> 32;
            }
            output[i] = (int) carry;
        }
        return output;

    }

    private TiraBigInteger[] divideAndRemainder(TiraBigInteger b) {
        int compare = this.compareTo(b);

        if (compare < 0) {
            return new TiraBigInteger[]{TiraBigInteger.ZERO, this};
        }
        if (compare == 0) {
            return new TiraBigInteger[]{TiraBigInteger.ONE, TiraBigInteger.ZERO};
        }

        Integer[] dividend = this.value;
        int originalLowerLength = b.toIntegerArray().length;
        Integer[] divisor = this.rightPadArray(b.toIntegerArray(), dividend.length);
        Integer[] quotient = this.initArray(dividend.length);
        int lengthDifference = this.value.length - b.toIntegerArray().length;
        long coarse = Long.divideUnsigned(((dividend[0] & UINT32_MASK) << 32) | (dividend[1] & UINT32_MASK), ((divisor[0] & UINT32_MASK) << 32) | (divisor[1] & UINT32_MASK));

        if (coarse > 1) {
            dividend = this.substractArrays(dividend, this.multiplyArrays(divisor, new Integer[]{(int) coarse - 1}));
            quotient[originalLowerLength - 1] = (int) coarse - 1;
        }

        while (this.compareArrays(dividend, divisor) >= 0) {
            dividend = this.substractArrays(dividend, divisor);
            quotient[originalLowerLength - 1]++;
        }

        for (int i = 0; i < lengthDifference; i++) {

            divisor = this.shiftRight(divisor, 1);
            while ((dividend[i] & UINT32_MASK) > 0) {

                coarse = Long.divideUnsigned(((dividend[i] & UINT32_MASK) << 32) | (dividend[i + 1] & UINT32_MASK), (divisor[i + 1] & UINT32_MASK));

                if (coarse > 1) {
                    dividend = this.substractArrays(dividend, this.multiplyArrays(divisor, new Integer[]{(int) coarse / 2}));
                    quotient[originalLowerLength + i] += (int) coarse / 2;
                }

            }

//            coarse = Long.divideUnsigned(((dividend[i + 1] & UINT32_MASK) << 32) | (dividend[i + 2] & UINT32_MASK), ((divisor[i + 1] & UINT32_MASK) << 32) | (divisor[i + 2] & UINT32_MASK));
//
//            if (coarse > 1) {
//                dividend = this.substractArrays(dividend, this.multiplyArrays(divisor, new Integer[]{(int) coarse / 2}));
//                quotient[originalLowerLength + i] += (int) coarse / 2;
//            }
            
            while (this.compareArrays(dividend, divisor) >= 0) {
                dividend = this.substractArrays(dividend, divisor);
                quotient[originalLowerLength + i]++;
            }
        }

        TiraBigInteger[] output = new TiraBigInteger[]{new TiraBigInteger(quotient), new TiraBigInteger(dividend)};

        return output;
    }

    private Integer[] initArray(int length) {
        Integer[] array = new Integer[length];

        for (int i = 0; i < length; i++) {
            array[i] = 0;
        }

        return array;
    }

    private Integer[] shiftLeft(Integer[] a, int amount) {
        int i;
        for (i = 0; i < a.length - amount; i++) {
            a[i] = a[i + amount];
        }
        for (; i < a.length; i++) {
            a[i] = 0;
        }

        return a;
    }

    private Integer[] shiftRight(Integer[] a, int amount) {
        int i;
        for (i = a.length - 1; i >= amount; i--) {
            a[i] = a[i - amount];
        }

        for (; i >= 0; i--) {
            a[i] = 0;
        }

        return a;
    }
}
