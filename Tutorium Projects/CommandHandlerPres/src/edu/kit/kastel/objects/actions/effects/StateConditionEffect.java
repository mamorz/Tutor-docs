package edu.kit.kastel.objects.actions.effects;

import edu.kit.kastel.commands.Result;
import edu.kit.kastel.logistics.DebugType;
import edu.kit.kastel.objects.actions.StatusCondition;
import edu.kit.kastel.objects.monsters.Monster;
import edu.kit.kastel.logistics.Randomizer;
import edu.kit.kastel.objects.monsters.StatisticOfMonster;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

/**
 * Class representing an inflictStatusCondition effect in the game.
 * @author upgcv
 */
public class StateConditionEffect implements ExecutableEffect, Effect {
    private static final String DEBUG_QUESTION_EFFECT_NAME = "make target %s";
    private static final String NO_DMG_REGEX = "--";
    private static final int TARGET_NOT_NEEDED_FACTOR = 1;

    private final int successRate;
    private final boolean requiresTarget;
    private final StatusCondition statusCondition;

    /**
     * Creates an instance of the inflictStatusCondition effect.
     * @param requiresTarget indicates if the effect requires a target monster to be used on
     * @param statusCondition which status Condition it inflicts
     * @param successRate hit rate of the effect
     */
    public StateConditionEffect(boolean requiresTarget, StatusCondition statusCondition, int successRate) {
        this.statusCondition = statusCondition;
        this.requiresTarget = requiresTarget;
        this.successRate = successRate;
    }

    @Override
    public Queue<ExecutableEffect> provideQueueOfSelf(Randomizer randomizer) {
        return new LinkedList<>(List.of(this.copyExecutableEffect()));
    }

    @Override
    public Result executeEffect(Monster user, Monster possibleTarget, Randomizer randomizer) {
        Monster target = this.requiresTarget ? possibleTarget : user;
        double userTargetInteraction =  user.getStatisticEffectiveValue(StatisticOfMonster.PRC)
                / (this.requiresTarget ? target.getStatisticEffectiveValue(StatisticOfMonster.AGL) : TARGET_NOT_NEEDED_FACTOR);

        Optional<Boolean> possibleSuccessIndicator = randomizer.getSuccessOrFail(this.successRate * userTargetInteraction);
        if (possibleSuccessIndicator.isEmpty()) {
            return Result.needsDebug(DebugType.BOOLEAN, DEBUG_QUESTION_EFFECT_NAME
                    .formatted(this.statusCondition.toString().toLowerCase()));
        }
        if (!possibleSuccessIndicator.get()) {
            return Result.failed();
        }
        return target.setStatusCondition(this.statusCondition);
    }

    @Override
    public boolean requiresTarget() {
        return this.requiresTarget;
    }

    @Override
    public Effect copyEffect() {
        return new StateConditionEffect(this.requiresTarget, this.statusCondition, this.successRate);
    }

    @Override
    public ExecutableEffect copyExecutableEffect() {
        return new StateConditionEffect(this.requiresTarget, this.statusCondition, this.successRate);
    }

    @Override
    public String getDMGValue() {
        return NO_DMG_REGEX;
    }

    @Override
    public String getHitRateValue() {
        return String.valueOf(this.successRate);
    }
}
