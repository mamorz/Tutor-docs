package edu.kit.kastel.logistics;

/**
 * Record class representing data that {@link Randomizer} needs to provide the {@link Competition} as next.
 * @param typeToExpect Type of info is expected: an Integer, a Boolean or a Double
 * @param bottomBorder bottom border of possible Integer or Double value to be accepted; if the current {@code typeToExpect}
 *                     is Boolean, than {@code bottomBorder} is set to Integer.MAX_VALUE value
 * @param upperBorder upper border of possible Integer or Double value to be accepted; if the current {@code typeToExpect}
 *                     is Boolean, than {@code upperBorder} is set to Integer.MIN_VALUE value
 * @author upgcv
 */
public record DebugExpectedData(DebugType typeToExpect, double bottomBorder, double upperBorder) {
}
