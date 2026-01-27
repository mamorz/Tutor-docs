package edu.kit.kastel.objects.monsters.stats;

import edu.kit.kastel.commands.Result;

/**
 * The {@code HealthPoints} class represents the health points (HP) of a monster, including its base value,
 * current value, and protection status against damage.
 *
 * @author upgcv
 */
public class HealthPoints {
    private static final String FIRST_SIGN_OF_HP_BOX = "[";
    private static final String LAST_SIGN_OF_HP_BOX = "]";
    private static final String HP_BOX_HP_SIGN = "X";
    private static final String HP_BOX_NO_HP_SIGN = "_";
    private static final String HP_IS_PROTECTED_MESSAGE = "%s is protected and takes no damage!";
    private static final String HP_SET_PROTECTED_MESSAGE = "%s is now protected against damage!";
    private static final String TOOK_DMG_MESSAGE = "%s takes %d damage!";
    private static final String HEALED_DMG_MESSAGE = "%s gains back %d health!";
    private static final int START_CYCLE_POINT = 0;
    private static final int BOTTOM_HP_BORDER = 0;
    private static final double MAX_HP_DOTS_NUMBER = 20.0;

    private final int baseValue;
    private String monsterName;
    private int value;
    private boolean isProtected;

    /**
     * Constructs a new {@code HealthPoints} object with the given HP value and monster name.
     *
     * @param value       The base HP value of the monster.
     * @param monsterName The name of the monster.
     */
    public HealthPoints(int value, String monsterName) {
        this.monsterName = monsterName;
        this.baseValue = value;
        this.isProtected = false;
        this.value = this.baseValue;
    }

    private HealthPoints(HealthPoints oldHP) {
        this.baseValue = oldHP.baseValue;
        this.monsterName = oldHP.monsterName;
        this.value = oldHP.value;
        this.isProtected = oldHP.isProtected;
    }

    /**
     * Updates the monster's name associated with this HP instance.
     *
     * @param newName The new name of the monster.
     */
    public void updateMonsterName(String newName) {
        this.monsterName = newName;
    }

    /**
     * Protects the monster from taking damage.
     *
     * @return A success result indicating that the monster is now protected. Can contain a message do display
     */
    public Result protect() {
        this.isProtected = true;
        return Result.success(HP_SET_PROTECTED_MESSAGE.formatted(this.monsterName));
    }

    /**
     * Removes the protection from the monster, making it vulnerable to damage again.
     */
    public void unprotect() {
        this.isProtected = false;
    }

    /**
     * Gets the base HP value of the monster.
     *
     * @return The base HP value.
     */
    public int getBaseValue() {
        return this.baseValue;
    }

    /**
     * Gets the current HP value of the monster, ensuring it is not negative.
     *
     * @return The current HP value, that doesn't go below 0
     */
    public int getHPValue() {
        return Math.max(this.value, BOTTOM_HP_BORDER);
    }


    /**
     * Inflicts damage on the HP.
     *
     * @param value                 The amount of damage to be inflicted.
     * @param isSelfCast            {@code true} if the damage is self-inflicted, {@code false} otherwise.
     * @param isFirstDebugExecution indicates if this method is being executed for the first time during this turn, while
     *                              the {@link edu.kit.kastel.logistics.Competition} is being debugged
     * @return A result indicating success or failure, depending on whether the monster faints.
     *      Can contain a message to display
     */
    public Result inflictDamage(int value, boolean isSelfCast, boolean isFirstDebugExecution) {
        if (this.isProtected && !isSelfCast) {
            return Result.success(HP_IS_PROTECTED_MESSAGE.formatted(this.monsterName));
        } else if (isFirstDebugExecution) {
            this.value -= value;
            if (this.value <= BOTTOM_HP_BORDER) {
                return Result.failed(TOOK_DMG_MESSAGE.formatted(this.monsterName, value));
            }
        }
        return Result.success(TOOK_DMG_MESSAGE.formatted(this.monsterName, value));
    }

    /**
     * Heals the monster by a specified amount.
     *
     * @param value The amount of HP to restore.
     * @param isFirstDebugExecution indicates if this method is being executed for the first time during this turn, while
     *                            the {@link edu.kit.kastel.logistics.Competition} is being debugged
     * @return A success result indicating the amount of HP restored. Can contain a message to display
     */
    public Result heal(int value, boolean isFirstDebugExecution) {
        if (isFirstDebugExecution) {
            this.value = Math.min(this.baseValue, this.value + value);
        }
        return Result.success(HEALED_DMG_MESSAGE.formatted(this.monsterName, value));
    }

    /**
     * Generates a string representation of the monster's HP as a visual bar.
     *
     * @return A string showing the HP bar with filled and empty segments.
     */
    public String getHPBoxes() {
        String lineToOut = FIRST_SIGN_OF_HP_BOX;
        int numberOfHPDots = (int) Math.ceil(MAX_HP_DOTS_NUMBER * this.value / this.baseValue);
        for (int i = START_CYCLE_POINT; i < numberOfHPDots; i++) {
            lineToOut = lineToOut.concat(HP_BOX_HP_SIGN);
        }
        for (int i = START_CYCLE_POINT; i < Math.min(((int) MAX_HP_DOTS_NUMBER) - numberOfHPDots, ((int) MAX_HP_DOTS_NUMBER)); i++) {
            lineToOut = lineToOut.concat(HP_BOX_NO_HP_SIGN);
        }
        lineToOut += LAST_SIGN_OF_HP_BOX;
        return lineToOut;
    }

    /**
     * Creates a deep copy of this {@code HealthPoints} object.
     *
     * @return A new {@code HealthPoints} instance with the same values.
     */
    public HealthPoints copy() {
        return new HealthPoints(this);
    }
}


