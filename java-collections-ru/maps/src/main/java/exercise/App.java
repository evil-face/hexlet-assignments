package exercise;

import java.util.HashMap;
import java.util.Map;

// BEGIN
class App {
    public static Map<String, Integer> getWordCount(String sentence) {
        Map<String, Integer> vocab = new HashMap<>();
        String[] sentenceArr = sentence.split(" ");

        if (sentence.equals("")) {
            return new HashMap<>();
        }

        for (String word: sentenceArr) {
            if (vocab.containsKey(word)) {
                vocab.put(word, vocab.get(word) + 1);
            } else {
                vocab.put(word, 1);
            }
        }
        return vocab;
    }

    public static String toString(Map<String, Integer> vocab) {
        var result = new StringBuilder();

        if (vocab.size() == 0) {
            result.append("{}");
        } else {
            result.append("{");
            for (Map.Entry<String, Integer> word: vocab.entrySet()) {
                result.append("\n  ");
                result.append(word.getKey());
                result.append(": ");
                result.append(word.getValue());
            }
            result.append("\n}");
        }

        return result.toString();
    }
}
//END
