package homework11;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Ivan", "Peter", "Andrew", "John", "Kevin");
        String[] dataArray = {"1, 2, 0","4, 5"};

        System.out.println(oddNamesList(names));
        System.out.println(reverseSortAndBringToUpperCase(names));
        System.out.println(getAllNumsFromArray(dataArray));
    }

    public static String oddNamesList(List<String> names) {
        return Stream.iterate(1, n -> ++n)
                .limit(names.size())
                .filter(n -> n % 2 != 0)
                .map(n -> n + ". " + names.get(n - 1)).collect(Collectors.joining(", "));
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

    public static Stream<Long> linearGenerator(int c, int m, int seed) {
        return Stream.iterate(1L, n -> n + 1);
    }
}
