/**
 * Array list implementation implemented from Prof. Brizan's slides.
 * 
 * @param <T> element type
 */
public class ArrayList<T> implements List<T> {
    private T[] arr;
    private int size;

    /**
     * Constructor
     */
    public ArrayList() {
        arr = (T[]) new Object[10];
        size = 0;
    }

    private void grow_array () {
        T [] new_arr = (T[]) new Object[arr.length * 3 / 2 + 1];
        for (int i = 0; i < arr.length; i++) {
            new_arr[i] = arr[i];
        }
        arr = new_arr;
    }

    /**
     * Current number of elements in the list.
     * @return size
     */
    public int size () {
        return size;
    }

    /**
     * Return element at given index.
     * @param index retrieval index
     * @return element at index
     * @throws Exception on invalid index
     */
    public T get (int index) throws Exception {
        if (index < 0 || index >= size) {
            throw new Exception("Invalid position");
        }
        return arr[index];
    }

    /**
     * Add element to the end of the list.
     * @param element element to add
     * @return true on success
     */
    public boolean add (T element) {
        if (size == arr.length) {
            grow_array();
        }
        arr[size++] = element;
        return true;
    }

    /**
     * Insert element at the given index.
     * @param index insertion index
     * @param element element to insert
     * @throws Exception on invalid index
     */
    public void add (int index, T element) throws Exception {
        if (index < 0 || index >= size) {
            throw new Exception("Invalid position");
        }
        for (int i = size; i > index; i--) {
            arr[i] = arr[i - 1];
        }
        arr[index] = element;
        size++;
    }

    /**
     * Remove and return element at index.
     * @param index index to remove
     * @return removed element
     * @throws Exception on invalid index
     */
    public T remove (int index) throws Exception {
        if (index < 0 || index >= size) {
            throw new Exception("Invalid position");
        }
        T removed = arr[index];
        for (int i = index; i < size - 1; i++) {
            arr[i] = arr[i + 1];
        }
        size--;
        return removed;
    }
    
    /**
     * Return an iterator for this list.
     * @return iterator
     */
    public Iterator<T> iterator () {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        private int nextIndex = 0;

        public boolean hasNext () {
            return nextIndex < size && nextIndex >= 0;
        }

        public T next () {
            return arr[nextIndex++];
        }
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        for (int i = 0; i < size; i++) arr[i] = null;
        size = 0;
    }
}
