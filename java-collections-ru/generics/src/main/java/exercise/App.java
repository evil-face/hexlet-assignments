package exercise;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

// BEGIN
class App {

    public static List<Map<String, String>> findWhere(List<Map<String, String>> bookList, Map<String, String> query) {
        List<Map<String, String>> result = new ArrayList<>();

        for (Map book: bookList) {
            if (book.entrySet().containsAll(query.entrySet())) {
                result.add(book);
            }
        }

        return result;
    }
}
//END
