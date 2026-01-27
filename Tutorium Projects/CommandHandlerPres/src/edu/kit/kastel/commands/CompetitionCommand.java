package edu.kit.kastel.commands;

import java.util.Arrays;

/**
 * This command represents the "competition ..." command that can be used to create a competition.
 *
 * @author upgcv
 */
public class CompetitionCommand implements Command<CommandHandler> {
    private static final String IMPOSSIBLE_IN_DEBUG_PROCESS_MESSAGE = "Error, you cannot use this command in debug mode!";

    private final String[] monstersToGetIntoCompetition;

    /**
     * Creates a new instance of CompetitionCommand with monsters the competition should be started with.
     * @param monsterNamesToGetIntoCompetition an array with names of monsters' names
     */
    public CompetitionCommand(String[] monsterNamesToGetIntoCompetition) {
        this.monstersToGetIntoCompetition = Arrays.copyOf(monsterNamesToGetIntoCompetition, monsterNamesToGetIntoCompetition.length);
    }

    @Override
    public Result execute(CommandHandler handle) {
        if (handle.isInDebugProcess()) {
            return Result.failed(IMPOSSIBLE_IN_DEBUG_PROCESS_MESSAGE);
        }
        return handle.startCompetition(this.monstersToGetIntoCompetition);
    }
}
