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
                new Address("Korolyov","28","Odesa","65104",
                        new Geo(-37.3159,81.1496)),
                "380637517677",
                "www.site.com.ua",
                new Company("Carnival","Cruising Brand","Bullshit"));

        // Task 1
        System.out.println("\033[0;96m" + "Task 1");
        System.out.println("================================================" + "\u001B[0m");
        System.out.println("Загрузка пользователя на сервер методом POST");
        System.out.println("Ответ сервера");
        System.out.println(createUser(newUser,site + "/users"));

        System.out.println("Попытка удаления пользователя по id = 1, ответ сервера: " +
                sendRequestAndGetResponseCode(site + "/users/1", HttpRequest.Builder::DELETE, null));

        // загрузка пользователя с сайта и редактирование отдельных его полей перед загрузкой обратно методом PUT
        User usr = GSON.fromJson(getUserInfoById(site, 4),User.class);
        usr.getCompany().setName("Carnival LLC");
        usr.setId(8);

        System.out.println("Загрузка пользователя на сервер методом PUT");
        System.out.println(updateUser(usr,site + "/users/1"));

        System.out.println("Получение информации обо всех пользователях: ");
        System.out.println(getAllUsersInfo(site));

        System.out.println("получение информации о пользователе с опредленным id = 3: ");
        System.out.println(getUserInfoById(site,3));

        System.out.println("получение информации о пользователе с опредленным username = Ervin Howell: ");
        System.out.println(getUserInfoByName(site,"Ervin Howell"));

        // Task 2
        System.out.println("\033[0;91m" + "Task 2");
        System.out.println("================================================" + "\u001B[0m");
        System.out.println("Получить все комментарии к последнему посту пользователя с id = " + 5);
        getCommentsToLastPostOfUser(site,5);

        // Task 3
        System.out.println("\033[0;93m" + "Task 3");
        System.out.println("================================================" + "\u001B[0m");
        System.out.printf("Все открытые задачи для пользователя %d.\n", 7);
        getAllOpenTasks(site, 7);
    }
    public static void getAllOpenTasks(String site, int userId) throws IOException, InterruptedException {
        TaskToDo[] tasks = GSON.fromJson(sendRequestAndGetResponseBody(site + "/users/" + userId + "/todos",HttpRequest.Builder::GET,null),TaskToDo[].class);
        Stream.of(tasks).filter(task -> !task.isCompleted()).map(GSON::toJson).forEach(System.out::println);
    }

    public static void getCommentsToLastPostOfUser(String site, int userId) throws IOException, InterruptedException {
        Post[] posts = GSON.fromJson(sendRequestAndGetResponseBody(site + "/users/" + userId + "/posts",HttpRequest.Builder::GET,null),Post[].class);

        var lastPost = Arrays.stream(posts)
                .max(Comparator.comparing(Post::getId))
                .get().getId();

        var result = sendRequestAndGetResponseBody(site + "/posts/" + lastPost + "/comments",HttpRequest.Builder::GET,null);

        Files.writeString(Path.of(String.format("./src/main/java/homework13/user-%d-post-%d-comments.json",userId,lastPost)),result);
        System.out.println(result);

    }

    public static String createUser(User user, String site) throws IOException, InterruptedException {
        //return sendPostRequest(site, GSON.toJson(user));
        return sendRequestAndGetResponseBody(site,
                x -> x.POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(user))),
                x -> x.header("Content-Type","application/json"));
    }

    public static String updateUser(User user, String site) throws IOException, InterruptedException{
        return sendRequestAndGetResponseBody(site,
                x -> x.PUT(HttpRequest.BodyPublishers.ofString(GSON.toJson(user))),
                x -> x.header("Content-Type","application/json"));
    }

    public static String getAllUsersInfo(String site) throws IOException, InterruptedException {
        return sendRequestAndGetResponseBody(site + "/users",HttpRequest.Builder::GET,null);
    }

    public static String getUserInfoById(String site, int id) throws IOException, InterruptedException {
        return sendRequestAndGetResponseBody(site + "/users/" + id,HttpRequest.Builder::GET,null);
    }

    public static String getUserInfoByName(String site, String name) throws IOException, InterruptedException {
        return sendRequestAndGetResponseBody(site + "/users?name=" + convertString(name),HttpRequest.Builder::GET,null);
    }

    private static String convertString(String inlet) {
        return Arrays.stream(inlet.split(""))
                .map(x -> {
                    if (x.equals(" ")) return "+";
                    else return x;
                })
                .collect(Collectors.joining(""));
    }

    private static String sendRequestAndGetResponseBody
            (String site, UnaryOperator<HttpRequest.Builder> method,
             UnaryOperator<HttpRequest.Builder> header) throws IOException, InterruptedException {

        var x = method.apply(HttpRequest.newBuilder().uri(URI.create(site)));
        var requestBuilder = header == null ? x : header.apply(x);
        var request = requestBuilder.build();

        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private static int sendRequestAndGetResponseCode
            (String site, UnaryOperator<HttpRequest.Builder> method,
             UnaryOperator<HttpRequest.Builder> header) throws IOException, InterruptedException {

        var x = method.apply(HttpRequest.newBuilder().uri(URI.create(site)));
        var requestBuilder = header == null ? x : header.apply(x);
        var request = requestBuilder.build();

        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).statusCode();
    }
}

