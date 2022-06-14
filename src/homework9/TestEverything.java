package homework9;

public class TestEverything {
    public static void main(String[] args) {
        MyList<Integer> arraylist = new MyArrayList<>();
        MyLinkedList<Integer> linkedlist = new MyLinkedList<>();
        MyHashMap testMap = new MyHashMap();

        testList(arraylist, "arraylist");
        testList(linkedlist,"linkedlist");

        System.out.println(linkedlist.poll());
        System.out.println(linkedlist.poll());

        testMap.put("KEY", 12);
        testMap.put("ANOTHER_KEY", 14);
        testMap.put("KEY", 1);
        System.out.println(testMap.size);
        System.out.println(testMap.get("KEY"));
        System.out.println(testMap.get("ANOTHER_KEY"));

    }

    public static void testList(MyList<Integer> list, String name) {
        System.out.println("Тестируем список: " + name);
        System.out.println("====================================");

        // Тестируем метод add()
        for (int i = 0; i < 12; i++) {
            list.add(i);
        }

        // Тестируем метод size()
        System.out.printf("Размер списка %s %d элементов\n", name, list.size());

        // Тестируем метод get()
        System.out.println("list.get(22) = " + list.get(22));
        System.out.println("list.get(4) = " + list.get(4));
        System.out.println("list.get(-3) = " + list.get(-3));
        System.out.println("list.get(11) = " + list.get(11));

        // Тестируем метод remove()
        list.remove(16);
        for (int i = 0; i < 15; i++) {
            System.out.println("Удаление элемента " + i);
            list.remove(0);
        }
        System.out.println("====================================");

        System.out.println("list.get(0) = " + list.get(0));
        System.out.println("list.size() = " + list.size());

    }
}
