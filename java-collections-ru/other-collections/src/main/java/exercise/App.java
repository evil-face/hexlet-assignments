package exercise;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

// BEGIN
public class App {
    public static Map<String, Object> genDiff(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> keys = new TreeSet<>(data1.keySet());
        keys.addAll(data2.keySet());
        Map<String, Object> diffMap = new LinkedHashMap<>();
        for(var key : keys) {
            if(!data1.containsKey(key)) {
                diffMap.put(key, "added");
            } else if (!data2.containsKey(key)) {
                diffMap.put(key, "deleted");
            } else if (data1.get(key).equals(data2.get(key))) {
                diffMap.put(key, "unchanged");
            } else {
                diffMap.put(key, "changed");
            }
        }
        return diffMap;
    }
}
//END
