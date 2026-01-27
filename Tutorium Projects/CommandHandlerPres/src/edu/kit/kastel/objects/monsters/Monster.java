package edu.kit.kastel.objects.monsters;

import edu.kit.kastel.commands.Result;
import edu.kit.kastel.commands.ResultType;
import edu.kit.kastel.logistics.DebugType;
import edu.kit.kastel.logistics.Randomizer;
import edu.kit.kastel.objects.Element;
import edu.kit.kastel.objects.actions.Action;
import edu.kit.kastel.objects.actions.StatusCondition;
import edu.kit.kastel.objects.monsters.stats.HealthPoints;
import edu.kit.kastel.objects.monsters.stats.Statistic;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Represents a Monster with various attributes such as element, health, and statistics.
 * A Monster can perform actions, take damage, heal, and change status conditions.
 * @author upgcv
 */
public class Monster {
    private static final String DEBUG_STATE_CHECK_NAME = "end %s condition";
    private static final String IS_DEAD_STATUS = "FAINTED";
    private static final String STAT_SET_PROTECTED_MESSAGE = "%s is now protected against status changes!";
    private static final String STAT_SET_UNPROTECTED_MESSAGE = "%s's protection fades away...";
    private static final String SHOW_SELF_MESSAGE = "%s: ELEMENT %s, HP %d, ATK %d, DEF %d, SPD %d";
    private static final String SHOW_STATISTICS_MESSAGE = "STATS OF %s";
    private static final String SHOW_ACTIONS_MESSAGE = "ACTIONS OF %s";
    private static final String HP_OUTPUT_FORMAT_MESSAGE = "HP %d/%d";
    private static final String STAT_OUTPUT_WITHOUT_OFFSET_FORMAT_MESSAGE = "%s %d";
    private static final String STAT_OUTPUT_OFFSET_FORMAT_TEXT = "%s %d(%s)";
    private static final String SHOW_STAT_JOINER_SIGN = ", ";
    private static final String MONSTER_NUMBER_PREFIX = "#";
    private static final String NUMBER_MONSTER_AND_HIS_TURN_FORMAT_MESSAGE = " %d %s";
    private static final String START_STATUS_INFO_SYMBOL = " (";
    private static final String END_STATUS_INFO_SYMBOL = ")";
    private static final int B_MODIFIER_FOR_ATK_DEF_SPD = 2;
    private static final int B_MODIFIER_FOR_PRC_AGL = 3;
    private static final int DEFAULT_PRC_AGL_VALUE = 1;
    private static final int EXTRA_ROUND_DURATION_FOR_SUBTRACTION = 1;
    private static final int NO_PROTECTION_LEFT = 0;
    private static final int NO_NUMBER_ASSIGNED = 0;
    private static final int DECREMENT_DURATION = 1;
    private static final double END_CONDITION_CHANCE = 1.0 / 3.0 * 100.0;

