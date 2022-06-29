package homework13;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

public class myHTTP {
    public static void main(String[] args) throws Exception {
        String site = "https://jsonplaceholder.typicode.com";

        System.out.println(getAllUsersInfo(site));
        System.out.println(getUserInfoById(site, 1));
        System.out.println();
        System.out.println(getUserInfoByName(site, "Nicholas Runolfsdottir V"));
    }

    public static void createUser(User user) {

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
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
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
}
