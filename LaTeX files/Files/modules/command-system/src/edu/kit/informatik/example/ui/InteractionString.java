package edu.kit.informatik.example.ui;

/**
 * The {@code InteractionString} enum contains specified strings or characters
 * used in the interaction between user and application.
 *
 * @author Aurelia Huell
 * @version 1.0
 *
 * @see UserInterface
 */

public enum InteractionString {

    /**
     * String for a successful interaction.
     */
    SUCCESS("OK"),

    /**
     * Regular expression of the inner separator of a entity.
     */
    ENTITY_SEPARATOR(","),

    /**
     * Regular expression of a numerical value between (including) 1 and 1000,
     * without leading zeros.
     */
    NUMBER_PATTERN("[1-9][0-9]{0,2}|1000"),

    /**
     * Regular expression of a name with letters of the latin alphabet only.
     */
    NAME_PATTERN("[a-zA-Z]+"),

    /**
     * Regular expression of entity with a certain amount and a certain name
     * separated by exactly one separator.
     *
     * @see InteractionString#ENTITY_SEPARATOR
     * @see InteractionString#NUMBER_PATTERN
     * @see InteractionString#NAME_PATTERN
     */
    ENTITY_PATTERN("(" + NUMBER_PATTERN + ")" + ENTITY_SEPARATOR
            + "(" + NAME_PATTERN + ")"),

    /**
     * String for a detailed exception message, if the user inputs a invalid
     * command.
     */
    COMMAND_INVALID("invalid command"),

    /**
     * String for a detailed exception message, if the specified data contains
     * a entity twice.
     */
    ENTITY_DUPLICATE("the specified entity '%s' appear twice"),

    /**
     * String for a detailed exception message, if the data is not yet
     * implemented.
     */
    MISSING_IMPLEMENTATION("the called data has not been implemented yet");

    /**
     * The representation of the specified string or character.
     */
    private final String representation;

    /**
     * Constructs a new {@code InteractionString} with a specified
     * representation of a string or character used in the interaction between
     * user and application.
     *
     * @param representation representation of a string or character
     */
    InteractionString(final String representation) {
        this.representation = representation;
    }

    /**
     * Returns the string representation of this {@code InteractionString}
     * constant.
     *
     * @return the string representation of this constant
     */
    @Override
    public String toString() {
        return representation;
    }
}
