package exercise;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// BEGIN
public class App {
    public static void swapKeyValue(KeyValueStorage kvs) {
        for (var key : kvs.toMap().keySet()) {
            var oldValue = kvs.get(key, "default");
            kvs.unset(key);
            kvs.set(oldValue, key);
        }
    }
}
// END
