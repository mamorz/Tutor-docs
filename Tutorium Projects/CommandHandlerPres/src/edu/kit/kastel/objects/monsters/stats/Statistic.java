package edu.kit.kastel.objects.monsters.stats;

import edu.kit.kastel.commands.Result;
import edu.kit.kastel.objects.monsters.StatisticOfMonster;

/**
 * Represents a statistic for a monster, including its base value, modification factors,
 * and whether it is currently protected against changes.
 * @author upgcv
 */
public class Statistic {
    private static final String STAT_BECAME_GREATER = "%s's %s rises!";
    private static final String STAT_BECAME_POORER = "%s's %s decreases...";
    private static final String STAT_IS_PROTECTED_MESSAGE = "%s is protected and is unaffected!";
    private static final String OFFSET_FOR_SHOW_STAT = "+%d";
    private static final int DEFAULT_OFFSET_VALUE = 0;
    private static final int UPPER_BORDER_OFFSET_VALUE = 5;

    private final StatisticOfMonster representation;
    private final int bModifierStatFactor;
    private final int baseValue;
    private String monsterName;
    private int offsetValue;
    private boolean isProtected;

    /**
     * Creates a new Statistic of a monster with provided properties.
     * @param representation statistic this instance represents
     * @param value start value of this stat
     * @param bModifierStatFactor factor the offset is being calculated with
     * @param monsterName name of the monster that has this stat
     */
    public Statistic(StatisticOfMonster representation, int value, int bModifierStatFactor, String monsterName) {
        this.representation = representation;
        this.baseValue = value;
        this.bModifierStatFactor = bModifierStatFactor;
        this.monsterName = monsterName;
        this.offsetValue = DEFAULT_OFFSET_VALUE;
        this.isProtected = false;
    }

    private Statistic(Statistic oldStat) {
        this.representation = oldStat.representation;
        this.baseValue = oldStat.baseValue;
        this.bModifierStatFactor = oldStat.bModifierStatFactor;
        this.monsterName = oldStat.monsterName;
        this.offsetValue = oldStat.offsetValue;
        this.isProtected = oldStat.isProtected;
    }
    /**
     * Updates the name of the monster associated with this statistic.
     * @param newName The new name of the monster.
     */
    public void updateMonsterName(String newName) {
        this.monsterName = newName;
    }
    /**
     * Changes the offset value of the statistic, modifying its effective value.
     * @param value     The amount to modify the statistic by.
     * @param isSelfCast Whether the change is self-inflicted.
     * @param isFirstDebugExecution indicates if this method is being executed for the first time during this turn, while
     *     the {@link edu.kit.kastel.logistics.Competition} is being debugged
     * @return A {@link Result} indicating success or failure. Can contain a message to display
     */
    public Result changeValueOffset(int value, boolean isSelfCast, boolean isFirstDebugExecution) {
        if (!isProtected || isSelfCast || value > DEFAULT_OFFSET_VALUE) {
            if (isFirstDebugExecution) {
                this.offsetValue = value > DEFAULT_OFFSET_VALUE ? Math.min(this.offsetValue + value, UPPER_BORDER_OFFSET_VALUE)
                        : Math.max(this.offsetValue + value, -UPPER_BORDER_OFFSET_VALUE);
            }
            return Result.success(value >= DEFAULT_OFFSET_VALUE
                    ? STAT_BECAME_GREATER.formatted(this.monsterName, this.representation)
                    : STAT_BECAME_POORER.formatted(this.monsterName, this.representation));
        } else {
            return Result.success(STAT_IS_PROTECTED_MESSAGE.formatted(this.monsterName));
        }
    }

    /**
     * Protects the statistic from being changed.
     */
    public void protect() {
        this.isProtected = true;
    }
    /**
     * Removes protection from the statistic, allowing changes.
     */
    public void unprotect() {
        this.isProtected = false;
    }
    /**
     * Returns a formatted string representation of the current offset value.
     * @return A string representing the offset to display it
     */
    public String getOffsetString() {
        return offsetValue > DEFAULT_OFFSET_VALUE ? String.format(OFFSET_FOR_SHOW_STAT, this.offsetValue)
                : String.valueOf(this.offsetValue);
    }
    /**
     * Retrieves the base value of the statistic.
     * @return The base value.
     */
    public int getBaseValue() {
        return this.baseValue;
    }
    /**
     * Checks whether the statistic has been modified from its base value.
     * @return {@code true} if the statistic is modified, {@code false} otherwise.
     */
    public boolean isChanged() {
        return this.offsetValue != DEFAULT_OFFSET_VALUE;
    }
    /**
     * Calculates the effective value of the statistic based on its modifications.
     * @return The modified statistic value.
     */
    public double getEffectiveValue() {
        return this.baseValue * (this.offsetValue >= DEFAULT_OFFSET_VALUE
                ? (this.bModifierStatFactor + this.offsetValue) / (double) this.bModifierStatFactor
                : (double) this.bModifierStatFactor / (this.bModifierStatFactor - this.offsetValue));
    }
    /**
     * Creates a deep copy of this statistic instance.
     * @return A new {@code Statistic} object with the same properties.
     */
    public Statistic copy() {
        return new Statistic(this);
    }
}
