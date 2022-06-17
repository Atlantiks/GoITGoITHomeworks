package homework9;

public class MyStack {
    Object[] stack;
    int position;

    MyStack () {
        position = -1;
    }

    void push(Object value) {
        if (position == 9) {
            System.out.println("Стек заполнен");
        } else {
            stack[++position] = value;
        }
    }

    Object pop() {
        if (position < 0) {
            System.out.println("Стек не загружен");
            return null;
        } else {
            return stack[position--];
        }
    }

    Object peek() {
        if (position < 0) {
            System.out.println("Стек не загружен");
            return null;
        } else {
            return stack[position];
        }
    }

    int size() {
        return stack.length;
    }

    void clear() {
        stack = new Object[10];
        position = -1;
    }

    public void remove(int index) {
        Object[] newArray = new Object[stack.length - 1];
        int j = 0;
        for (int i = 0; i < stack.length; i++) {
            if (i != index) newArray[j++] = stack[i];
        }
        stack = newArray;

    }


}
