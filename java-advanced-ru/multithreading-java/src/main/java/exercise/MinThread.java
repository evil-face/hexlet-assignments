package exercise;

// BEGIN
public class MinThread extends Thread {
    private int min = Integer.MAX_VALUE;
    private int[] arr;

    public MinThread(int[] arr) {
        this.arr = arr;
    }

    public int getMin() {
        return min;
    }

    @Override
    public void run() {
        for (int i : arr) {
            if (i < min) {
                min = i;
            }
        }
    }
}
// END
