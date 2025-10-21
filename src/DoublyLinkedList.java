/**
 * Doubly-linked list implementation. Each node points to prev and next.
 * 
 * Implemeted from Prof. Brizan's slides.
 * 
 * @param <T> element type
 */
public class DoublyLinkedList<T> implements List<T>{
    private class Node {
        T data;
        Node next;
        Node prev;

        public Node(T value) {
            data = value;
            next = null;
            prev = null;
        }
    }

    int size;
    Node head;

    /**
     * Constructor
     */
    public DoublyLinkedList() {
        size = 0;
        head = null;
    }

    /**
     * Current number of elements in the list.
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Add element to the end of the list.
     * @param element element to add
     * @return true on success
     */
    public boolean add(T element) {
        if (size == 0) {
            head = new Node(element);
            ++size;
            return true;
        }
        Node node = head;
        while (node.next != null) {
            node = node.next;
        }
        Node newlast = new Node(element);
        newlast.prev = node;
        node.next = newlast;
        ++size;
        return true;
    }

    /**
     * Insert element at the given index.
     * @param index insertion index
     * @param element element to insert
     * @throws Exception on invalid index
     */
    public void add(int index, T element) throws Exception {
        if( index < 0 || index > size) {
            throw new Exception("Invalid position");
        }
        if (index == 0) {
            Node node = new Node(element);
            node.next = head;
            if (head != null) {
                head.prev = node;
            }
            head = node;
        } else {
            Node node = new Node(element);
            Node prev = head;
            for (int i = 0; i < index-1; i++) {
                prev = prev.next;
            }
            node.next = prev.next;
            if (prev.next != null) {
                prev.next.prev = node;
            }
            node.prev = prev;
            prev.next = node;
        }
        size++;
    }

    /**
     * Remove and return element at index.
     * @param index index to remove
     * @return removed element
     * @throws Exception on invalid index
     */
    public T remove(int index) throws Exception {
        if( index < 0 || index >= size) {
            throw new Exception("Invalid position");
        }
        if (index == 0) {
            Node node = head;
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
            size--;
            return node.data;
        } else {
            Node prev = head;
            for (int i = 0; i < index-1; i++) {
                prev = prev.next;
            }
            Node node = prev.next;
            prev.next = node.next;
            if (node.next != null) {
                node.next.prev = prev;
            }
            size--;
            return node.data;
        }
    }

    /**
     * Return element at given index.
     * @param index retrieval index
     * @return element at index
     * @throws Exception on invalid index
     */
    public T get(int index) throws Exception {
        if( index < 0 || index >= size) {
            throw new Exception("Invalid position");
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Return an iterator for this list.
     * @return iterator
     */
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    
    private class ListIterator implements Iterator<T> {
        private Node node = head;

        public boolean hasNext() {
            return node != null;
        }

        public T next() {
            if (node == null) throw new RuntimeException("No more elements");
            T val = node.data;
            node = node.next;
            return val;
        }
    }
}
