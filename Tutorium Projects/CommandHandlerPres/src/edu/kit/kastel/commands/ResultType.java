/*
 * Copyright (c) 2025, KASTEL. All rights reserved.
 */

package edu.kit.kastel.commands;

/**
 * The result type of command execution.
 * 
 * @author Programmieren-Team
 * @author upgcv
 */
public enum ResultType {
    /**
     * Indicates that the command execution was successful.
     */
    SUCCESS,
    /**
     * Indicates that the command execution failed.
     */
    FAILURE,
    /**
     * Indicates that the command execution needs information from user.
     */
    NEEDS_DEBUG
}