    /**
     * Element of this monster.
     */
    public final Element element;
    private final String name;
    private final HealthPoints healthPoints;
    private final Map<StatisticOfMonster, Statistic> statistics;
    private final Map<String, Action> actions;
    private final List<String> actionNames;
    private boolean isDead;
    private int monsterNumber;
    private int protectionDuration;
    private StatusCondition statusCondition;
    /**
     * Constructs a new Monster with the specified attributes.
     * @param name the name of the monster
     * @param element the elemental type of the monster
     * @param healthPoints the initial health points
     * @param attack the attack statistic
     * @param defence the defense statistic
     * @param speed the speed statistic
     */
    public Monster(String name, Element element, int healthPoints, int attack, int defence, int speed) {
        this.name = name;
        this.element = element;
        this.isDead = false;
        this.protectionDuration = NO_PROTECTION_LEFT;
        this.statusCondition = StatusCondition.OK;
        this.healthPoints = new HealthPoints(healthPoints, name);
        this.statistics = new EnumMap<>(StatisticOfMonster.class);
        this.statistics.put(StatisticOfMonster.ATK, new Statistic(StatisticOfMonster.ATK, attack, B_MODIFIER_FOR_ATK_DEF_SPD, name));
        this.statistics.put(StatisticOfMonster.DEF, new Statistic(StatisticOfMonster.DEF, defence, B_MODIFIER_FOR_ATK_DEF_SPD, name));
        this.statistics.put(StatisticOfMonster.SPD, new Statistic(StatisticOfMonster.SPD, speed, B_MODIFIER_FOR_ATK_DEF_SPD, name));
        this.statistics.put(StatisticOfMonster.PRC,
                new Statistic(StatisticOfMonster.PRC, DEFAULT_PRC_AGL_VALUE, B_MODIFIER_FOR_PRC_AGL, name));
        this.statistics.put(StatisticOfMonster.AGL,
                new Statistic(StatisticOfMonster.AGL, DEFAULT_PRC_AGL_VALUE, B_MODIFIER_FOR_PRC_AGL, name));
        this.actions = new HashMap<>();
        this.actionNames = new ArrayList<>();
    }
    //creates a new monster with deep copies of all properties of the input monster
    private Monster(Monster monster) {
        this.name = monster.name;
        this.element = monster.element;
        this.monsterNumber = monster.monsterNumber;
        this.isDead = monster.isDead;
        this.statusCondition = monster.statusCondition;
        this.healthPoints = monster.healthPoints.copy();
        this.protectionDuration = monster.protectionDuration;
        this.actions = new HashMap<>();
        this.actionNames = new ArrayList<>();
        for (String actionName : monster.actionNames) {
            this.actionNames.add(actionName);
            this.actions.put(actionName, monster.actions.get(actionName).copy());
        }
        this.statistics = new HashMap<>();
        for (StatisticOfMonster stat : StatisticOfMonster.values()) {
            this.statistics.put(stat, monster.statistics.get(stat).copy());
        }
    }
    /**
     * Retrieves an action by its name.
     * @param actionName the name of the action
     * @return the corresponding Action object
     */
    public Action getAction(String actionName) {
        return this.actions.get(actionName).copy();
    }
    /**
     * Gets the effective value of a given statistic, considering status conditions.
     * @param statisticToGet the statistic to retrieve
     * @return the effective value of the statistic
     */
    public double getStatisticEffectiveValue(StatisticOfMonster statisticToGet) {
        return this.statistics.get(statisticToGet).getEffectiveValue()
                * this.statusCondition.getStatisticReducingFactor(statisticToGet);
    }
    /**
     * Changes the offset value of a statistic.
     * @param statisticToChange the statistic to modify
     * @param offsetValue the offset value to apply
     * @param isSelfCast whether the change is self-inflicted
     * @param isFirstDebugExecution indicates if this method is being executed for the first time during this turn, while
     *     the {@link edu.kit.kastel.logistics.Competition} is being debugged
     * @return the result of the operation containing possible message
     */
    public Result changeStatisticOffsetValue(StatisticOfMonster statisticToChange, int offsetValue, boolean isSelfCast,
                                             boolean isFirstDebugExecution) {
        return this.statistics.get(statisticToChange).changeValueOffset(offsetValue, isSelfCast, isFirstDebugExecution);
    }
    /**
     * Retrieves the current status condition of the monster.
     * @return the monster's current status condition
     */
    public StatusCondition getMonsterStatus() {
        return this.statusCondition;
    }
    /**
     * Checks if the monster is dead.
     * @return true if the monster is dead, false otherwise
     */
    public boolean isDead() {
        return this.isDead;
    }
    /**
     * Deals damage to the monster.
     * @param damage the amount of damage
     * @param isSelfCast whether the damage is self-inflicted
     * @param isFirstDebugExecution indicates if this method is being executed for the first time during this turn, while
     *      the {@link edu.kit.kastel.logistics.Competition} is being debugged
     * @return the result of the damage operation containing possible message
     */
    public Result dealDamage(int damage, boolean isSelfCast, boolean isFirstDebugExecution) {
        Result inflictDMGResult = this.healthPoints.inflictDamage(damage, isSelfCast, isFirstDebugExecution);
        this.isDead = inflictDMGResult.getType() == ResultType.FAILURE;
        return Result.success(inflictDMGResult.getMessage());
    }
    /**
     * Heals the monster by a specified value.
     * @param value the amount to heal
     * @param isFirstDebugExecution indicates if this method is being executed for the first time during this turn, while
     *        the {@link edu.kit.kastel.logistics.Competition} is being debugged
     * @return the result of the healing operation with a possible message
     */
    public Result healHP(int value, boolean isFirstDebugExecution) {
        return this.healthPoints.heal(value, isFirstDebugExecution);

    }
    /**
     * Gets the current health points of the monster.
     * @return the current HP value
     */
    public int getHPValue() {
        return this.healthPoints.getHPValue();
    }
    /**
     * Gets the base health points of the monster.
     * @return the base HP value
     */
    public int getBaseHPValue() {
        return this.healthPoints.getBaseValue();
    }
    /**
     * Protects the monster's statistics for a given duration.
     * @param duration the duration of protection
     * @return the result of the protection operation containing a possible message
     */
    public Result protectHP(int duration) {
        unprotect();
        this.protectionDuration = duration + EXTRA_ROUND_DURATION_FOR_SUBTRACTION;
        return this.healthPoints.protect();
    }
    /**
     * Protects the monster's statistics for a given duration.
     * @param duration the duration of protection
     * @return the result of the protection operation containing a message
     */
    public Result protectStats(int duration) {
        unprotect();
        this.protectionDuration = duration + EXTRA_ROUND_DURATION_FOR_SUBTRACTION;
        for (StatisticOfMonster stat : StatisticOfMonster.values()) {
            this.statistics.get(stat).protect();
        }
        return Result.success(STAT_SET_PROTECTED_MESSAGE.formatted(this.getName()));
    }
    /**
     * Removes protection from health points and statistics.
     */
    public void unprotect() {
        for (StatisticOfMonster stat : StatisticOfMonster.values()) {
            this.statistics.get(stat).unprotect();
        }
        this.healthPoints.unprotect();
    }
    /**
     * Sets the monster's status condition if monster doesn't have any.
     * @param conditionToSet the status condition to apply
     * @return the result of the operation with a possible message
     */
    public Result setStatusCondition(StatusCondition conditionToSet) {
        if (this.statusCondition.equals(StatusCondition.OK)) {
            this.statusCondition = conditionToSet;
            return Result.success(conditionToSet.getGetsStatusMessage().formatted(this.getName()));
        }
        return Result.success();
    }
    /**
     * Attempts to end the monster's current status condition based on a random chance.
     * @param randomizer the randomizer used to determine success
     * @return the result of the status condition check containing a possible message
     */
    public Result tryEndStatCondition(Randomizer randomizer) {
        if (this.statusCondition != StatusCondition.OK) {
            StatusCondition previousCondition = this.statusCondition;
            Optional<Boolean> possibleSuccessIndicator = randomizer.getSuccessOrFail(END_CONDITION_CHANCE);
            if (possibleSuccessIndicator.isEmpty()) {
                randomizer.setFirstBooleanForEndCondition();
                return Result.needsDebug(DebugType.BOOLEAN, DEBUG_STATE_CHECK_NAME
                        .formatted(this.statusCondition.toString().toLowerCase()));
            } else {
                this.statusCondition = possibleSuccessIndicator.get() ? StatusCondition.OK : this.statusCondition;
                return Result.success(this.statusCondition == StatusCondition.OK
                        ? previousCondition.getLosesStatusMessage().formatted(this.getName())
                        : previousCondition.getHasStatusMessage().formatted(this.getName()));
            }
        }
        randomizer.skipFirstBooleanValue();
        return Result.failed();
    }
    /**
     * Gets a string to format to show the info of this monster.
     * @return a string to be formatted with all needed info for a show command
     */
    public String getShowInfo() {
        return this.healthPoints.getHPBoxes() + NUMBER_MONSTER_AND_HIS_TURN_FORMAT_MESSAGE
                + this.getName() + START_STATUS_INFO_SYMBOL + (isDead() ? IS_DEAD_STATUS : getMonsterStatus()) + END_STATUS_INFO_SYMBOL;
    }
    /**
     * Retrieves the monster's name with possible monster number.
     * @return the monster's name
     */
    public String getName() {
        return this.name + (monsterNumber > NO_NUMBER_ASSIGNED ? MONSTER_NUMBER_PREFIX + this.monsterNumber : "");
    }
    /**
     * Updates the monster's number and creates a copy of it.
     * @param number the new monster number
     * @return a copy of the updated monster
     */
    public Monster updateMonsterNumberAndCopy(int number) {
        this.monsterNumber = number;
        this.healthPoints.updateMonsterName(getName());
        for (StatisticOfMonster stat : StatisticOfMonster.values()) {
            this.statistics.get(stat).updateMonsterName(getName());
        }
        return this.copy();
    }
    /**
     * Adds a new valid action to the monster.
     * @param newAction the action to add
     * @return true if the action was added, false otherwise
     */
    public boolean addAction(Action newAction) {
        if (newAction == null || this.actionNames.contains(newAction.getName())) {
            return false;
        }
        this.actionNames.add(newAction.getName());
        this.actions.put(newAction.getName(), newAction.copy());
        return true;
    }
    /**
     * Returns a formatted string representing the monster's basic information.
     * @return a string representation of the monster
     */
    public String showSelf() {
        return SHOW_SELF_MESSAGE.formatted(this.getName(), this.element, this.getBaseHPValue(),
                this.statistics.get(StatisticOfMonster.ATK).getBaseValue(),
                this.statistics.get(StatisticOfMonster.DEF).getBaseValue(),
                this.statistics.get(StatisticOfMonster.SPD).getBaseValue());
    }
    /**
     * Retrieves information about the monster's actions.
     * @return a string containing action information
     */
    public String getActionsInfo() {
        StringJoiner output = new StringJoiner(System.lineSeparator());
        output.add(SHOW_ACTIONS_MESSAGE.formatted(this.getName()));
        for (String actionName : actionNames) {
            output.add(actions.get(actionName).getActionInfo());
        }
        return output.toString();
    }
    /**
     * Retrieves a formatted string showing the monster's statistics.
     * @return a string containing the monster's statistics
     */
    public String getShowStatsInfo() {
        StringJoiner outputString = new StringJoiner(SHOW_STAT_JOINER_SIGN);
        outputString.add(String.format(HP_OUTPUT_FORMAT_MESSAGE, this.getHPValue(), this.healthPoints.getBaseValue()));
        for (StatisticOfMonster stat : StatisticOfMonster.values()) {
            Statistic statToPrint = this.statistics.get(stat);
            String stringToAdd = statToPrint.isChanged()
                    ? STAT_OUTPUT_OFFSET_FORMAT_TEXT.formatted(stat.toString(), statToPrint.getBaseValue(), statToPrint.getOffsetString())
                    : String.format(STAT_OUTPUT_WITHOUT_OFFSET_FORMAT_MESSAGE, stat.toString(), statToPrint.getBaseValue());
            outputString.add(stringToAdd);
        }
        return SHOW_STATISTICS_MESSAGE.formatted(this.getName()) + System.lineSeparator() + outputString;
    }
    /**
     * Advances the monster to the next round, updating protections.
     * @return the result of the round advancement containing possible message
     */
    public Result nextRound() {
        if (this.protectionDuration > NO_PROTECTION_LEFT) {
            this.protectionDuration -= DECREMENT_DURATION;
            if (this.protectionDuration == NO_PROTECTION_LEFT) {
                unprotect();
                return Result.success(STAT_SET_UNPROTECTED_MESSAGE.formatted(this.getName()));
            }
        }
        return Result.success();
    }
    /**
     * Creates a copy of the current monster.
     * @return a new Monster object identical to this one
     */
    public Monster copy() {
        return new Monster(this);
    }
}
