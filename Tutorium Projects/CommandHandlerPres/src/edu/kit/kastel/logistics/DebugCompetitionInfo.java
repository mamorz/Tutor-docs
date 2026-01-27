package edu.kit.kastel.logistics;

/**
 * Record class representing the info about the competition: if it's currently being debugged and optionally information
 * about what type of info is now required from the user.
 * @param isDebugOn indicates if the competition is being debugged (with true value) and false otherwise
 * @param neededInfo possible info that the {@link Competition} class needs next to proceed: a Boolean, Double or an Integer
 * @author upgcv
 */
public record DebugCompetitionInfo(boolean isDebugOn, DebugType neededInfo) {
}
