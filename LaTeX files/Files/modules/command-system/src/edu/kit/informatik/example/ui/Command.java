package edu.kit.informatik.example.ui;

import edu.kit.informatik.example.data.DataInterface;
import edu.kit.informatik.example.exception.DataException;
import edu.kit.informatik.example.exception.InputException;

import edu.kit.informatik.example.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * The {@code Command} class contains specified commands used in the interaction
 * between user and application.
 *
 * @author Aurelia Huell
 * @version 1.0
 *
 * @see UserInterface
 * @see InteractionString
 *
 * @see DataInterface
 * @see DataException
 * @see InputException
 *
 * @see StringUtil
 *
 * @see Matcher
 * @see MatchResult
 * @see Pattern
 */

public enum Command {

    /**
     * Create a new entity for the system with the specified name.
     *
     * @see InteractionString#ENTITY_PATTERN
     */
    ADD_ENTITY("addEntity " + StringUtil.bracket(
                InteractionString.ENTITY_PATTERN.toString())) {
        @Override
        public void execute(final MatchResult matchResult,
                final DataInterface data) throws InputException, DataException {
            this.output = data.addEntity(matchResult.group(1));
        }
    },

    /**
     * Displays every entity of the data.
     *
     * @see DataInterface#toString()
     */
    PRINT("print") {
        @Override
        public void execute(final MatchResult matchResult,
                final DataInterface data) throws InputException, DataException {
            this.output = data.toString();
        }
    },

    /**
     * Displays every entity of the data.
     *
     * @see DataInterface#toString()
     */
    RESET("reset") {
        @Override
        public void execute(final MatchResult matchResult,
                final DataInterface data) throws InputException, DataException {
            this.output = data.reset();
        }
    },

    /**
     * Quits the application.
     */
    QUIT("quit") {
        @Override
        public void execute(final MatchResult matchResult,
                final DataInterface data) {
            quit();
        }
    };

    /**
     * Represents the current output if available.
     */
    protected String output;

    /**
     * Represent whether the application is currently active or not.
     */
    private boolean isActive;

    /**
     * Specifies the regular expression of the command.
     */
    private final Pattern pattern;

    /**
     * Constructs a new {@code Command} with a specified regular expression of
     * the command.
     *
     * @param pattern regular expression of the command
     */
    Command(final String pattern) {
        this.isActive = true;
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * Execute a matching command, if available.
     *
     * @param input    given user input
     * @param data instance of an data application
     * @return the executed command
     * @throws InputException if there is no matching command
     * @throws DataException if there is an internal problem
     */
    public static Command executeMatching(final String input,
            final DataInterface data) throws InputException, DataException {
        for (final Command command : Command.values()) {
            final Matcher matcher = command.pattern.matcher(input);

            if (matcher.matches()) {
                command.execute(matcher, data);

                return command;
            }
        }

        throw new InputException(InteractionString.COMMAND_INVALID.toString());
    }

    /**
     * Execute a specified command.
     *
     * @param matchResult regular expression of the expected input format
     * @param data        instance of a data application
     * @throws InputException in case of invalid or inappropriate command
     *      arguments
     * @throws DataException in case of an internal problem
     */
    public abstract void execute(MatchResult matchResult, DataInterface data)
            throws InputException, DataException;

    /**
     * Returns whether the application is currently active or not.
     *
     * @return true if active, otherwise the other way round
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Returns a string representation of the output.
     *
     * @return a string representation of the output
     */
    public String getOutput() {
        return output;
    }

    /**
     * Quits the application.
     */
    protected void quit() {
        isActive = false;
    }
}
