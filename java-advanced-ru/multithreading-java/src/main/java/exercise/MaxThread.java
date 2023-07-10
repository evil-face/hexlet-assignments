package exercise;

// BEGIN
public class MaxThread extends Thread {
    private int max = Integer.MIN_VALUE;
    private int[] arr;

    public MaxThread(int[] arr) {
        this.arr = arr;
    }

    public int getMax() {
        return max;
    }

    @Override
    public void run() {
        for (int i : arr) {
            if (i > max) {
                max = i;
            }
        }
    }
}
// END
