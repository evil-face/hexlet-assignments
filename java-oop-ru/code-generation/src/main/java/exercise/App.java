package exercise;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

// BEGIN
class App {
    public static void save(Path path, Car car) {
        String json = car.serialize();
        try {
            Files.writeString(path, json);
        } catch (IOException e) {
            System.out.println("Could not create file or write content");
        }
    }

    public static Car extract(Path path) throws IOException {
        if(!Files.exists(path)) {
            throw new IOException("File not found");
        }
        String jsonContent = Files.readString(path);
        return Car.unserialize(jsonContent);
    }
}
// END
