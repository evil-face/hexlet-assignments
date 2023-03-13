package exercise;

import java.util.StringJoiner;
import java.util.Map;

// BEGIN
abstract class Tag {
    private final String name;
    private final Map<String, String> attributes;
    Tag(String name, Map<String, String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return this.name;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public abstract String toString();

    public String buildSingleTag() {
        var sj = new StringJoiner(" ", "<", ">");
        sj.add("%s".formatted(getName()));
        for (var tagPair : getAttributes().entrySet()) {
            sj.add("%s=\"%s\"".formatted(tagPair.getKey(), tagPair.getValue()));
        }
        return sj.toString();
    }
}
// END
