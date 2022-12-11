package exercise;

import java.util.Arrays;
import java.util.stream.Stream;

// BEGIN
public class App {
    public static void main(String[] args) {
        String[][] image = {
                {"x", "x", "x", "x"},
                {"x", "o", "o", "x"},
                {"x", "o", "o", "x"},
                {"x", "x", "x", "x"},
        };
        System.out.println(Arrays.deepToString(image));
        enlargeArrayImage(image);
    }
    public static String[][] enlargeArrayImage(String[][] arr) {
        return Arrays.stream(arr)
                .map(row -> Arrays.stream(row)
                        .flatMap(e -> Stream.of(e, e))
                        .toArray(String[]::new))
                .flatMap(row -> Stream.of(row, row))
                                .toArray(String[][]::new);
    }
}
// END
