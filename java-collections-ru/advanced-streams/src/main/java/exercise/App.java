package exercise;

import java.util.stream.Collectors;
import java.util.Arrays;

// BEGIN
public class App {
    public static String getForwardedVariables(String content) {
        return Arrays.stream(content.split("\n"))
                .filter(line -> line.startsWith("environment"))
                .filter(line -> line.contains("X_FORWARDED"))
                .map(line -> line.replace("environment=\"", ""))
                .map(line -> line.replace("\"", ""))
                .flatMap(line -> Arrays.stream(line.split(",")))
                .filter(kv -> kv.startsWith("X_FORWARDED_"))
                .map(kv -> kv.replace("X_FORWARDED_", ""))
                .collect(Collectors.joining(","));
    }
}
//END
