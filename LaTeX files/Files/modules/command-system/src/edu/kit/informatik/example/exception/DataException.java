package edu.kit.informatik.example.exception;

/**
 * The {@code DataException} is thrown in case of invalid or inappropriate data
 * input.
 *
 * @author Aurelia Huell
 * @version 1.0
 */

public class DataException extends IllegalArgumentException {

    /**
     * Unique serialVersionUID.
     */
    private static final long serialVersionUID = -83107116667119L;

    /**
     * Constructs a new {@code DataException} without a detail message.
     */
    public DataException() {
        super();
    }

    /**
     * Constructs a new {@code DataException} with a specified detail message.
     *
     * @param message detailed error message
     */
    public DataException(final String message) {
        super(message);
    }
}
