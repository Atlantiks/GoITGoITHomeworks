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

public class myHttpHandler {
    private final static HttpClient CLIENT = HttpClient.newHttpClient();
    public final static Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();

    public static void getAllOpenTasks(String site, int userId) throws IOException, InterruptedException {
        TaskToDo[] tasks = GSON.fromJson(sendRequestAndGetResponseBody(site + "/users/" + userId + "/todos", HttpRequest.Builder::GET,null),TaskToDo[].class);
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
        return sendRequestAndGetResponseBody(site + "/users/1",
                x -> x.PUT(HttpRequest.BodyPublishers.ofString(GSON.toJson(user))),
                x -> x.header("Content-Type","application/json"));
    }

    public static int deleteUser(int userId, String site)  throws IOException, InterruptedException{
        return sendRequestAndGetResponseCode(site + "/users/" + userId, HttpRequest.Builder::DELETE, null);
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
