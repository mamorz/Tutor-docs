package edu.kit.kastel.commands;

/**
 * Class representing a quit command in the game.
 * @author upgcv
 */
public class QuitCommand implements Command<CommandHandler> {

    @Override
    public Result execute(CommandHandler handle) {
        handle.endApplication();
        return null;
    }
}
