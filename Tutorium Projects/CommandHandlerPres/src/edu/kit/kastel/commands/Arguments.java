package edu.kit.kastel.commands;

import edu.kit.kastel.logistics.GameSetup;

import java.util.Arrays;

/**
 * This class represents the arguments of a {@link Command}.
 * @author upgcv
 * @author Programmieren-Team
 */
public class Arguments {
    //Regex defining that a monster's name can include numbers
    private static final String MONSTER_NAME_REGEX = "[a-zA-Z]+#\\d+";
    //Regex checking if the source root path is valid (covers multiple possible path inputs)
    private static final String FILE_PATH_REGEX = "([A-Z]:|.|)/?(([a-zA-Z0-9_., -]+)/)+\3\\.txt";
    private static final String DOUBLE_NUMBER_REGEX = "\\d+(\\.\\d+)?";
    private static final String INTEGER_NUMBER_REGEX = "[1-9]\\d*";
    private static final String SUCCESS_OR_FAIL_REGEX = "[yn]";
    private static final String SUCCESS_REGEX = "y";
    private static final String INVALID_DOUBLE_NUMBER_ENTERED = "Error, you should enter a valid double number!";
    private static final String INVALID_INTEGER_NUMBER_ENTERED = "Error, you should enter a valid integer number!";
    private static final String INVALID_YES_OR_NO_VALUE_ENTERED = "Error, you should enter 'y' or 'n'!";
    private static final String NOT_FILE_PATH_ERROR = "Error, it is not a valid source path!";
    private static final String INVALID_PATH_ARGS_MESSAGE = "Error, path should be entered at once!";
    private static final String TOO_FEW_ARGS = "Error, this act needs at least %d arguments!";
    private static final String INVALID_MONSTER_NAME = "Error, there is no monster named %s!";
    private static final String INVALID_ACTION_NAME_ACTION_COMMAND = "Error, there is no action named %s!";
    private static final String NUMBER_SEPARATOR_IN_MONSTER_NAMES = "#";

    private static final int SUBSTRING_START_FOR_MONSTER_NAME = 0;
    private static final int FIRST_ELEMENT_IN_ARR = 0;
    private static final int MINIMUM_ACTION_ARGS = 1;
    private static final int ACTION_AND_MONSTER_NAME_FOR_ACTION_COMMAND = 2;
    private static final int ONLY_ACTION_NAME_FOR_ACTION_COMMAND = 1;

    private int parseIndex;
    private final String[] inputData;
    private final GameSetup gameSetup;


    /**
     * Constructs a new Arguments instance including the current {@link GameSetup} and an {@code input} to parse.
     * @param gameSetup current gameSetup after reading the config
     * @param input arguments to be parsed
     */
    public Arguments(GameSetup gameSetup, String[] input) {
        this.parseIndex = FIRST_ELEMENT_IN_ARR;
        this.inputData = Arrays.copyOf(input, input.length);
        this.gameSetup = gameSetup.copy();
    }

    private String retrieveArgument() {
        return this.inputData[parseIndex++];
    }

    /**
     * Parses and handles the arguments to conclude, if a correct file path was entered.
     * @return correct file path
     * @throws InvalidArgumentException if the file path was not entered correctly
     */
    public String parseSrcPath() throws InvalidArgumentException {
        if (isExhausted()) {
            throw new InvalidArgumentException(INVALID_PATH_ARGS_MESSAGE);
        }
        String srcPath = retrieveArgument();
        if (!srcPath.matches(FILE_PATH_REGEX)) {
            throw new InvalidArgumentException(NOT_FILE_PATH_ERROR);
        }
        return srcPath;
    }

    /**
     * Parses and handles monsters' names entered.
     * @return correct monsters' names array
     * @throws InvalidArgumentException if there are no monster with a name entered found in current {@link GameSetup}
     */
    public String[] parseMonsterNames() throws InvalidArgumentException {
        while (!isExhausted()) {
            String monsterNameToCheck = retrieveArgument();
            if (this.gameSetup.getMonster(monsterNameToCheck) == null) {
                throw new InvalidArgumentException(INVALID_MONSTER_NAME.formatted(monsterNameToCheck));
            }
        }
        return Arrays.copyOf(this.inputData, this.inputData.length);
    }

