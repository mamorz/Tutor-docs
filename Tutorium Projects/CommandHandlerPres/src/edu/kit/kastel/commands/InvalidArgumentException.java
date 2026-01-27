package edu.kit.kastel.commands;

/**
 * Signals that parsing/retrieving an argument failed.
 *
 * @author upgcv
 */
public class InvalidArgumentException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message. The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     */
    public InvalidArgumentException(String message) {
        super(message);
    }
}
