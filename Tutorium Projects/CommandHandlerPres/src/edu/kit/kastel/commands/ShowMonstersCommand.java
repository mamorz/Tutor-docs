package edu.kit.kastel.commands;

/**
 * Class representing show monster command.
 * @author upgcv
 */
public class ShowMonstersCommand implements Command<CommandHandler> {
    private static final String IMPOSSIBLE_IN_DEBUG_PROCESS_MESSAGE = "Error, you cannot use this command in debug mode!";

    @Override
    public Result execute(CommandHandler handle) {
        if (handle.isInDebugProcess()) {
            return Result.failed(IMPOSSIBLE_IN_DEBUG_PROCESS_MESSAGE);
        }
        return Result.success(handle.showMonsters());
    }
}
