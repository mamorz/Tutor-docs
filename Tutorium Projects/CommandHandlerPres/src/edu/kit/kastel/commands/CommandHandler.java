package edu.kit.kastel.commands;

import edu.kit.kastel.logistics.Competition;
import edu.kit.kastel.logistics.GameSetup;
import edu.kit.kastel.objects.monsters.Monster;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Handles user commands, processes input and output, manages game setup and competition.
 * @author upgcv
 * @author Programmieren-Team
 */
public class CommandHandler {
    private static final String NOT_AUTO_RANDOM_MODE_REGEX = "debug";
    private static final String WORDS_SEPARATOR_REGEX = " ";
    private static final String INVALID_SRC_PATH_MESSAGE = "Error, %s is not a valid path to a file!";
    private static final String NOT_NEEDED_ARGUMENTS_MESSAGE = "Error, invalid arguments provided!";
    private static final String UNKNOWN_MONSTERS_MESSAGE = "Error, the competition couldn't start with desired monsters!";
    private static final String NOT_ENOUGH_MONSTERS_TO_START_COMPETITION = "Error, the competition could not start with %d monsters";
    private static final String INVALID_PRECONDITION_MESSAGE = "Error, command cannot be used right now.";
    private static final String UNKNOWN_COMMAND_MESSAGE = "Error, you entered a command that cannot be executed (right now).";
    private static final String NUMBER_REGEX = "-?[1-9][0-9]*";
    private static final int CYCLE_START_POINT = 0;
    private static final int MIN_MONSTERS_TO_RUN_COMPETITION = 2;

    private final boolean toBeDebugged;
    private final boolean isSeedEntered;
    private final PrintStream outputStream;
    private final PrintStream errorStream;
    private final InputStream inputStream;
    private final Set<CommandHandlerKeyword> commandHandlerKeywords = EnumSet.allOf(CommandHandlerKeyword.class);
    private final Set<CompetitionKeyword> competitionKeywords = EnumSet.allOf(CompetitionKeyword.class);

    private long seed;
    private boolean isProgOn;
    private GameSetup gameSetup;
    private Competition currentCompetition;


    /**
     * Initializes the command handler with the given input mode and streams.
     *
     * @param input        The input mode, either "debug" or a seed number.
     * @param inputStream  The input stream for user commands.
     * @param outputStream The output stream for game messages.
     * @param errorStream  The error stream for displaying errors.
     * @throws IllegalArgumentException if input line contains too many arguments
     */
    public CommandHandler(String input, InputStream inputStream, PrintStream outputStream, PrintStream errorStream) {
        if (input.equals(NOT_AUTO_RANDOM_MODE_REGEX)) {
            this.isSeedEntered = false;
            this.toBeDebugged = true;
        } else if (input.matches(NUMBER_REGEX)) {
            this.isSeedEntered = true;
            this.toBeDebugged = false;
            this.seed = Long.parseLong(input);
        } else if (!input.isBlank()) {
            throw new IllegalArgumentException(NOT_NEEDED_ARGUMENTS_MESSAGE);
        } else {
            this.isSeedEntered = false;
            this.toBeDebugged = false;
        }
        this.isProgOn = true;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.errorStream = errorStream;
    }

    /**
     * Processes user input from the input stream and executes commands accordingly.
     */
    public void handleUserInput() {
        if (!isProgOn) {
            return;
        }
        try (Scanner scanner = new Scanner(this.inputStream)) {
            while (this.isProgOn && scanner.hasNextLine()) {
                handleLine(scanner.nextLine());
            }
        }
    }

    private void handleLine(String nextLine) {
        if (!findAndExecuteCommand(this.commandHandlerKeywords, this, nextLine)
                && !findAndExecuteCommand(this.competitionKeywords, currentCompetition, nextLine)
                && !(this.isInDebugProcess() && handlePossibleDebugCommand(nextLine))) {
            handleResult(Result.failed(UNKNOWN_COMMAND_MESSAGE));
        }
    }


    private <S, T extends Keyword<S>> boolean findAndExecuteCommand(Set<T> keywordsToCheck, S value, String inputLine) {
        T keyword = retrieveKeyword(keywordsToCheck, inputLine);
        if (keyword == null) {
            return false;
        } else {
            handleCommand(value, keyword.retrieveCommandArgs(inputLine), keyword);
            return true;
        }
    }

    private <S, T extends Keyword<S>> void handleCommand(S value, String[] arguments, T keyword) {
        if (value == null) {
            handleResult(Result.failed(INVALID_PRECONDITION_MESSAGE));
            return;
        }

        Arguments argumentsHolder = new Arguments(this.gameSetup, arguments);
        Command<S> providedCommand;
        try {
            providedCommand = keyword.provide(argumentsHolder);
        } catch (InvalidArgumentException e) {
            handleResult(Result.failed(e.getMessage()));
            return;
        }

        if (!argumentsHolder.isExhausted()) {
            handleResult(Result.failed(NOT_NEEDED_ARGUMENTS_MESSAGE));
            return;
        }

        handleResult(providedCommand.execute(value));
    }

