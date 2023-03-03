package exercise;

import java.util.HashMap;
import java.util.Map;

// BEGIN
public class FileKV implements KeyValueStorage {
    private String filePath;

    public FileKV(String path, Map<String, String> initial) {
        this.filePath = path;
        Map<String, String> tempMap = Utils.unserialize(Utils.readFile(this.filePath));
        tempMap.putAll(initial);
        Utils.writeFile(this.filePath, Utils.serialize(tempMap));
    }
    @Override
    public void set(String key, String value) {
        Map<String, String> tempMap = Utils.unserialize(Utils.readFile(this.filePath));
        tempMap.put(key, value);
        Utils.writeFile(this.filePath, Utils.serialize(tempMap));
    }

    @Override
    public void unset(String key) {
        Map<String, String> tempMap = Utils.unserialize(Utils.readFile(this.filePath));
        tempMap.remove(key);
        Utils.writeFile(this.filePath, Utils.serialize(tempMap));
    }

    @Override
    public String get(String key, String defaultValue) {
        Map<String, String> tempMap = Utils.unserialize(Utils.readFile(this.filePath));
        return tempMap.getOrDefault(key, defaultValue);
    }

    @Override
    public Map<String, String> toMap() {
        return Utils.unserialize(Utils.readFile(this.filePath));
    }
}
// END
