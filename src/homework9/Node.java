package homework9;

public class Node<T> {
    T actual;
    Node<T> next;

    public Node(T actual, Node<T> next) {
        this.actual = actual;
        this.next = next;
    }

}
