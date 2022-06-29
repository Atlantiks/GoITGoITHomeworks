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
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        // Task 1
        System.out.println("\033[0;96m" + "Task 1");
        System.out.println("================================================" + "\u001B[0m");
        System.out.println("Загрузка пользователя на сервер методом POST");
        System.out.println("Ответ сервера");
        System.out.println(createUser(newUser,site + "/users"));

        System.out.println("Попытка удаления пользователя по id = 1, ответ сервера: " + sendDeleteRequest(site + "/users/1"));

        // загрузка пользователя с сайта и редактирование отдельных его полей перед загрузкой обратно методом PUT
        User usr = GSON.fromJson(getUserInfoById(site, 4),User.class);
        usr.company.name = "Carnival LLC";
        usr.id = 12;

        System.out.println("Загрузка пользователя на сервер методом PUT");
        String serverResponse = sendPutRequest(site + "/users/1",GSON.toJson(usr));
        System.out.println(serverResponse);

        System.out.println("Получение информации обо всех пользователях: ");
        System.out.println(getAllUsersInfo(site));

        System.out.println("получение информации о пользователе с опредленным id = 3: ");
        System.out.println(getUserInfoById(site,3));

        System.out.println("получение информации о пользователе с опредленным username = Ervin Howell: ");
        System.out.println(getUserInfoByName(site,"Ervin Howell"));

        // Task 2
        System.out.println("\033[0;91m" + "Task 2");
        System.out.println("================================================" + "\u001B[0m");
        getCommentsToLastPostOfUser(site,5);

        // Task 3
        System.out.println("\033[0;93m" + "Task 3");
        System.out.println("================================================" + "\u001B[0m");
        System.out.printf("Все открытые задачи для пользователя %d.\n", 7);
        getAllOpenTasks(site, 7);


        // Lambda tests to shorten code
        String result = sendRequest(site + "/users/1", HttpRequest.Builder::GET);
        String result2 = sendRequest(site + "/users", x -> x.POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(usr))));
        System.out.println(result);
        System.out.println(result2);
    }



    public static void getAllOpenTasks(String site, int userId) throws IOException, InterruptedException {
        TaskToDo[] tasks = GSON.fromJson(sendGetRequest(site + "/users/" + userId + "/todos"),TaskToDo[].class);
        Stream.of(tasks).filter(task -> !task.isCompleted()).map(GSON::toJson).forEach(System.out::println);
    }

    public static void getCommentsToLastPostOfUser(String site, int userId) throws IOException, InterruptedException {
        Post[] posts = GSON.fromJson(sendGetRequest(site + "/users/" + userId + "/posts"),Post[].class);

        var lastPost = Arrays.stream(posts)
                .max(Comparator.comparing(Post::getId))
                .get().getId();

        var result = sendGetRequest(site + "/posts/" + lastPost + "/comments");

        Files.writeString(Path.of(String.format("./src/main/java/homework13/user-%d-post-%d-comments.json",userId,lastPost)),result);
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

    private static String sendRequest(String site,
            UnaryOperator<HttpRequest.Builder> method) throws IOException, InterruptedException {

        var x = method.apply(
                        HttpRequest.newBuilder()
                                .uri(URI.create(site)))
                .build();

        return CLIENT.send(x, HttpResponse.BodyHandlers.ofString()).body();

    }
}

