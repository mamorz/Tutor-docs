package edu.kit.informatik;

/**
 * Some helper constants for writing colored output.
 */
public enum TerminalColor {
    RESET_COLOR("\u001b[39;49m"),
    RED("\u001b[31m"),
    GREEN("\u001b[32m"),
    BLUE("\u001b[34m");

    private String escapeSequence;

    TerminalColor(String escapeSequence) {
        this.escapeSequence = escapeSequence;
    }

    @Override
    public String toString() {
        return escapeSequence;
    }
}
