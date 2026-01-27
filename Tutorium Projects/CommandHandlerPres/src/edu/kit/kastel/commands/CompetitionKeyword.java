package edu.kit.kastel.commands;

import edu.kit.kastel.logistics.Competition;

import java.util.Arrays;
/**
 * This enum represents all keywords for commands handling a {@link Competition}.
 *
 * @author upgcv
 * @author Programmieren-Team
 */
public enum CompetitionKeyword implements Keyword<Competition> {

    /**
     * The keyword for {@link ShowCommand show} command.
     */
    SHOW(arguments -> new ShowCommand()),

    /**
     * The keyword for {@link ShowActionsCommand show actions} command.
     */
    SHOW_ACTIONS(arguments -> new ShowActionsCommand()),

    /**
     * The keyword for {@link ActionCommand action} command.
     */
    ACTION(arguments -> new ActionCommand(arguments.parseSetAction())),

    /**
     * The keyword for {@link PassCommand pass} command.
     */
    PASS(arguments -> new PassCommand()),

    /**
     * The keyword for {@link ShowStatsCommand show stats} command.
     */
    SHOW_STATS(arguments -> new ShowStatsCommand());

    private static final String WORDS_SEPARATOR_IN_ENUM = "_";
    private static final String WORDS_SEPARATOR_FOR_UI = " ";
    private static final String NOT_MATCHING_MESSAGE = "Error, cannot retrieve arguments if the line does not include the command!";
    private static final int START_CYCLE_INDEX = 0;


    private final CommandProvider<Competition> provider;

    CompetitionKeyword(CommandProvider<Competition> provider) {
        this.provider = provider;
    }

    @Override
    public boolean matches(String command) {
        if (this == SHOW) {
            return command.equals(this.name().toLowerCase());
        }
        return command.startsWith(this.name().toLowerCase().replace(WORDS_SEPARATOR_IN_ENUM, WORDS_SEPARATOR_FOR_UI));
    }

    @Override
    public String[] retrieveCommandArgs(String command) {
        if (!this.matches(command)) {
            throw new IllegalArgumentException(NOT_MATCHING_MESSAGE);
        }
        int startIndex = START_CYCLE_INDEX;
        while (startIndex < this.name().split(WORDS_SEPARATOR_IN_ENUM).length
                && command.split(WORDS_SEPARATOR_FOR_UI)[startIndex]
                .equals(this.name().toLowerCase().split(WORDS_SEPARATOR_IN_ENUM)[startIndex])) {
            startIndex++;
        }
        return Arrays.copyOfRange(command.split(WORDS_SEPARATOR_FOR_UI), startIndex, command.split(WORDS_SEPARATOR_FOR_UI).length);
    }

    @Override
    public Command<Competition> provide(Arguments arguments) throws InvalidArgumentException {
        return this.provider.provide(arguments);
    }
}
