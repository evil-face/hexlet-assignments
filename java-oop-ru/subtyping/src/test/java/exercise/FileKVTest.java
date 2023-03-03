package exercise;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
// BEGIN

// END


class FileKVTest {

    private static Path filepath = Paths.get("src/test/resources/file").toAbsolutePath().normalize();
    private static FileKV fkv;
    @BeforeAll
    public static void beforeEach() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(new HashMap<String, String>());
        Files.writeString(filepath, content, StandardOpenOption.CREATE);
        fkv = new FileKV(filepath.toString(), Map.of("test", "biba"));
    }

    // BEGIN
    @Test
    public void test() {
        String actual = fkv.get("test", "default");
        assertThat(actual).isEqualTo("biba");

        fkv.set("zhoka", "boka");
        String actual1 = fkv.get("zhoka", "default");
        assertThat(actual1).isEqualTo("boka");

//        fkv.set("badKey", "badValue");
//        fkv.unset("badKey");
//        assertThat(fkv.get("badKey", "default")).isEqualTo("default");
    }

//    @Test
//    public void testSet() {
//        fkv.set("zhoka", "boka");
//        String actual = fkv.get("zhoka", "default");
//        assertThat(actual).isEqualTo("boka");
//    }

//    @Test
//    public void testUnset() {
//        fkv.set("badKey", "badValue");
//        fkv.unset("badKey");
//        assertThat(fkv.get("badKey", "default")).isEqualTo("default");
//    }

    // END
}