    private boolean handlePossibleDebugCommand(String inputLine) {
        Arguments argumentsHolder = new Arguments(this.gameSetup, inputLine.trim().split(WORDS_SEPARATOR_REGEX));
        try {
            switch (this.currentCompetition.getDebugInfo().neededInfo()) {
                case DOUBLE -> this.currentCompetition.setRandomizerDebugData(argumentsHolder.parseRandomDouble());
                case INTEGER -> this.currentCompetition.setRandomizerDebugData(argumentsHolder.parseRandomInteger());
                case BOOLEAN -> this.currentCompetition.setRandomizerDebugData(argumentsHolder.parseSuccessOrFail());
                default -> {
                    return false; //if the randomizer doesn't need already handled info above
                }
            }
        } catch (InvalidArgumentException e) {
            handleResult(Result.failed(e.getMessage()));
            return true;
        }
        handleResult(this.currentCompetition.handlePhaseOneAndTwo());
        return true;
    }

    /**
     * This method indicates whether there is a competition and the second phase of a round is being debugged by the user.
     * @return true if the second phase is being debugged, false otherwise
     */
    public boolean isInDebugProcess() {
        return this.currentCompetition != null && this.currentCompetition.getDebugInfo().isDebugOn();
    }


    /**
     * Loads and processes a game configuration from the specified file path.
     *
     * @param possibleSourcePath The path to the configuration file.
     */
    public void handleFirstConfig(String possibleSourcePath) {
        Arguments initialArgumentsHolder = new Arguments(new GameSetup(), possibleSourcePath.trim().split(WORDS_SEPARATOR_REGEX));
        Result resultHandleCFG;
        try {
            resultHandleCFG = CommandHandlerKeyword.LOAD.provide(initialArgumentsHolder).execute(this);
        } catch (InvalidArgumentException e) {
            handleResult(Result.failed(INVALID_SRC_PATH_MESSAGE.formatted(possibleSourcePath)));
            endApplication();
            return;
        }
        if (!initialArgumentsHolder.isExhausted()) {
            handleResult(Result.failed(NOT_NEEDED_ARGUMENTS_MESSAGE));
            return;
        }
        handleResult(resultHandleCFG);
        if (this.gameSetup == null) {
            endApplication();
        }
    }

    /**
     * Sets the game setup configuration.
     *
     * @param config The game setup configuration.
     */
    public void setConfig(GameSetup config) {
        this.gameSetup = config.copy();
        this.currentCompetition = null;
    }

    private void handleResult(Result result) {
        String pendingMessage = "";
        if (this.currentCompetition != null && !this.currentCompetition.isDecided()) {
            pendingMessage = this.currentCompetition.getPendingMessage();
        }
        if (result == null || (result.getMessage().isBlank() && pendingMessage.isBlank())) {
            return;
        }
        String resultMessage = result.getMessage() + (result.getMessage().isBlank() ? "" : System.lineSeparator());
        switch (result.getType()) {
            case FAILURE:
                errorStream.print(resultMessage);
                outputStream.print(pendingMessage);
                break;
            case SUCCESS:
                outputStream.print(resultMessage + pendingMessage);
                break;
            default:
                break;
        }
    }

    private static <T extends Keyword<?>> T retrieveKeyword(Collection<T> keywords, String inputLine) {
        for (T keyword : keywords) {
            if (keyword.matches(inputLine)) {
                return keyword;
            }
        }
        return null;
    }
    /**
     * Starts a competition with the given monsters. If there are less than 2 monsters' names provided, the competition
     * does not begin.
     *
     * @param monsters The names of the monsters participating in the competition.
     * @return A result indicating success or failure of the competition start.
     */
    public Result startCompetition(String[] monsters) {
        if (monsters.length < MIN_MONSTERS_TO_RUN_COMPETITION) {
            return Result.failed(NOT_ENOUGH_MONSTERS_TO_START_COMPETITION.formatted(monsters.length));
        }
        this.currentCompetition = makeCompetitionBetween(monsters);
        return this.currentCompetition == null
                ? Result.failed(UNKNOWN_MONSTERS_MESSAGE)
                : this.currentCompetition.start();
    }

    private Competition makeCompetitionBetween(String[] monsterNames) {
        Monster[] monsters = new Monster[monsterNames.length];
        for (int index = CYCLE_START_POINT; index < monsterNames.length; index++) {
            if (this.gameSetup.getMonster(monsterNames[index]) == null) {
                return null;
            }
            monsters[index] = this.gameSetup.getMonster(monsterNames[index]);
        }
        if (this.currentCompetition != null) {
            return new Competition(monsters, this.currentCompetition);
        }
        return toBeDebugged ? new Competition(monsters, true)
                : isSeedEntered ? new Competition(monsters, seed) : new Competition(monsters);
    }
    /**
     * Terminates the command handler, stopping further user input processing.
     */
    void endApplication() {
        this.isProgOn = false;
    }
    /**
     * Displays the available monsters.
     *
     * @return A string representation of all available monsters.
     */
    String showMonsters() {
        return this.gameSetup == null ? "" : this.gameSetup.showMonsters();
    }


}
