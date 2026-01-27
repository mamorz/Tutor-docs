package edu.kit.kastel.objects.actions;

import edu.kit.kastel.objects.monsters.StatisticOfMonster;

/**
 * Represents various status conditions that can affect a monster in the game.
 * Each status condition has associated messages and may impact a specific statistic.
 * @author upgcv
 */
public enum StatusCondition {

    /** The default status with no negative effects. */
    OK("", "", "", null, 1),

    /** A burning condition that reduces the affected monster's attack power. */
    BURN("%s caught on fire!", "%s is burning!",
            "%s's burning has faded!", StatisticOfMonster.ATK, 0.75),

    /** A quicksand condition that reduces the affected monster's speed. */
    QUICKSAND("%s gets caught by quicksand!", "%s is caught in quicksand!",
            "%s escaped the quicksand!", StatisticOfMonster.SPD, 0.75),

    /** A wet condition that reduces the affected monster's defense. */
    WET("%s becomes soaking wet!", "%s is soaking wet!",
            "%s dried up!", StatisticOfMonster.DEF, 0.75),

    /** A sleep condition that temporarily disables the affected monster. */
    SLEEP("%s falls asleep!", "%s is asleep!",
            "%s woke up!", null, 1);

    private static final double DEFAULT_REDUCING_FACTOR = 1.0;

    private final String getsStatusMessage;
    private final String hasStatusMessage;
    private final String losesStatusMessage;
    private final StatisticOfMonster statisticOfMonsterSuffering;
    private final double statisticReducingFactor;

    StatusCondition(String getsStatusMessage, String hasStatusMessage, String losesStatusMessage,
                    StatisticOfMonster stateOfMonsterSuffering, double statisticReducingFactor) {
        this.getsStatusMessage = getsStatusMessage;
        this.hasStatusMessage = hasStatusMessage;
        this.losesStatusMessage = losesStatusMessage;
        this.statisticOfMonsterSuffering = stateOfMonsterSuffering;
        this.statisticReducingFactor = statisticReducingFactor;
    }

    /**
     * Retrieves the statistic reduction factor for a given affected statistic.
     * @param stateToBeReduced The statistic being checked for reduction.
     * @return The reduction factor if the statistic is affected, otherwise 1.0.
     */
    public double getStatisticReducingFactor(StatisticOfMonster stateToBeReduced) {
        return stateToBeReduced.equals(this.statisticOfMonsterSuffering)
                ? this.statisticReducingFactor : DEFAULT_REDUCING_FACTOR;
    }
    /**
     * Retrieves the message displayed when a monster acquires this status.
     * @return The "gets status" message.
     */
    public String getGetsStatusMessage() {
        return getsStatusMessage;
    }

    /**
     * Retrieves the message displayed when a monster currently has this status.
     * @return The "has status" message.
     */
    public String getHasStatusMessage() {
        return hasStatusMessage;
    }

    /**
     * Retrieves the message displayed when a monster loses this status.
     * @return The "loses status" message.
     */
    public String getLosesStatusMessage() {
        return losesStatusMessage;
    }
}
