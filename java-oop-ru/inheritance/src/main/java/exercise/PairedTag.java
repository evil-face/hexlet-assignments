package exercise;

import java.util.Map;
import java.util.List;

// BEGIN
class PairedTag extends Tag {
    private final String body;
    private final List<Tag> subTags;

    PairedTag(String name, Map<String, String> attributes, String body, List<Tag> subTags) {
        super(name, attributes);
        this.body = body;
        this.subTags = subTags;
    }

    public String getBody() {
        return this.body;
    }
    
    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append(buildSingleTag());
        for (var tag : subTags) {
            sb.append(tag.toString());
        }
        sb.append(getBody());
        sb.append("</%s>".formatted(this.getName()));
        return sb.toString();
    }
}
// END
