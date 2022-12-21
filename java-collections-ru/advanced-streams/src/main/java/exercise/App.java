package exercise;

import java.util.stream.Collectors;
import java.util.Arrays;

// BEGIN
public class App {
    public static String getForwardedVariables(String content) {
        return Arrays.stream(content.split("\n"))
                .filter(str -> str.startsWith("environment"))
                .filter(str -> str.contains("X_FORWARDED"))
                .map(str -> str.replace("environment=\"", ""))
                .map(str -> str.replace("\"", ""))
                .flatMap(str -> Arrays.stream(str.split(",")))
                .filter(str -> str.startsWith("X_FORWARDED_"))
                .map(str -> str.replace("X_FORWARDED_", ""))
                .collect(Collectors.joining(","));

    }
}
//END
