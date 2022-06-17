package homework11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Ivan", "Peter", "Andrew", "John", "Kevin");
        List<String> sortedNames;
        String[] dataArray = {"1, 2, 0","4, 5"};

        System.out.println(oddNamesList(names));
        System.out.println(sortedNames = reverseSortAndBringToUpperCase(names));
        System.out.println(getAllNumsFromArray(dataArray));
    }

    public static String oddNamesList(List<String> names) {
        final int[] counter = {1};
        return names.stream().reduce("", (a, b) -> {
            if (counter[0]++ % 2 != 0) return a + (counter[0] - 1) + ". " + b + " ";
            else return a;
        }).trim();
    }

    public static ArrayList<String> reverseSortAndBringToUpperCase(List<String> namesList) {
        return namesList.stream()
                .map(String::toUpperCase)
                .sorted(Comparator.reverseOrder())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

    }

    public static String getAllNumsFromArray(String[] nums) {
        return Stream.of(nums)
                .flatMap(value -> Stream.of(value.split(", ")))
                .sorted()
                .collect(Collectors.joining(", "));
    }
}
