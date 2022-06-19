package homework11;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Ivan", "Peter", "Andrew", "John", "Kevin");
        String[] dataArray = {"1, 2, 0","4, 5"};

        // Task 1
        System.out.println(oddNamesList(names));
        // Task 2
        System.out.println(reverseSortAndBringToUpperCase(names));
        // Task 3
        System.out.println(getAllNumsFromArray(dataArray));
        // Task 4
        linearGenerator(25214903917L,11,(long)Math.pow(2,48),1)
                .limit(20)
                .forEach(System.out::println);
        // Task 5
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

    public static Stream<Long> linearGenerator(long a, int c, long m, long seed) {
        return Stream.iterate(seed, n -> (a * n + c) % m);
    }
}
