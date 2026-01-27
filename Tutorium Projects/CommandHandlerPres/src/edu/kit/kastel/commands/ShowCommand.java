package edu.kit.kastel.commands;

import edu.kit.kastel.logistics.Competition;

/**
 * Class representing show command.
 * @author upgcv
 */
public class ShowCommand implements Command<Competition> {
    private static final String NO_COMPETITION_INITIALIZED = "Error, there is no competition yet!";

    @Override
    public Result execute(Competition handle) {
        if (handle.isDecided()) {
            return Result.failed(NO_COMPETITION_INITIALIZED);
        }
        return Result.success(handle.getShowInfo());
    }
}
