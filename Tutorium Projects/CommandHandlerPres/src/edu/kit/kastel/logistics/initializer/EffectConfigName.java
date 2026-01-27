package edu.kit.kastel.logistics.initializer;

/**
 * Defines the different effect types that can be applied in the game.
 * @author upgcv
 */
public enum EffectConfigName {

    /** Represents an effect that deals damage to a target. */
    DAMAGE,

    /** Represents an effect that inflicts a status condition on a target (e.g., burn, sleep). */
    INFLICT_STATUS_CONDITION,

    /** Represents an effect that modifies a monster's statistic values (e.g., increasing attack power). */
    INFLICT_STAT_CHANGE,

    /** Represents an effect that protects a monster's statistics from being altered. */
    PROTECT_STAT,

    /** Represents an effect that restores a monster's health points. */
    HEAL,

    /** Represents an effect that repeats an action multiple times. */
    REPEAT,

    /** Represents an effect that allows an action to continue in some form. */
    CONTINUE;

    private static final String ENUM_NOT_NEEDED_CHAR = "_";

    /**
     * Method retrieving an instance of EffectConfigName that matches the String representation.
     * @param possibleName string representation of an EffectConfigName to be checked
     * @return an EffectConfigName if the input was valid, null otherwise
     */
    public static EffectConfigName getFromStringRepresentation(String possibleName) {
        for (EffectConfigName effectName : EffectConfigName.values()) {
            if (effectName.toString().replace(ENUM_NOT_NEEDED_CHAR, "").equalsIgnoreCase(possibleName)) {
                return effectName;
            }
        }
        return null;
    }
}