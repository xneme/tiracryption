package tiracryption.methods;

import java.util.Arrays;

public class RotN {
    private int n;

    public RotN() {
        this.n = 13;
    }    
    
    public RotN(int n) {
        this.n = n;
    }
    
    public String encrypt(String input) {
        char c[] = input.toCharArray();
        
        for (int i = 0; i < c.length; i++) {
            if (c[i] >= 'a' && c[i] <= 'z') {
                c[i] = (char) ('a' + (c[i] - 'a' + n) % 26);
            }
            if (c[i] >= 'A' && c[i] <= 'Z') {
                c[i] = (char) ('A' + (c[i] - 'A' + n) % 26);
            }
        }
        return new String(c);
    }
}
 