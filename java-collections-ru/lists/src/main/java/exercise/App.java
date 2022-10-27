package exercise;

import java.util.ArrayList;
import java.util.List;

// BEGIN
class App {
    public static boolean scrabble(String letters, String word) {
        List<Character> wordList = new ArrayList<>();

        for (char c: word.toLowerCase().toCharArray()) {
            wordList.add(c);
        }

        for (char c: letters.toCharArray()) {
            wordList.remove((Character) c);
        }

        return wordList.isEmpty() ? true : false;
    }
}
//END
