package homework9;


public class MyArrayList<T> implements MyList<T> {
    private Object[] array;
    private int positionToAddTo;

    public MyArrayList() {
        clear();
    }

    @Override
    public void add(T value) {
        if (positionToAddTo >= array.length) {
            Object[] newArray = new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            this.array = newArray;
        }
        array[positionToAddTo++] = value;
    }

    @Override
    public void remove(int index) {
        if (index >= positionToAddTo || index < 0) {
            System.out.printf("Элемента с заданныи индексом (%d) не существует\n", index);
            return;
        }
        Object[] newArray = new Object[array.length - 1];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (i != index) newArray[j++] = array[i];
        }
        array = newArray;
        positionToAddTo--;
    }

    @Override
    public void clear() {
        this.array = new Object[10];
        positionToAddTo = 0;
    }

    @Override
    public int size() {
        return positionToAddTo;
    }

    @Override
    public T get(int index) {
        if (index >= array.length || index < 0) {
            System.out.printf("Элемента с заданныи индексом (%d) не существует\n", index);
            return null;
        } else {
            return (T)array[index];
        }
    }
}
