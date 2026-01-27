package edu.kit.kastel.objects.actions;

import edu.kit.kastel.commands.Result;
import edu.kit.kastel.commands.ResultType;
import edu.kit.kastel.logistics.DebugType;
import edu.kit.kastel.objects.Element;
import edu.kit.kastel.objects.actions.effects.Effect;
import edu.kit.kastel.objects.actions.effects.ExecutableEffect;
import edu.kit.kastel.objects.monsters.Monster;
import edu.kit.kastel.logistics.Randomizer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringJoiner;

/**
 * This class represents an action that can be performed by a monster.
 * It can include various effects and determine if it needs a target to be executed.
 *
 * @author upgcv
 */
public class Action {
    private static final String DEBUG_QUESTION_FOR_NUMBER_OF_REPEATS = "repeat count";
    private static final String HIT_NOT_SUCCESSFUL_MESSAGE = "The action failed...";
    private static final String SHOW_ACTION_INFO = "%s: ELEMENT %s, Damage %s, HitRate %s";
    private static final String NO_DMG_REGEX = "--";
    private static final String ANNOUNCEMENT_WHAT_IS_BEING_DONE = "%s uses %s!";
    private static final String EFFECTIVE_AGAINST_MONSTER = "It is very effective!";
    private static final String NOT_EFFECTIVE_AGAINST_MONSTER = "It is not very effective...";
    private static final String CONTAINS_EFFECTIVENESS_INFO_REGEX = "(.|" + System.lineSeparator()
            + ")*(It is very effective!|It is not very effective\\.\\.\\.)(.|" + System.lineSeparator() + ")*";

    private static final int FIRST_EFFECT_IN_QUEUE = 0;

    private final String name;
    private final Element element;
    private final List<Effect> effects;
    private StringJoiner messageJoiner;
    private boolean isUsingMessageDisplayed;


    /**
     * Constructs an Action with a name, element, and list of effects.
     *
     * @param name The name of the action.
     * @param element The element type of the action.
     * @param effects The list of effects associated with the action.
     */
    public Action(String name, Element element, List<Effect> effects) {
        this.name = name;
        this.element = element;
        this.effects = new ArrayList<>(effects.size());
        for (Effect effect : effects) {
            this.effects.add(effect.copyEffect());
        }
    }
    private Action(Action oldAction) {
        this(oldAction.getName(), oldAction.element, oldAction.effects);
        this.isUsingMessageDisplayed = oldAction.isUsingMessageDisplayed;
        this.messageJoiner = oldAction.messageJoiner;
    }
    /**
     * Executes the action, applying its effects to the target monster.
     *
     * @param user The monster performing the action.
     * @param target The monster receiving the action.
     * @param randomizer The randomizer for probability-based effects.
     * @return A Result containing the outcome of the action.
     */
    public Result executeAction(Monster user, Monster target, Randomizer randomizer) {
        if (randomizer.isProcessFirstExecution()) {
            this.isUsingMessageDisplayed = false;
        }
        Queue<ExecutableEffect> effectQueue = new LinkedList<>();
        for (Effect effect : this.effects) {
            Queue<ExecutableEffect> nextQueueToAdd = effect.provideQueueOfSelf(randomizer);
            if (nextQueueToAdd == null) {
                return Result.needsDebug(DebugType.INTEGER, DEBUG_QUESTION_FOR_NUMBER_OF_REPEATS);
            }
            effectQueue.addAll(nextQueueToAdd);
        }
        if (user.getMonsterStatus() == StatusCondition.SLEEP) {
            return Result.success(getUsingActionMessage(user).trim());
        }
        if (target == null || target.isDead()) {
            return Result.success(getUsingActionMessage(user) + HIT_NOT_SUCCESSFUL_MESSAGE);
        }
        Result firstEffectResult = effectQueue.remove().executeEffect(user, target, randomizer);
        if (firstEffectResult.getType().equals(ResultType.NEEDS_DEBUG)) {
            return Result.needsDebug(firstEffectResult.getTypeToDebug(), firstEffectResult.getDebugMessageLabel(),
                    firstEffectResult.getMessage().isBlank()
                            ? getUsingActionMessage(user).trim()
                            : (getUsingActionMessage(user) + firstEffectResult.getMessage()));
        }
        if (firstEffectResult.getType().equals(ResultType.FAILURE)) {
            return Result.success(getUsingActionMessage(user) + HIT_NOT_SUCCESSFUL_MESSAGE);
        }
        this.messageJoiner = new StringJoiner(System.lineSeparator());
        addInfoToJoiner(getUsingActionMessage(user).trim(), randomizer);
        addInfoToJoiner(firstEffectResult.getMessage(), randomizer);
        while (!effectQueue.isEmpty()) {
            Result effectResult = effectQueue.poll().executeEffect(user, target, randomizer);
            if (effectResult.getType() == ResultType.SUCCESS) {
                addInfoToJoiner(effectResult.getMessage(), randomizer);
            } else if (effectResult.getType() == ResultType.NEEDS_DEBUG) {
                addInfoToJoiner(effectResult.getMessage(), randomizer);
                return Result.needsDebug(effectResult.getTypeToDebug(), effectResult.getDebugMessageLabel(), this.messageJoiner.toString());
            }
        }
        return Result.success(this.messageJoiner.toString());
    }

