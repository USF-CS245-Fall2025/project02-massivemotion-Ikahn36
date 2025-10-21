/**
 * List interface given by the assignment.
 * Implementations provide basic add/get/remove functions
 * and an iterator for traversal.
 *
 * @param <T> element type
 */
public interface List<T> {

    /**
     * Insert element at the given index.
     * @param index insertion index
     * @param element element to insert
     * @throws Exception on invalid index
     */
    public void add (int index, T element) throws Exception;

    /**
     * Add element to the end of the list.
     * @param element element to add
     * @return true on success
     */
    public boolean add (T element);

    /**
     * Return element at given index.
     * @param index retrieval index
     * @return element at index
     * @throws Exception on invalid index
     */
    public T get (int index) throws Exception;

    /**
     * Remove and return element at index.
     * @param index index to remove
     * @return removed element
     * @throws Exception on invalid index
     */
    public T remove (int index) throws Exception;

    /**
     * Current number of elements in the list.
     * @return size
     */
    public int size ();

    /**
     * Return an iterator for this list.
     * @return iterator
     */
    public Iterator<T> iterator();

    /**
     * Remove all elements from the list.
     */
    public void clear();
}
