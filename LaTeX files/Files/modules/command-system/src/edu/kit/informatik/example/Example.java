package edu.kit.informatik.example;

import edu.kit.informatik.example.data.DataInterface;
import edu.kit.informatik.example.data.Data;
import edu.kit.informatik.example.ui.cli.CommandLineInterface;
import edu.kit.informatik.example.ui.UserInterface;

/**
 * The {@code Example} class initializes the {@link Data} application and
 * invokes a {@link CommandLineInterface} to start the user interaction.
 *
 * @author Aurelia Huell
 * @version 1.0
 *
 * @see DataInterface
 * @see Data
 * @see UserInterface
 * @see CommandLineInterface
 */
public final class Example {

    /**
     * Private Constructor to prevent a object creation.
     */
    private Example() {
    }

    /**
     * This {@code main} method is the entry point for this application, which
     * initializes a Data application and starts the user interaction.
     *
     * @param args the command line arguments - everything passed is ignored
     */
    public static void main(final String[] args) {
        final DataInterface data = new Data();
        new CommandLineInterface(data).interact();
    }
}
