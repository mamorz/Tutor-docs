/**
 * A single linked list containing point objects The list only allows the same object once in the list.
 * 
 * @author nb
 * @version 1.0
 */
public interface LinkedPointListInterface {
    /*
     * This class is just for educational purposes. It doesn't contain "perfectly" written code and may still contain
     * errors. Please use with caution!!!
     */

    /**
     * Adds a given Point object to the list
     * 
     * @param p
     *            Point object to add
     */
    public void add(Point p);

    /**
     * Adds the given Point object as the first element of the list
     * 
     * @param p
     *            Point object to add
     */
    public void addFirst(Point p);

    /**
     * Adds new Point object at last position
     * 
     * @param pPoint
     *            object to add
     */
    public void addLast(Point p);

    /**
     * Checks if the given Point object is part of the list
     * 
     * @param p
     *            Point object to check for
     * @return true/false indicating if the Point object exists in the list
     */
    public boolean contains(Point p);

    /**
     * Returns the Point object at position <index> in the list. Index starts at 0
     * 
     * @param index
     *            Index of the object to return
     * @return Point object at position <index> in the list (or null if not exist)
     */
    public Point get(int index);

    /**
     * Returns the first object in the list
     * 
     * @return first object in list
     */
    public Point getFirst();

    /**
     * Returns last object in the list
     * 
     * @return last point object in list
     */
    public Point getLast();

    /**
     * Returns the index of the given point object. Index always starts with 0.
     * 
     * @param p
     *            point object to look for
     * @return index of point object (-1 if not found)
     */
    public int indexOf(Point p);

    /**
     * Returns the index of the first similiar point - identified by the same x/y coordinates
     * 
     * @param p
     *            point object to look for
     * @return index of point object (-1 if not found)
     */
    public int indexOfFirstSimilar(Point p);

    /**
     * Is this list empty?
     * 
     * @return true/false indicating if list is empty
     */
    public boolean isEmpty();

    /**
     * Removes the element at <index>
     * 
     * @param index
     *            indicating the index of the element to remove (index always starts with 0)
     */
    public void remove(int index);

    /**
     * Removes a given point object from the ist
     * 
     * @param p
     *            point object to remove
     */
    public void remove(Point p);

    /**
     * Removes the first element
     */
    public void removeFirst();

    /**
     * Removes the last element
     */
    public void removeLast();

    /**
     * Returns the number of elements in the list
     * @return number of elements
     */
    public int size();
    
    /**
     * Returns a string-representation of the list
     */
    @Override
    public String toString();

}