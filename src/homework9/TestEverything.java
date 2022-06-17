package homework9;

public class TestEverything {
    public static void main(String[] args) {
        MyList<Integer> arraylist = new MyArrayList<>();
        MyLinkedList<Integer> linkedlist = new MyLinkedList<>();
        MyHashMap testMap = new MyHashMap();

        testList(arraylist, "arraylist");
        testList(linkedlist,"linkedlist");
        testQue(linkedlist, "linkedlist");
        testMap(testMap);
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

    public static void testQue(MyQueue<Integer> queue, String name) {
        System.out.println("Тестируем очередь: " + name);
        System.out.println("====================================");
        System.out.println("queue.size() = " + queue.size());
        System.out.println("queue.peek() = " + queue.peek());
        System.out.println("queue.poll() = " + queue.poll());
        queue.add(123);
        System.out.println("queue.size() = " + queue.size());
        System.out.println("queue.peek() = " + queue.peek());
        System.out.println("queue.poll() = " + queue.poll());
        System.out.println("queue.size() = " + queue.size());
    }

    public static void testMap(MyHashMap map) {
        map.put("KEY", 12);
        map.put("ANOTHER_KEY", 14);
        map.put("KEY", 1);
        System.out.println(map.size);
        System.out.println(map.get("KEY"));
        System.out.println(map.get("ANOTHER_KEY"));
    }
}
