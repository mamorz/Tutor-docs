package edu.kit.kastel.commands;

import edu.kit.kastel.logistics.Competition;

/**
 * This command represents the "action ..." command that can be used during a competition.
 *
 * @author upgcv
 */
public class ActionCommand implements Command<Competition> {
    private static final String IN_DEBUG_PROCESS_MESSAGE = "Error, you cannot use this command in debug mode!";
    private static final String NO_COMPETITION_INITIALIZED_MESSAGE = "Error, there is no competition yet!";
    private static final String NO_TARGET_ENTERED_MESSAGE = "Error, you should enter a target for this action!";
    private static final int ACTION_POSITION = 0;
    private static final int TARGET_POSITION = 1;

    private final String actionName;
    private final String targetName;

    /**
     * Creates a new instance of ActionCommand with name of the action to be used and a possible target name.
     * Target can be an empty name.
     * @param actionArgs an array with names of the action to be executed and a possible target name
     */
    public ActionCommand(String[] actionArgs) {
        this.actionName = actionArgs[ACTION_POSITION];
        this.targetName = actionArgs.length > TARGET_POSITION ? actionArgs[TARGET_POSITION] : "";
    }

    @Override
    public Result execute(Competition handle) {
        if (handle.isDecided()) {
            return Result.failed(NO_COMPETITION_INITIALIZED_MESSAGE);
        }
        if (handle.getDebugInfo().isDebugOn()) {
            return Result.failed(IN_DEBUG_PROCESS_MESSAGE);
        }
        if (handle.actionNeedsTarget(this.actionName) && this.targetName.isBlank()) {
            return Result.failed(NO_TARGET_ENTERED_MESSAGE);
        }
        return handle.setNextAction(this.actionName, this.targetName);
    }
}
