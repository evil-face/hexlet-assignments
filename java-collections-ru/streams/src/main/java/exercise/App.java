package exercise;

import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.Set;

// BEGIN
class App {
    public static void main(String[] args) {
        String[] emails = {
                "info@gmail.com",
                "info@yandex.ru",
                "mk@host.com",
                "support@hexlet.io",
                "info@hotmail.com",
                "support.yandex.ru@host.com"
        };

        List<String> emailsList = Arrays.asList(emails);
        System.out.println(App.getCountOfFreeEmails(emailsList)); // 3
    }

    public static long getCountOfFreeEmails(List<String> input) {
        long res = 0;
        if (input != null) {
            res = input.stream()
                    .filter(email -> email.contains("@gmail.com") ||
                            email.contains("@yandex.ru") ||
                            email.contains("@hotmail.com"))
                    .count();
        } else System.out.println("List is null");

//        if (input != null) {
//            List<String> freeDomains = List.of("@gmail.com", "@yandex.ru", "@hotmail.com");
//            Set<String> freeDomainsSet = new HashSet<>(freeDomains);
//
//            res = input.stream()
//                    .filter(email -> freeDomainsSet.contains(email))
//                    .count();
//        } else System.out.println("List is null");

        return res;
    }
}
// END
