package exercise;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReversedSequenceTest {
    @Test
    void testCorrectCase() {
        String original = "test";
        String expected = "tset";
        var reversed = new ReversedSequence(original);
        String actual = reversed.toString();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testCorrectCaseLong() {
        String original = "this is A LONG string";
        String expected = "gnirts GNOL A si siht";
        var reversed = new ReversedSequence(original);
        String actual = reversed.toString();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testSubSequence() {
        String original = "this is A LONG string";
        String expected = "gnirts";
        var reversed = new ReversedSequence(original);
        String actual = (String) reversed.subSequence(0, 6);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testLength() {
        String original = "test";
        var reversed = new ReversedSequence(original);
        int actual = reversed.length();
        assertThat(actual).isEqualTo(original.length());
    }

    @Test
    void testCharAt() {
        String original = "testbobobo";
        var reversed = new ReversedSequence(original);
        char actual = reversed.charAt(5);
        assertThat(actual).isEqualTo('b');
    }
}
