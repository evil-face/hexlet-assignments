package exercise;

import java.util.Random;

// BEGIN
public class ListThread extends Thread {
    private SafetyList safetyList;

    public ListThread(SafetyList safetyList) {
        this.safetyList = safetyList;
    }

    @Override
    public void run() {
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            safetyList.add(rand.nextInt(1_000));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
// END
