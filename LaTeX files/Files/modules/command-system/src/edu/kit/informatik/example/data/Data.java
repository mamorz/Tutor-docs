package edu.kit.informatik.example.data;

import edu.kit.informatik.example.exception.DataException;
import edu.kit.informatik.example.ui.InteractionString;
import edu.kit.informatik.example.util.StringUtil;

// Use the Java-API!
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code Data} manages the logic of the application.
 *
 * @author Aurelia Huell
 * @version 1.0
 *
 * @see DataException
 * @see InteractionString
 * @see StringUtil
 *
 * @see ArrayList
 * @see Arrays
 * @see Collections
 * @see HashMap
 * @see List
 * @see Map
 */
public class Data implements DataInterface {

    /**
     * Some minimum and/or maximum boundary given in the specification.
     */
    private static final int SOME_BOUNDARY = 1;

    /**
     * Some storage or game board.
     */
    private List<String> data;

    /**
     * Constructs a new empty {@code Data} application.
     *
     * @throws DataException if the {@code Data} is not implemented
     */
    public Data() throws DataException {
        reset();
    }

    /*
     * Remember to implement everything from the DataInterface.
     *
     * Depending on the task your data package could look like this:
     *
     * GameInterface (interface)
     * Game (class)
     * GameActions (enum)
     * GamePhase (enum)
     *
     * Board: (subpackage)
     *      GameBoard (class)
     *      Field (class)
     *      Point (class)
     *
     * Token: (subpackage)
     *      TokenInterface (interface)
     *      Die (class)
     *      NormalToken (class)
     *      SpecialToken (class)
     *
     * or for a storage implementation instead of a game:
     *
     * DataInterface (interface)
     * Data (class)
     *
     * Helper: (subpackage)
     *      AmountComparator (class)
     *      LexicographicComparator (class)
     *      Traverser (class)
     *
     * Entity: (subpackage)
     *      Entity (class)
     *      SpecialEntity (class)
     *      EntityFragment (class)
     */

    /**
     * Returns a representation of the stored data.
     *
     * <p>Example: {@code Hello, World, ! Boundary: 1}</p>
     *
     * @return the specified string
     */
    @Override
    public String toString() {
        final StringBuilder representation = new StringBuilder()
                .append(StringUtil.toString(data,
                        InteractionString.ENTITY_SEPARATOR.toString()))
                .append(System.lineSeparator())
                .append("Boundary: ")
                .append(SOME_BOUNDARY);
        return representation.toString();
    }

    @Override
    public String reset() {
        data = new ArrayList<>();

        return InteractionString.SUCCESS.toString();
    }

    @Override
    public String addEntity(final String entity) {
        throwNotImplement();

        return InteractionString.SUCCESS.toString();
    }

    /**
     * Don't forget to write some private methods to avoid overly complex code.
     *
     * @throws DataException if the data has not been implemented yet
     */
    private void throwNotImplement() throws DataException {
        throw new DataException(
                InteractionString.MISSING_IMPLEMENTATION.toString());
    }
}