    /**
     * Parses and handles a name of an action and optimal target monster's name.
     * @return valid action name and an optimal valid target monster's name
     * @throws InvalidArgumentException if arguments were not valid
     */
    public String[] parseSetAction() throws InvalidArgumentException {
        if (isExhausted()) {
            throw new InvalidArgumentException(TOO_FEW_ARGS.formatted(MINIMUM_ACTION_ARGS));
        }
        int outputDataLength = ONLY_ACTION_NAME_FOR_ACTION_COMMAND;
        String lineToCheck = retrieveArgument();
        if (this.gameSetup.getAction(lineToCheck) == null) {
            throw new InvalidArgumentException(INVALID_ACTION_NAME_ACTION_COMMAND.formatted(lineToCheck));
        }
        if (!isExhausted()) {
            outputDataLength = ACTION_AND_MONSTER_NAME_FOR_ACTION_COMMAND;
            lineToCheck = retrieveArgument();
            if (lineToCheck.matches(MONSTER_NAME_REGEX)) {
                lineToCheck = lineToCheck.substring(SUBSTRING_START_FOR_MONSTER_NAME,
                        lineToCheck.indexOf(NUMBER_SEPARATOR_IN_MONSTER_NAMES));
            }
            if (this.gameSetup.getMonster(lineToCheck) == null) {
                throw new InvalidArgumentException(INVALID_MONSTER_NAME.formatted(lineToCheck));
            }
        }
        return Arrays.copyOf(this.inputData, outputDataLength);
    }

    /**
     * Parses and handles a double number input.
     * @return a double if parsing was successful
     * @throws InvalidArgumentException if parsing was not possible
     */
    public double parseRandomDouble() throws InvalidArgumentException {
        if (isExhausted()) {
            throw new InvalidArgumentException(TOO_FEW_ARGS.formatted(MINIMUM_ACTION_ARGS));
        }
        String doubleToCheck = retrieveArgument();
        if (doubleToCheck.matches(DOUBLE_NUMBER_REGEX)) {
            return Double.parseDouble(doubleToCheck);
        }
        throw new InvalidArgumentException(INVALID_DOUBLE_NUMBER_ENTERED);
    }

    /**
     * Parses and handles an integer number input.
     * @return an integer if parsing was successful
     * @throws InvalidArgumentException if parsing was not possible
     */
    public int parseRandomInteger() throws InvalidArgumentException {
        if (isExhausted()) {
            throw new InvalidArgumentException(TOO_FEW_ARGS.formatted(MINIMUM_ACTION_ARGS));
        }
        String integerToCheck = retrieveArgument();
        if (integerToCheck.matches(INTEGER_NUMBER_REGEX)) {
            return Integer.parseInt(integerToCheck);
        }
        throw new InvalidArgumentException(INVALID_INTEGER_NUMBER_ENTERED);
    }

    /**
     * Parses and handles a double number input.
     * @return a boolean if parsing was successful
     * @throws InvalidArgumentException if parsing was not possible
     */
    public boolean parseSuccessOrFail() throws InvalidArgumentException {
        if (isExhausted()) {
            throw new InvalidArgumentException(TOO_FEW_ARGS.formatted(MINIMUM_ACTION_ARGS));
        }
        String doubleToCheck = retrieveArgument();
        if (doubleToCheck.matches(SUCCESS_OR_FAIL_REGEX)) {
            return doubleToCheck.matches(SUCCESS_REGEX);
        }
        throw new InvalidArgumentException(INVALID_YES_OR_NO_VALUE_ENTERED);
    }
    /**
     * Returns whether all provided arguments have been consumed.
     *
     * @return {@code true} if all arguments have been consumed, {@code false} otherwise
     */
    public boolean isExhausted() {
        return this.parseIndex >= this.inputData.length;
    }
}
