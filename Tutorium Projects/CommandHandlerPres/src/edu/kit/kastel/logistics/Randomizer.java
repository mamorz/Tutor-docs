package edu.kit.kastel.logistics;

import edu.kit.kastel.commands.InvalidArgumentException;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;

/**
 * A class that provides randomization methods for various types of values,
 * such as integers, doubles, and boolean outcomes. It also supports debug mode for
 * testing purposes and provides methods for generating random values within specified bounds.
 *
 * @author upgcv
 */
public class Randomizer {
    private static final String NOT_THIS_TYPE_EXPECTED_MESSAGE = "Error, it was not this type of value expected!";
    private static final String INPUT_IS_NOT_BETWEEN_BORDERS_MESSAGE = "Error, this value is out of range!";
    private static final String QUESTION_NUMBER_FIRST_MESSAGE_PART = "Decide %s";
    private static final String QUESTION_INTEGER_SECOND_MESSAGE_PART = ": an integer between %d and %d?";
    private static final String QUESTION_DOUBLE_SECOND_MESSAGE_PART = ": a number between %s and %s?";
    private static final String QUESTION_BOOLEAN_MESSAGE = "Decide %s: yes or no? (y/n)";

    private static final int MAX_PERCENTAGE_RATE = 100;
    private static final int INTEGER_OFFSET_FOR_UPPER_BOUND = 1;

    private final Queue<Boolean> currentBooleanQueue = new LinkedList<>();
    private final Queue<Integer> currentIntegerQueue = new LinkedList<>();
    private final Queue<Double> currentDoubleQueue = new LinkedList<>();
    private final Queue<Boolean> booleansOnTurn = new LinkedList<>();
    private final Queue<Integer> integersOnTurn = new LinkedList<>();
    private final Queue<Double> doublesOnTurn = new LinkedList<>();
    private String pendingMessage;
    private final Random random;
    private boolean isDebug;
    private DebugExpectedData expectedData = null;
    private boolean isFirstBooleanForEndCondition;

    /**
     * Constructs a new Randomizer instance with a default random seed.
     */
    public Randomizer() {
        this.random = new Random();
    }

    /**
     * Constructs a new Randomizer instance with a specified seed for reproducible results.
     * @param seed the seed value for the random number generator.
     */
    public Randomizer(long seed) {
        this.random = new Random(seed);
    }
    /**
     * Determines whether a success or failure occurs based on a given success rate.
     * The success rate should be a value between 0 and 100, where 100 is always success.
     *
     * @param successRate the success rate (between 0 and 100).
     * @return an Optional value of a Boolean, which {@code isEmpty()} if this value needs to be debugged by the user,
     *     otherwise the next Boolean value to proceed processing the application.
     */
    public Optional<Boolean> getSuccessOrFail(double successRate) {
        if (isDebug) {
            if (this.currentBooleanQueue.isEmpty()) {
                setBooleanPending();
                return Optional.empty();
            } else {
                return Optional.of(this.currentBooleanQueue.remove());
            }
        } else {
            return Optional.of(random.nextDouble() * MAX_PERCENTAGE_RATE <= successRate);
        }
    }
    /**
     * Generates a random integer between two specified integers (inclusive of the first, exclusive of the second).
     *
     * @param firstDigit the lower bound (inclusive).
     * @param secondDigit the upper bound (exclusive).
     * @return an Optional value of an Integer inbounds, which {@code isEmpty()} if this value needs to be debugged by the user,
     *     otherwise the next Integer value inbounds to proceed processing the application.
     */
    public Optional<Integer> getRandomInt(int firstDigit, int secondDigit) {
        if (isDebug) {
            if (this.currentIntegerQueue.isEmpty()) {
                setIntegerPending(firstDigit, secondDigit);
                return Optional.empty();
            } else {
                return Optional.of(this.currentIntegerQueue.remove());
            }
        } else {
            return Optional.of(random.nextInt(firstDigit, secondDigit + INTEGER_OFFSET_FOR_UPPER_BOUND));
        }
    }
    /**
     * Generates a random double between two specified doubles (inclusive of the first, exclusive of the second).
     *
     * @param firstDigit the lower bound (inclusive).
     * @param secondDigit the upper bound (exclusive).
     * @return an Optional value of a Double, which {@code isEmpty()} if this value needs to be debugged by the user,
     *     otherwise the next Double value inbounds to proceed processing the application.
     */
    public Optional<Double> getRandomDouble(double firstDigit, double secondDigit) {
        if (isDebug) {
            if (this.currentDoubleQueue.isEmpty()) {
                setDoublePending(firstDigit, secondDigit);
                return Optional.empty();
            } else {
                return Optional.of(this.currentDoubleQueue.remove());
            }
        } else {
            return Optional.of(random.nextDouble(firstDigit, secondDigit));
        }
    }

    /**
     * Returns the type of data randomizer is expecting from the user during debug mode.
     * @return {@code null} if the randomizer is not expecting any data, otherwise the expected data type indicated with
     *      {@link DebugType} enum keyword
     * @see Competition
     */
    public DebugType expectedValueType() {
        return this.expectedData == null ? null : this.expectedData.typeToExpect();
    }

    /**
     * Turns the debug mode on.
     */
    public void turnDebugOn() {
        this.isDebug = true;
    }

    /**
     * Indicates if the debug mode is on in this application.
     * @return true if the debug is on, false otherwise
     */
    public boolean isDebugOn() {
        return this.isDebug;
    }

