package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppTest {
    private List<Integer> testList;

    @BeforeEach
    void integerList() {
        this.testList = new ArrayList<>();
        this.testList.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7 ,8, 9, 10));
    }

    @Test
    void testTake_firstTwo() {
        // BEGIN
        List<Integer> actual = App.take(testList, 2);
        assertThat(actual).size().isEqualTo(2);
        assertThat(actual).containsExactly(1, 2);
        // END
    }

    @Test
    void testTake_firstSeven() {
        // BEGIN
        List<Integer> actual = App.take(testList, 7);
        assertThat(actual).size().isEqualTo(7);
        assertThat(actual).containsExactly(1, 2, 3, 4, 5, 6, 7);
        // END
    }

    // пограничные тесты

    @Test
    void testTake_zero() {
        // BEGIN
        List<Integer> actual = App.take(testList, 0);
        assertThat(actual).size().isEqualTo(0);
        assertThat(actual).isEmpty();
        // END
    }

    @Test
    void testTake_all() {
        // BEGIN
        List<Integer> actual = App.take(testList, testList.size());
        assertThat(actual).size().isEqualTo(10);
        assertThat(actual).isEqualTo(testList);
        // END
    }
}
