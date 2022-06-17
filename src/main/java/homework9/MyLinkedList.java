package homework9;

import java.util.Objects;

public class MyLinkedList<T> implements MyList<T>, MyQueue<T> {
    Node<T> firstNode;
    Node<T> lastNode;
    int size;

    public MyLinkedList() {
        clear();
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            firstNode = new Node<T>(value,null);
            lastNode = firstNode;
        } else {
            Node<T> previous = lastNode;
            lastNode = new Node<T>(value, null);
            previous.next = lastNode;
        }
        size++;
    }

    @Override
    public void remove(int index) {
        if (index >= size || index < 0) {
            System.out.printf("Элемента с заданныи индексом (%d) не существует\n", index);
            return;
        } else if (index == 0) {
            if (size == 1) {
                clear();
                return;
            } else {
                //System.out.println(size);
                firstNode = firstNode.next;
            }
        } else {
            Node<T> previousNode = firstNode;
            Node<T> followingNode = firstNode.next;
            for (int i = 1; i < index; i++) {
                previousNode = followingNode;
                followingNode = followingNode.next;
            }
            previousNode.next = followingNode.next;
        }
        size--;
    }

    @Override
    public void clear() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T peek() {
        return !Objects.isNull(firstNode) ? firstNode.actual : null;
    }

    @Override
    public T poll() {
        if (Objects.isNull(firstNode)) return null;
        Node<T> element = firstNode;
        firstNode = firstNode.next;
        size--;
        return element.actual;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            System.out.printf("Элемента с заданныи индексом (%d) не существует\n", index);
            return null;
        } else if (index == 0) {
            return firstNode.actual;
        } else {
            T result = null;
            Node<T> resultNode = firstNode.next;
            for (int i = 1; i <= index; i++) {
                result = resultNode.actual;
                resultNode = resultNode.next;
            }
            return result;
        }
    }
}
