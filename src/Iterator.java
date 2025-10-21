/**
 * Iterator interface used by the custom List implementations.
 * 
 * @param <T> element type
 */
public interface Iterator<T> {
    /**
     * @return true if there are more elements
     */
    public boolean hasNext();

    /**
     * Return the next element. Implementations may throw a runtime
     * exception if called when no next element exists.
     * @return next element
     */
    public T next();
}
