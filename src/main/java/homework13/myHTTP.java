package homework13;

public class myHTTP {
    private static final String SITE = "https://jsonplaceholder.typicode.com";

    public static void main(String[] args) throws Exception {

        User newUser = new User(7,
                "Sergii Shynkarenko",
                "email@mail.com",
                new Address("Korolyov", "28", "Odesa", "65104",
                        new Geo(-37.3159, 81.1496)),
                "380637517677",
                "www.site.com.ua",
                new Company("Carnival", "Cruising Brand", "Bullshit"));

        // Task 1
        System.out.println("\033[0;96m" + "Task 1");
        System.out.println("================================================" + "\u001B[0m");
        System.out.println("Загрузка пользователя на сервер методом POST");
        System.out.println("Ответ сервера");
        System.out.println(myHttpHandler.createUser(newUser, SITE + "/users"));

        System.out.println("Попытка удаления пользователя по id = 1, ответ сервера: " + myHttpHandler.deleteUser(1,SITE));

        // загрузка пользователя с сайта и редактирование отдельных его полей перед загрузкой обратно методом PUT
        User usr = myHttpHandler.GSON.fromJson(myHttpHandler.getUserInfoById(SITE, 4), User.class);
        usr.getCompany().setName("Carnival LLC");
        usr.setId(8);

        System.out.println("Загрузка пользователя на сервер методом PUT");
        System.out.println(myHttpHandler.updateUser(usr, SITE));

        System.out.println("Получение информации обо всех пользователях: ");
        System.out.println(myHttpHandler.getAllUsersInfo(SITE));

        System.out.println("получение информации о пользователе с опредленным id = 3: ");
        System.out.println(myHttpHandler.getUserInfoById(SITE, 3));

        System.out.println("получение информации о пользователе с опредленным username = Ervin Howell: ");
        System.out.println(myHttpHandler.getUserInfoByName(SITE, "Ervin Howell"));

        // Task 2
        System.out.println("\033[0;91m" + "Task 2");
        System.out.println("================================================" + "\u001B[0m");
        System.out.println("Получить все комментарии к последнему посту пользователя с id = " + 5);
        myHttpHandler.getCommentsToLastPostOfUser(SITE, 5);

        // Task 3
        System.out.println("\033[0;93m" + "Task 3");
        System.out.println("================================================" + "\u001B[0m");
        System.out.printf("Все открытые задачи для пользователя %d.\n", 7);
        myHttpHandler.getAllOpenTasks(SITE, 7);
    }

}