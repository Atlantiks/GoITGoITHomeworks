package homework11;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Ivan", "Peter", "Andrew", "John", "Kevin");
        System.out.println(oddNamesList(names));
        System.out.println(switchToUpperCaseAndSort(names));
    }

    public static String oddNamesList(List<String> names) {
        final int[] counter = {1};
        return names.stream().reduce("", (a, b) -> {
            if (counter[0]++ % 2 != 0) return a + (counter[0] - 1) + ". " + b + " ";
            else return a;
        }).trim();
    }

    public static String switchToUpperCaseAndSort(List<String> names) {
        return names.stream()
                .map(String::toUpperCase).sorted()
                .reduce("", (x, y) -> x + " " + y)
                .trim();
    }
}
