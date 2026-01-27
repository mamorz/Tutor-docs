package edu.kit.kastel.commands;

/**
 * This interface represents a keyword that can be used to identify a command.
 * @param <T> the type of the value that is handled by the command
 *
 * @author Programmieren-Team
 * @author upgcv
 */
public interface Keyword<T> extends CommandProvider<T> {

    /**
     * Returns whether the keyword matches the given command.
     *
     * @param command the command to be checked
     * @return whether the keyword matches the command
     */
    boolean matches(String command);

    /**
     * Returns command arguments made from the input line depending on the keyword.
     * @param command the full input line to work with
     * @return an array with command arguments to be used for the command, being represented by this keyword
     */
    String[] retrieveCommandArgs(String command);
}