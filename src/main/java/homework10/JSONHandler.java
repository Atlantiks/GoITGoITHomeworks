package homework10;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JSONHandler {
    static Gson gson  = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        String fileName = "./src/main/java/homework10/user.txt";
        List<User> users = new ArrayList<>();

        try (Stream<String> streamOfLines = Files.lines(Paths.get(fileName))) {
            streamOfLines
                    .skip(1)
                    .forEach(line -> users.add(
                    new User(line.split(" ")[0],Integer.parseInt(line.split(" ")[1]))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String output = gson.toJson(users);
        System.out.println(output);
        try {
            Files.writeString(Path.of("./src/main/java/homework10/user.json"),output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class User {
        String name;
        int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
