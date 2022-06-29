package homework13;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class myHTTP {
    private final static HttpClient CLIENT = HttpClient.newHttpClient();
    private final static Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();
    public static void main(String[] args) throws Exception {
        String site = "https://jsonplaceholder.typicode.com";

        User newUser = new User(7,
                "Sergii Shynkarenko",
                "email@mail.com",
                new User.Address("Korolyov","28","Odesa","65104",
                        new User.Geo(-37.3159,81.1496)),
                "380637517677",
                "www.site.com.ua",
                new User.Company("Carnival","Cruising Brand","Bullshit"));

        String test = createUser(newUser,site + "/users");
        System.out.println(test);

        System.out.println("Попытка удаления пользователя по id: " + sendDeleteRequest(site + "/users/1"));
        System.out.println(getUserInfoById(site, 5));

        User usr = GSON.fromJson(getUserInfoById(site, 5),User.class);
        usr.company.name = "Carnival LLC";


        String test2 = sendPutRequest(site + "/users",GSON.toJson(usr));
        System.out.println(test2);

        getCommentsToLastPostOfUser(site + "/users/1/posts");
    }

    public static void getCommentsToLastPostOfUser(String site) throws IOException, InterruptedException {
        Post[] posts = GSON.fromJson(sendGetRequest(site),Post[].class);
        var x = Arrays.stream(posts)
                .max(Comparator.comparing(post -> post.id))
                .get().id;
        var result = sendGetRequest("https://jsonplaceholder.typicode.com/posts/" + x + "/comments");
        Files.writeString(Path.of("./src/main/java/homework13/comments.json"),result);
        System.out.println(result);
    }

    public static String createUser(User user, String site) throws IOException, InterruptedException {
        return sendPostRequest(site, GSON.toJson(user));
    }

    public static String getAllUsersInfo(String site) throws IOException, InterruptedException {
        return sendGetRequest(site + "/users");
    }

    public static String getUserInfoById(String site, int id) throws IOException, InterruptedException {
        return sendGetRequest(site + "/users/" + id);
    }

    public static String getUserInfoByName(String site, String name) throws IOException, InterruptedException {
        return sendGetRequest(site + "/users?name=" + convertString(name));
    }

    private static String sendGetRequest(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        var response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static String convertString(String inlet) {
        return Arrays.stream(inlet.split(""))
                .map(x -> {
                    if (x.equals(" ")) return "+";
                    else return x;
                })
                .collect(Collectors.joining(""));
    }

    private static String sendPostRequest(String uri, String data) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();

        var response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static int sendDeleteRequest(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .DELETE()
                .build();

        var response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    private static String sendPutRequest(String uri, String data) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .PUT(HttpRequest.BodyPublishers.ofString(data))
                .build();

        var response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}

