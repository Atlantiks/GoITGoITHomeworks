package homework9;

public class MyHashMap {
    NodeForMap firstNode;
    NodeForMap lastNode;
    int size;

    public MyHashMap() {
        clear();
    }

    public void clear() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    public void put(Object key,Object value) {
        if (size == 0) {
            firstNode = new NodeForMap(key,value,null);
            lastNode = firstNode;
        } else {
            NodeForMap currentNode = firstNode;
            Object storedKey = currentNode.key;
            for (int i = 1; i <= size; i++) {
                if (key == storedKey) {
                    System.out.println("Ключ уже существует");
                    return;
                }
                if (i != size) {
                    currentNode = currentNode.next;
                    storedKey = currentNode.key;
                }
            }
            NodeForMap previous = lastNode;
            lastNode = new NodeForMap(key,value, null);
            previous.next = lastNode;
        }
        size++;
    }

    Object get(Object key) {
        NodeForMap current = firstNode;
        for (int i = 1; i <= size ; i++) {
            if (current.key == key) return current.value;
            current = current.next;
        }
        return null;
    }

    public void remove(Object key) {

        if (size < 1) return;

        if (key == firstNode.key) {
            firstNode = firstNode.next;
            size--;
        } else {
            NodeForMap currentNode = firstNode;
            NodeForMap nextNode = firstNode.next;

            for (int i = 2; i <= size; i++) {
                if (key == currentNode.key) {
                    currentNode.next = nextNode.next;
                    size--;
                } else {
                    currentNode = nextNode;
                    nextNode = nextNode.next;
                }

            }


        }

    }

    public int size() {
        return size;
    }


}
