package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    // BEGIN
    public static Map<String, Integer> getMinMax(int[] arr) {
        MaxThread maxThread = new MaxThread(arr);
        MinThread minThread = new MinThread(arr);

        maxThread.start();
        LOGGER.log(Level.INFO,"Thread " + maxThread.getName() + " started");

        minThread.start();
        LOGGER.log(Level.INFO,"Thread " + minThread.getName() + " started");

        try {
            maxThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOGGER.log(Level.INFO,"Thread " + maxThread.getName() + " finished");

        try {
            minThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOGGER.log(Level.INFO,"Thread " + minThread.getName() + " finished");

        return new HashMap<String, Integer>(Map.of("min", minThread.getMin(), "max", maxThread.getMax()));
    }
    // END
}
