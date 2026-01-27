package edu.kit.kastel.objects.actions.effects;

/**
 * Enum representing the type of strength used in effects such as damage or healing.
 * Each strength type is associated with a specific prefix used for identifying its strength value.
 *
 * @author upgcv
 */
public enum StrengthType {

    /**
     * Represents the base strength type.
     */
    BASE("b"),

    /**
     * Represents the relative strength type.
     */
    REL("r"),

    /**
     * Represents the absolute strength type.
     */
    ABS("a");


    private final String dmgPrefix;

    StrengthType(String prefix) {
        this.dmgPrefix = prefix;
    }

    /**
     * Returns the prefix associated with the strength type.
     *
     * @return the prefix as a string (e.g., "b" for BASE, "r" for REL, "a" for ABS).
     */
    public String getDmgPrefix() {
        return this.dmgPrefix;
    }
}