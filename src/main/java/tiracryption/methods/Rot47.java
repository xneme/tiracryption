package tiracryption.methods;

public class Rot47 {

    public String encrypt(String input) {
        char c[] = input.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (c[i] >= 33 && c[i] <= 126) {
                c[i] = (char) (33 + (c[i] - 33 + 47) % 94);
            }
        }
        return new String(c);
    }

    public String decrypt(String input) {
        return encrypt(input);
    }

}
