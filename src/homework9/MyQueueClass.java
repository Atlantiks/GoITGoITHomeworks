package homework9;


public class MyQueueClass<T> implements MyQueue<T> {
    Node<T> firstNode;
    Node<T> lastNode;
    int size;
    int maxSize;

    public MyQueueClass(int maxSizesize) {
        clear();
        this.maxSize = maxSizesize;
    }

    @Override
    public void add(T value) {
        if (size == maxSize) {
            System.out.println("Очередь переполнена! Невозможно добавить элемент");
        }
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
        if (index == 0) {
            firstNode = firstNode.next;
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
        return firstNode.actual;
    }

    @Override
    public T poll() {
        Node<T> element = firstNode;

        firstNode = firstNode.next;
        size--;
        return element.actual;
    }
}
