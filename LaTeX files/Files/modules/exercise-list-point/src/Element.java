/**
 * Representation of a list-element for LinkedPointList
 * @author nb
 * @version 1.0
 */
public class Element {
	// Point object contained in this element
	private Point content;
	// following element
	private Element next;

	/**
	 * Constructor to create a list element
	 * @param content object the element holds
	 * @param next the next element in the list
	 */
	public Element(Point content, Element next) {
		this.content = content;
		this.next = next;
	}

	/**
	 * Return the object in the element
	 * @return point-object in the element
	 */
	public Point getContent() {
		return content;
	}

	/**
	 * Sets the elements content to a given point-object
	 * @param content point-object to set as content
	 */
	public void setContent(Point content) {
		this.content = content;
	}

	/**
	 * Get the next list-element following this element
	 * @return next list-element
	 */
	public Element getNext() {
		return next;
	}

	/**
	 * Set the next list-element following this element
	 * @param next List-Element following this element
	 */
	public void setNext(Element next) {
		this.next = next;
	}
}