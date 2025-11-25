/**
 * A single linked list containing point objects The list only allows the same object once in the list.
 * 
 * @author nb
 * @version 1.0
 */
public class LinkedPointList implements LinkedPointListInterface {
    /*
     * This class is just for educational purposes. It doesn't contain "perfectly" written code and may still contain
     * errors. Please use with caution!!!
     */

    /** Head element */
    private Element head;

    /**
     * Constructor to create an empty list
     */
    public LinkedPointList() {

    }

    /**
     * Contructor to create the list and add a given Point object right away
     * 
     * @param point
     *            The Point object to add to the new list
     */
    public LinkedPointList(Point point) {
        this.head = new Element(point, null);
    }

    /**
     * Adds a given Point object to the list
     * 
     * @param p
     *            Point object to add
     */
    public void add(Point p) {
        // only add if it doesn't exist yet
        if (!this.contains(p)) {
            this.addLast(p);
        }
    }

    /**
     * Adds the given Point object as the first element of the list
     * 
     * @param p
     *            Point object to add
     */
    public void addFirst(Point p) {
        // only add if it doesn't exist yet
        if (!this.contains(p)) {
            Element newElement = new Element(p, head);
            if (this.head != null) {
                // we already have a head-element. preserve it as part of the list by making it 2nd element
                newElement.setNext(this.head);
            }
            this.head = newElement;
        }
    }

    /**
     * Adds new Point object at last position
     * 
     * @param pPoint
     *            object to add
     */
    public void addLast(Point p) {
        // only add if it doesn't exist yet
        if (!this.contains(p)) {
            Element last = getLastElement();
            Element newElement = new Element(p, null);
            if (last == null) {
                this.head = newElement;
            } else {
                last.setNext(newElement);
            }
        }
    }

    /**
     * Checks if the given Point object is part of the list
     * 
     * @param p
     *            Point object to check for
     * @return true/false indicating if the Point object exists in the list
     */
    public boolean contains(Point p) {
        boolean found = false;
        Element temp = this.head;
        while (temp != null && !found) {
            if (temp.getContent() == p) {
                found = true;
            }
            temp = temp.getNext();
        }
        return found;
    }

    /**
     * Returns the Point object at position <index> in the list. Index starts at 0
     * 
     * @param index
     *            Index of the object to return
     * @return Point object at position <index> in the list (or null if not exist)
     */
    public Point get(int index) {
        Element temp = this.getElement(index);
        if (temp != null) {
            return temp.getContent();
        } else {
            return null;
        }
    }

    /**
     * Returns the first object in the list
     * 
     * @return first object in list
     */
    public Point getFirst() {
        if (this.head != null) {
            return head.getContent();
        }
        return null;
    }

    /**
     * Returns last object in the list
     * 
     * @return last point object in list
     */
    public Point getLast() {
        Element last = this.getLastElement();
        if (last != null) {
            return last.getContent();
        }
        return null;
    }

    /**
     * Returns the index of the given point object. Index always starts with 0.
     * 
     * @param p
     *            point object to look for
     * @return index of point object (-1 if not found)
     */
    public int indexOf(Point p) {
        int index = -1;
        boolean found = false;
        Element temp = this.head;
        while (temp != null && !found) {
            index++;
            if (temp.getContent() == p) {
                found = true;
            }
            temp = temp.getNext();
        }
        if (!found) {
            return -1;
        } else {
            return index;
        }
    }

    /**
     * Returns the index of the first similiar point - identified by the same x/y coordinates
     * 
     * @param p
     *            point object to look for
     * @return index of point object (-1 if not found)
     */
    public int indexOfFirstSimilar(Point p) {
        int index = -1;
        boolean found = false;
        Element temp = this.head;
        while (temp != null && !found) {
            index++;
            if (temp.getContent().equals(p)) {
                found = true;
            }
            temp = temp.getNext();
        }
        if (!found) {
            return -1;
        } else {
            return index;
        }
    }

    /**
     * Is this list empty?
     * 
     * @return true/false indicating if list is empty
     */
    public boolean isEmpty() {
        if (this.head == null) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Removes the element at <index>
     * 
     * @param index
     *            indicating the index of the element to remove (index always starts with 0)
     */
    public void remove(int index) {
        if (index == 0) {
            this.removeFirst();
        } else {
            if (this.getElement(index) != null) { // does the element exist at all?
                Element prev = this.getElement(index - 1); // get the element before the element to remove
                prev.setNext(prev.getNext().getNext());
            }
        }
    }

    /**
     * Removes a given point object from the ist
     * 
     * @param p
     *            point object to remove
     */
    public void remove(Point p) {
        int i = this.indexOf(p);
        if (i >= 0) {
            this.remove(i);
        }
    }

    /**
     * Removes the first element
     */
    public void removeFirst() {
        // no need to check if there is a 2nd element, because if there is none, getNext will return null
        // which is exactly what we need to set then
        this.head = this.head.getNext();
    }

    /**
     * Removes the last element
     */
    public void removeLast() {
        this.remove(this.size() - 1);
    }

    /**
     * Returns the number of elements in the list
     * 
     * @return number of elements
     */
    public int size() {
        int size = 0;
        if (isEmpty() == false) {
            Element temp = this.head;
            while (temp != null) {
                size++;
                temp = temp.getNext();
            }
        }
        return size;
    }

    /**
     * Returns a string-representation of the list
     */
    public String toString() {
        String out = "";
        Element temp = this.head;
        while (temp != null) {
            out += temp.getContent().toString() + "; ";
            temp = temp.getNext();
        }
        return out;
    }

    // get the last element
    private Element getLastElement() {
        Element temp = this.head;
        while (temp != null && temp.getNext() != null) {
            temp = temp.getNext();
        }
        return temp;
    }

    // get the Element at position index
    private Element getElement(int index) {
        Element temp = this.head;
        boolean found = false;
        int i = 0;
        while (temp != null && !found && i < index) {
            i++;
            temp = temp.getNext();
        }
        return temp;
    }

}