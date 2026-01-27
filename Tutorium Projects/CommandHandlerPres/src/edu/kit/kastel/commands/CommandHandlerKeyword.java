package edu.kit.kastel.commands;

import java.util.Arrays;

/**
 * This enum represents all keywords for commands handling a {@link CommandHandler}.
 *
 * @author upgcv
 * @author Programmieren-Team
 */
public enum CommandHandlerKeyword implements Keyword<CommandHandler> {

    /**
     * The keyword for {@link LoadCommand load} command.
     */
    LOAD(arguments -> new LoadCommand(arguments.parseSrcPath())),

    /**
     * The keyword for {@link QuitCommand load} command.
     */
    QUIT(arguments -> new QuitCommand()),

    /**
     * The keyword for {@link CompetitionCommand load} command.
     */
    COMPETITION(arguments -> new CompetitionCommand(arguments.parseMonsterNames())),

    /**
     * The keyword for {@link ShowMonstersCommand load} command.
     */
    SHOW_MONSTERS(arguments -> new ShowMonstersCommand());

    private static final String ILLEGAL_USE_OF_METHOD_MESSAGE =
            "Error, you must use this method to retrieve command arguments from an input line that matches this command keyword!";
    private static final String WORDS_SEPARATOR_IN_ENUM = "_";
    private static final String WORDS_SEPARATOR_FOR_UI = " ";
    private static final int START_CYCLE_INDEX = 0;
    private final CommandProvider<CommandHandler> provider;

    CommandHandlerKeyword(CommandProvider<CommandHandler> provider) {
        this.provider = provider;
    }

    @Override
    public boolean matches(String command) {
        return command.startsWith(this.name().toLowerCase().replace(WORDS_SEPARATOR_IN_ENUM, WORDS_SEPARATOR_FOR_UI));
    }

    @Override
    public String[] retrieveCommandArgs(String command) {
        if (!this.matches(command)) {
            throw new IllegalArgumentException(ILLEGAL_USE_OF_METHOD_MESSAGE);
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
    public Command<CommandHandler> provide(Arguments arguments) throws InvalidArgumentException {
        return provider.provide(arguments);
    }
}
