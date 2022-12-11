package exercise;

import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

// BEGIN
class Sorter {
    public static List<String> takeOldestMans(List<Map<String, String>> list) {
        List<String> oldestMans = list.stream()
                .filter(u -> u.get("gender").equals("male"))
                .sorted(Comparator.comparing(u -> LocalDate.parse(u.get("birthday"))))
                .map(u -> u.get("name"))
                .collect(Collectors.toList());

        return oldestMans;
    }
}
// END
