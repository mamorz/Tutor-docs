package edu.kit.kastel.objects;

/**
 * Enum representing different elements in the game.
 * Each element has effectiveness relationships with other elements
 * that determine how effective their interactions are in combat.
 * @author upgcv
 */
public enum Element {

    /**
     * Represents the normal element with no special strengths or weaknesses.
     */
    NORMAL,

    /**
     * Represents the fire element, effective against earth but weak to water.
     */
    FIRE,

    /**
     * Represents the water element, effective against fire but weak to earth.
     */
    WATER,

    /**
     * Represents the earth element, effective against water but weak to fire.
     */
    EARTH;

    private static final double NORMAL_MODIFICATION = 1.0;
    private static final double EFFECTIVE_MODIFICATION = 2.0;
    private static final double NOT_EFFECTIVE_MODIFICATION = 0.5;
    private static final int START_CYCLE_POINT = 0;

    private static final Element[][] VERY_EFFECTIVE_AGAINST
            = new Element[][] {{WATER, FIRE}, {FIRE, EARTH}, {EARTH, WATER}};

    /**
     * Determines the effectiveness of one element against another.
     *
     * @param user the element using the attack or action.
     * @param target the element being targeted by the attack or action.
     * @return a double representing the effectiveness modifier:
     *         - 2.0 for very effective
     *         - 1.0 for normal effectiveness
     *         - 0.5 for not effective
     */
    public static double getEffectivenessGrade(Element user, Element target) {
        if (user.equals(NORMAL) || target.equals(NORMAL) || user.equals(target)) {
            return NORMAL_MODIFICATION;
        }
        Element[] inputCouple = new Element[] {user, target};
        for (Element[] elementCouple : VERY_EFFECTIVE_AGAINST) {
            boolean isTheCouple = true;
            for (int i = START_CYCLE_POINT; i < elementCouple.length; i++) {
                if (!elementCouple[i].equals(inputCouple[i])) {
                    isTheCouple = false;
                    break;
                }
            }
            if (isTheCouple) {
                return EFFECTIVE_MODIFICATION;
            }
        }
        return NOT_EFFECTIVE_MODIFICATION;
    }
}
