package exercise;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import java.util.TreeSet;

// BEGIN
public class App {
    public static LinkedHashMap<String, Object> genDiff(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> keys = new TreeSet<>();
        data1.forEach((k,v) -> keys.add(k));
        data2.forEach((k,v) -> keys.add(k));
        LinkedHashMap<String, Object> diffMap = new LinkedHashMap<>();
        keys.forEach(str -> {
                    if (data1.containsKey(str) && data2.containsKey(str)) {
                        if (data1.get(str).equals(data2.get(str))) {
                            diffMap.put(str, "unchanged");
                        }
                        else {
                            diffMap.put(str, "changed");
                        }
                    } else if (!data1.containsKey(str) && data2.containsKey(str)) {
                        diffMap.put(str, "added");
                    } else {
                        diffMap.put(str, "deleted");
                    }
                });
        System.out.println(diffMap.getClass() + " " + diffMap.getClass().getName());
        return diffMap;
    }
}
//END
