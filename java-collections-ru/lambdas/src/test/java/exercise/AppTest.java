package exercise;

import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

// BEGIN
class AppTest {
    String[][] image;
    String[][] expected;

    @Test
    void testReadme() {
        image = new String[][]{
                {"*", "*", "*", "*"},
                {"*", " ", " ", "*"},
                {"*", " ", " ", "*"},
                {"*", "*", "*", "*"},
        };

        expected = new String[][]{
                {"*", "*", "*", "*", "*", "*", "*", "*"},
                {"*", "*", "*", "*", "*", "*", "*", "*"},
                {"*", "*", " ", " ", " ", " ", "*", "*"},
                {"*", "*", " ", " ", " ", " ", "*", "*"},
                {"*", "*", " ", " ", " ", " ", "*", "*"},
                {"*", "*", " ", " ", " ", " ", "*", "*"},
                {"*", "*", "*", "*", "*", "*", "*", "*"},
                {"*", "*", "*", "*", "*", "*", "*", "*"}
        };
        String[][] actual1 = App.enlargeArrayImage(image);
        assertThat(actual1).isEqualTo(expected);
    }

    @Test
    void testEmpty() {
        image = new String[0][];
        expected = new String[0][];
        String[][] actual2 = App.enlargeArrayImage(image);
        assertThat(actual2).isEqualTo(expected);
    }

    @Test
    void testSingleElement() {
        image = new String[][]{{"@"}};
        expected = new String[][]{
                {"@", "@"},
                {"@", "@"},
        };
        String[][] actual3 = App.enlargeArrayImage(image);
        assertThat(actual3).isEqualTo(expected);
    }

    @Test
    void testSingleColumn() {
        image = new String[][]{{"."}, {"."}, {"."}, {"."}};
        expected = new String[][]{
                {".", "."},
                {".", "."},
                {".", "."},
                {".", "."},
                {".", "."},
                {".", "."},
                {".", "."},
                {".", "."}
        };
        String[][] actual4 = App.enlargeArrayImage(image);
        assertThat(actual4).isEqualTo(expected);
    }

    @Test
    void testSingleRow() {
        image = new String[][]{{".", "-", ".", "-"}};
        expected = new String[][]{
                {".", ".", "-", "-", ".", ".", "-", "-",},
                {".", ".", "-", "-", ".", ".", "-", "-",}
        };
        String[][] actual5 = App.enlargeArrayImage(image);
        assertThat(actual5).isEqualTo(expected);
    }
}
// END
