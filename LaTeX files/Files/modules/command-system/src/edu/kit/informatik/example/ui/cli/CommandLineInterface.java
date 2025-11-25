package edu.kit.informatik.example.ui.cli;

import edu.kit.informatik.Terminal;

import edu.kit.informatik.example.data.DataInterface;
import edu.kit.informatik.example.exception.DataException;
import edu.kit.informatik.example.exception.InputException;

import edu.kit.informatik.example.ui.Command;
import edu.kit.informatik.example.ui.UserInterface;

/**
 * The {@code CommandLineInterface} interact with the user by using a
 * {@link data} and specified commands.
 *
 * @author Aurelia Huell
 * @version 1.0
 *
 * @see Terminal
 *
 * @see DataInterface
 * @see InputException
 *
 * @see Command
 * @see UserInterface
 */

public class CommandLineInterface implements UserInterface {

    /**
     * Instance of an data application.
     */
    private final DataInterface data;

    /**
     * Constructs a new {@code CommandLineInterface} using a
     * {@link DataInterface}.
     *
     * @param data {@link data} to be managed by the
     *      {@code CommandLineInterface}
     */
    public CommandLineInterface(final DataInterface data) {
        this.data = data;
    }

    @Override
    public void interact() {
        Command command = null;

        do {
            try {
                command = Command.executeMatching(readInput(), data);
                final String output = command.getOutput();

                if (command.isActive()) {
                    print(output);
                }
            } catch (InputException e) {
                printError(e.getMessage());
            } catch (DataException e) {
                printError(e.getMessage());
            }
        } while (command == null || command.isActive());
    }

    @Override
    public void print(final String message) {
        if (message != null) {
            Terminal.printLine(message);
        }
    }

    /**
     * Read one line of user input.
     *
     * @return the user input
     */
    private String readInput() {
        return Terminal.readLine();
    }

    /**
     * Prints the detailed exception message to the user.
     *
     * @param message the message of the exception
     */
    private void printError(final String message) {
        if (message != null) {
            Terminal.printError(message);
        }
    }
}