    /**
     * Gets the message that is to be displayed before next input in the application. Indicates what a user should decide
     *      in the application.
     * @return Message to be displayed as pending by the {@link Competition}
     */
    public String getPendingMessage() {
        return this.pendingMessage;
    }

    /**
     * Resets collected data for the current move to proceed on the next during a debug mode in the {@link Competition}.
     */
    public void resetMove() {
        this.expectedData = null;
        this.booleansOnTurn.clear();
        this.integersOnTurn.clear();
        this.doublesOnTurn.clear();
        this.currentBooleanQueue.clear();
        this.currentIntegerQueue.clear();
        this.currentDoubleQueue.clear();
        this.isFirstBooleanForEndCondition = false;
    }

    private void refillCurrentQueues() {
        this.currentBooleanQueue.addAll(booleansOnTurn);
        this.currentDoubleQueue.addAll(doublesOnTurn);
        this.currentIntegerQueue.addAll(integersOnTurn);
    }

    /**
     * Indicates if the process that is being processed is being done at the first time during this turn.
     * @return true if it is the first time, false otherwise
     */
    public boolean isProcessFirstExecution() {
        return this.currentBooleanQueue.isEmpty() && this.currentDoubleQueue.isEmpty() && this.currentIntegerQueue.isEmpty();
    }

    /**
     * Sets the first Boolean in the queue responsible for the end condition question.
     */
    public void setFirstBooleanForEndCondition() {
        this.isFirstBooleanForEndCondition = true;
    }

    /**
     * Skips the next boolean value if needed for the end condition effect.
     */
    public void skipFirstBooleanValue() {
        if (this.isFirstBooleanForEndCondition) {
            getSuccessOrFail(MAX_PERCENTAGE_RATE); //to skip the first element for the end condition effect, which executes only once.
        }
    }

    private void setBooleanPending() {
        this.expectedData = new DebugExpectedData(DebugType.BOOLEAN, Integer.MAX_VALUE, Integer.MIN_VALUE);
        this.pendingMessage = QUESTION_BOOLEAN_MESSAGE;
    }

    private void setIntegerPending(int bottomBorder, int upperBorder) {
        this.expectedData = new DebugExpectedData(DebugType.INTEGER, bottomBorder, upperBorder);
        this.pendingMessage = QUESTION_NUMBER_FIRST_MESSAGE_PART
                + QUESTION_INTEGER_SECOND_MESSAGE_PART.formatted(bottomBorder, upperBorder);
    }

    private void setDoublePending(double bottomBorder, double upperBorder) {
        this.expectedData = new DebugExpectedData(DebugType.DOUBLE, bottomBorder, upperBorder);
        this.pendingMessage = QUESTION_NUMBER_FIRST_MESSAGE_PART
                + QUESTION_DOUBLE_SECOND_MESSAGE_PART.formatted(bottomBorder, upperBorder);
    }

    /**
     * Sets the next Boolean value for the competition round in the second phase to proceed. Is usually used to set the data
     *      during the debug phase.
     * @param nextBoolean the next Boolean that is being asked from the user to determine if the effect/action/hit is to succeed
     *                    or not
     * @throws InvalidArgumentException if the expected data type is not corresponding with the given data type (here:
     *      the expected data is not of Boolean type)
     */
    public void setNextValue(boolean nextBoolean) throws InvalidArgumentException {
        if (this.expectedData.typeToExpect() != DebugType.BOOLEAN) {
            throw new InvalidArgumentException(NOT_THIS_TYPE_EXPECTED_MESSAGE);
        }
        this.booleansOnTurn.add(nextBoolean);
        refillCurrentQueues();
    }
    /**
     * Sets the next Integer value for the competition round in the second phase to proceed. Is usually used to set the data
     *      during the debug phase.
     * @param nextInteger the next Integer that is being asked from the user to determine a number of rounds/times an effect
     *                   is going to last/be repeated
     * @throws InvalidArgumentException if the expected data type is not corresponding with the given data type (here:
     *      the expected data is not of Integer type) or if the given data is not inbounds of the expected value
     */
    public void setNextValue(int nextInteger) throws InvalidArgumentException {
        if (this.expectedData.typeToExpect() != DebugType.INTEGER) {
            throw new InvalidArgumentException(NOT_THIS_TYPE_EXPECTED_MESSAGE);
        }
        if (this.expectedData.bottomBorder() > nextInteger || this.expectedData.upperBorder() < nextInteger) {
            throw new InvalidArgumentException(INPUT_IS_NOT_BETWEEN_BORDERS_MESSAGE);
        }
        this.integersOnTurn.add(nextInteger);
        refillCurrentQueues();
    }

    /**
     * Sets the next Double value for the competition round in the second phase to proceed. Is usually used to set the data
     *      during the debug phase.
     * @param nextDouble the next Double that is being asked from the user to determine e.g. the random factor
     *                  in damage calculations
     * @throws InvalidArgumentException if the expected data type is not corresponding with the given data type (here:
     *      the expected data is not of Double type) or if the given data is not inbounds of the expected value
     */
    public void setNextValue(double nextDouble) throws InvalidArgumentException {
        if (this.expectedData.typeToExpect() != DebugType.DOUBLE) {
            throw new InvalidArgumentException(NOT_THIS_TYPE_EXPECTED_MESSAGE);
        }
        if (this.expectedData.bottomBorder() > nextDouble || this.expectedData.upperBorder() < nextDouble) {
            throw new InvalidArgumentException(INPUT_IS_NOT_BETWEEN_BORDERS_MESSAGE);
        }
        this.doublesOnTurn.add(nextDouble);
        refillCurrentQueues();
    }
}
