package edu.kit.kastel.objects.monsters;

/**
 * Represents the different statistics that a monster can have in the game.
 * @author upgcv
 */
public enum StatisticOfMonster {

    /** Attack power of the monster, affecting its damage output. */
    ATK,

    /** Defense capability of the monster, reducing incoming damage. */
    DEF,

    /** Speed of the monster, influencing turn order and movement. */
    SPD,

    /** Perception of the monster, potentially affecting accuracy or awareness. */
    PRC,

    /** Agility of the monster, impacting evasion and dexterity. */
    AGL
}