    //adds message to joiner if it is not blank and deletes effectiveness messages if they happen to appear multiple times.
    private void addInfoToJoiner(String info, Randomizer randomizer) {
        if (randomizer.isProcessFirstExecution()) {
            if (info.matches(CONTAINS_EFFECTIVENESS_INFO_REGEX)
                    && this.messageJoiner.toString().matches(CONTAINS_EFFECTIVENESS_INFO_REGEX)) {
                List<String> splitInfo = new ArrayList<>(List.of(info.split(System.lineSeparator())));
                splitInfo.remove(EFFECTIVE_AGAINST_MONSTER);
                splitInfo.remove(NOT_EFFECTIVE_AGAINST_MONSTER);
                StringJoiner joiner = new StringJoiner(System.lineSeparator());
                for (String message : splitInfo) {
                    joiner.add(message);
                }
                this.messageJoiner.add(joiner + (info.endsWith(System.lineSeparator()) ? System.lineSeparator() : ""));
            } else if (!info.isBlank()) {
                this.messageJoiner.add(info);
            }
        }
    }

    private String getUsingActionMessage(Monster user) {
        if (!this.isUsingMessageDisplayed) {
            this.isUsingMessageDisplayed = true;
            return ANNOUNCEMENT_WHAT_IS_BEING_DONE.formatted(user.getName(), this.getName()) + System.lineSeparator();
        }
        return "";
    }

    /**
     * Determines if the action requires a target to be executed.
     *
     * @return True if at least one effect requires a target, false otherwise.
     */
    public boolean requiresTarget() {
        for (Effect effect : effects) {
            if (effect.requiresTarget()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves information about the action.
     *
     * @return A formatted string containing the action's name, element, damage, and hit rate.
     */
    public String getActionInfo() {
        String dmgInfoValue = NO_DMG_REGEX;
        for (Effect effect : effects) {
            if (!effect.getDMGValue().equals(dmgInfoValue)) {
                dmgInfoValue = effect.getDMGValue();
                break;
            }
        }
        return String.format(SHOW_ACTION_INFO,
                this.name, this.element, dmgInfoValue, this.effects.get(FIRST_EFFECT_IN_QUEUE).getHitRateValue());
    }

    /**
     * Creates a copy of the current action.
     *
     * @return A new Action object with the same properties.
     */
    public Action copy() {
        return new Action(this);
    }


    /**
     * Retrieves the name of the action.
     *
     * @return The name of the action.
     */
    public String getName() {
        return this.name;
    }

}
