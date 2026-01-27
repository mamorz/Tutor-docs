package edu.kit.kastel.commands;
/*
 * Copyright (c) 2025, KASTEL. All rights reserved.
 */


import edu.kit.kastel.logistics.DebugType;

/**
 * The result of a command execution.
 *
 * @author Programmieren-Team
 * @author upgcv
 */
public final class Result {

    private final ResultType type;
    private final String message;
    private final String debugMessageLabel;
    private final DebugType typeToDebug;

    private Result(ResultType type, String message) {
        this(type, message, null, "");
    }

    private Result(ResultType type, DebugType typeToDebug, String debugMessageLabel) {
        this(type, "", typeToDebug, debugMessageLabel);
    }

    private Result(ResultType type, String message,  DebugType typeToDebug, String debugMessageLabel) {
        this.type = type;
        this.message = message;
        this.typeToDebug = typeToDebug;
        this.debugMessageLabel = debugMessageLabel;
    }


    /**
     * Returns the type of the result.
     * @return the type of the result
     */
    public ResultType getType() {
        return type;
    }

    /**
     * Returns the message of the result.
     * @return the message of the result
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the type of info debugging needs right now.
     * @return one of the {@link Result} types if this result has this info, {@code null} otherwise
     */
    public DebugType getTypeToDebug() {
        return typeToDebug;
    }

    /**
     * Returns the type of info debugging needs right now.
     * @return label of the next debug message if this result has one, an empty string otherwise
     */
    public String getDebugMessageLabel() {
        return debugMessageLabel;
    }

    /**
     * Creates a new error result with the given message. The {@link #getType()} method will return {@link ResultType#FAILURE}.
     * @param message the message of the result
     * @return a new error result
     */
    public static Result failed(String message) {
        return new Result(ResultType.FAILURE, message);
    }

    /**
     * Creates a new error result without any given message. The {@link #getType()} method will return {@link ResultType#FAILURE}.
     * @return a new error result
     */
    public static Result failed() {
        return new Result(ResultType.FAILURE, "");
    }

    /**
     * Creates a new success result without any message. The {@link #getType()} method will return {@link ResultType#SUCCESS},
     * the {@link #getMessage()} method will return {@code null}.
     * @return a new success result without any message
     */
    public static Result success() {
        return new Result(ResultType.SUCCESS, "");
    }

    /**
     * Creates a new success result with the given message. The {@link #getType()} method will return {@link ResultType#SUCCESS}.
     * @param message the message of the result
     * @return a new success result
     */
    public static Result success(String message) {
        return new Result(ResultType.SUCCESS, message);
    }

    /**
     * Creates a new needs debug result with the given type to debug and debug message label, in other words what is being
     *      decided by the user.
     * @param typeToDebug {@link DebugType} that is now needed
     * @param debugMessageLabel string that messages what action/effect is being decided
     * @return a new needs debug result without any message
     */
    public static Result needsDebug(DebugType typeToDebug, String debugMessageLabel) {
        return new Result(ResultType.NEEDS_DEBUG, typeToDebug, debugMessageLabel);
    }

    /**
     * Creates a new needs debug result with the given type to debug and debug message label, in other words what is being
     *      decided by the user.
     * @param typeToDebug {@link DebugType} that is now needed
     * @param debugMessageLabel string that messages what action/effect is being decided
     * @param messageToPlace the message that needs to be placed before the user decides next debug value
     * @return a new needs debug result with a message to place
     */
    public static Result needsDebug(DebugType typeToDebug, String debugMessageLabel, String messageToPlace) {
        return new Result(ResultType.NEEDS_DEBUG, messageToPlace, typeToDebug, debugMessageLabel);
    }
}
