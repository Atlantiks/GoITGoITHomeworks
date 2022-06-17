package homework10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class WordCounter {
    public static void main(String[] args) {
        //File file = new File("./src/main/java/homework10/words.txt");
        String fileName = "./src/main/java/homework10/words.txt";
        Map<String,Integer> words = new HashMap<>();

        try (Stream<String> streamOfLines = Files.lines(Paths.get(fileName))) {
            streamOfLines
                    .forEach(x -> Stream.of(x.split(" "))
                    .forEach(word -> words.merge(word,1, (oldQuantity,newQuantity) -> ++oldQuantity )));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

/*        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while (Objects.nonNull(line = reader.readLine())) {
                for (var word:line.split(" ")) {
                    words.merge(word,1, (oldQuantity,newQuantity) -> ++oldQuantity );
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/

        words.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }
}
