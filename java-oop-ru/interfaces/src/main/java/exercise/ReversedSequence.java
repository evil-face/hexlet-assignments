package exercise;

import java.util.Arrays;

// BEGIN
public class ReversedSequence implements CharSequence {
    private final char[] arr;

    public ReversedSequence(String s) {
        arr = new char[s.length()];
        for(int i = 0; i < s.length(); i++) {
            arr[i] = s.charAt(s.length() - 1 - i);
        }
    }

    @Override
    public int length() {
        return arr.length;
    }

    @Override
    public char charAt(int index) {
        return arr[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var ch : arr) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
// END
