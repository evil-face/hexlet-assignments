package exercise;

class SafetyList {
    // BEGIN
    private int[] arr;
    private int size;

    public SafetyList() {
        this.arr = new int[10];
        this.size = 0;
    }

    public synchronized void add(int i) {
        if (size >= arr.length) {
            int[] newArr = new int[arr.length * 2];
            System.arraycopy(arr, 0, newArr, 0, size);
            this.arr = newArr;
        }
        arr[size++] = i;
    }

    public int get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return arr[index];
    }

    public int getSize() {
        return size;
    }
    // END
}
