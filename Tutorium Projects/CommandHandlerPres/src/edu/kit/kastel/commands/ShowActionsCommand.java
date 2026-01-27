package edu.kit.kastel.commands;

import edu.kit.kastel.logistics.Competition;

/**
 * Class representing the show actions command.
 * @author upgcv
 */
public class ShowActionsCommand implements Command<Competition> {
    private static final String NO_COMPETITION_INITIALIZED_MESSAGE = "Error, there is no competition yet!";
    private static final String IMPOSSIBLE_IN_DEBUG_PROCESS_MESSAGE = "Error, you cannot use this command in debug mode!";


    @Override
    public Result execute(Competition handle) {
        if (handle.isDecided()) {
            return Result.failed(NO_COMPETITION_INITIALIZED_MESSAGE);
        }
        if (handle.getDebugInfo().isDebugOn()) {
            return Result.failed(IMPOSSIBLE_IN_DEBUG_PROCESS_MESSAGE);
        }
        return Result.success(handle.getMonsterOnTurn().getActionsInfo());
    }
}
