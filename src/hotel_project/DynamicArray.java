package hotel_project;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynamicArray<T> implements Iterable<T> {
    private T[] array;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public DynamicArray() {
        this.capacity = 10;
        this.array = (T[]) new Object[capacity];
        this.size = 0;
    }

    public void add(T element) {
        if (size == capacity) {
            resize();
        }
        array[size++] = element;
    }

    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
        if (size == capacity) {
            resize();
        }
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        size++;
    }

    private void resize() {
        capacity *= 2;
        @SuppressWarnings("unchecked")
        T[] newArray = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++){
            newArray[i] = array[i];
        }
        array = newArray;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
        return array[index];
    }

    public int size() {
        return size;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
        for (int i = index; i < size - 1; i++){
            array[i] = array[i + 1];
        }
        array[--size] = null;
    }

    public boolean removeElement(T element) {
        for (int i = 0; i < size; i++){
            if (array[i].equals(element)) {
                remove(i);
                return true;
            }
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] result = (T[]) new Object[size];
        for (int i = 0; i < size; i++){
            result[i] = array[i];
        }
        return result;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;
            
            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }
            
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[currentIndex++];
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
