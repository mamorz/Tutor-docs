package edu.kit.kastel.commands;

/**
 * This interface provides a command instance constructed with the given arguments.
 * @param <T> the type of the value that is handled by the command
 *
 * @author Programmieren-Team
 */
public interface CommandProvider<T> {

    /**
     * Constructs a new command instance with the given arguments.
     * @param arguments the arguments to be used for constructing the command
     * @return the constructed command
     * @throws InvalidArgumentException if arguments throws this Exception
     * @see Arguments
     */
    Command<T> provide(Arguments arguments) throws InvalidArgumentException;
}