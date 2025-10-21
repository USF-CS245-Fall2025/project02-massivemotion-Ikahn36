public class DummyHeadLinkedList<T> implements List<T> {
    /**
     * Linked list implementation that uses a dummy head node to simplify
     * insertion/removal logic at the head.
     * 
     * Implemented from Prof. Brizan's slides.
     * 
     * @param <T> element type
     */
    private class Node {
        T data;
        Node next;

        public Node(T value) {
            data = value;
            next = null;
        }
    }

    int size;
    Node head;

    /**
     * Constructor
     */
    public DummyHeadLinkedList() {
        size = 0;
        head = new Node(null);
    }

    /**
     * Current number of elements in the list.
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        Node cur = head.next;
        head.next = null;
        while (cur != null) {
            Node n = cur.next;
            cur.data = null;
            cur.next = null;
            cur = n;
        }
        size = 0;
    }

    /**
     * Add element to the end of the list.
     * @param element element to add
     * @return true on success
     */
    public boolean add(T element) {
        Node node = head;
        while (node.next != null) {
            node = node.next;
        }
        Node newlast = new Node(element);
        newlast.next = null;
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
        Node prev = head;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node node = new Node(element);
        node.next = prev.next;
        prev.next = node;
        size++;
    }

    /**
     * Return element at given index.
     * @param index retrieval index
     * @return element at index
     * @throws Exception on invalid index
     */
    public T get(int index) throws Exception {
        if (index < 0 || index >= size) {
            throw new Exception("Invalid position");
        }
        Node current = head.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Return element at given index.
     * @param index retrieval index
     * @return element at index
     * @throws Exception on invalid index
     */
    public T remove(int index) throws Exception {
        if (index < 0 || index >= size) {
            throw new Exception("Invalid position");
        }
        Node prev = head;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node toRemove = prev.next;
        prev.next = toRemove.next;
        size--;
        return toRemove.data;
    }

    /**
     * Return an iterator for this list.
     * @return iterator
     */
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        private Node node = head.next;

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