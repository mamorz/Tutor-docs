package edu.kit.informatik.example.ui;

/**
 * The {@code UserInterface} defines a interface to interact with the user by
 * using a {@link Data}, specified {@link Command}s and specified
 * {@link InteractionString}s.
 *
 * @author Aurelia Huell
 * @version 1.0
 *
 * @see Command
 * @see InteractionString
 */

public interface UserInterface {

    /**
     * Interact with the user and print output/errors, if necessary.
     */
    void interact();

    /**
     * Print a message to the user.
     *
     * @param message the message to print
     */
    void print(String message);
}
