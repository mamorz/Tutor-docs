package edu.kit.informatik.example.exception;

/**
 * The {@code InputException} is thrown in case of invalid or inappropriate user
 * input.
 *
 * @author Aurelia Huell
 * @version 1.0
 */

public class InputException extends IllegalArgumentException {

    /**
     * Unique serialVersionUID.
     */
    private static final long serialVersionUID = -73110112117116L;

    /**
     * Constructs a new {@code InputException} without a detail message.
     */
    public InputException() {
        super();
    }

    /**
     * Constructs a new {@code InputException} with a specified detail message.
     *
     * @param message detailed error message
     */
    public InputException(final String message) {
        super(message);
    }
}
