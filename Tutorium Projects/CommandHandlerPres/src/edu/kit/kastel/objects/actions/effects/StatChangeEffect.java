package edu.kit.kastel.objects.actions.effects;

import edu.kit.kastel.commands.Result;
import edu.kit.kastel.logistics.DebugType;
import edu.kit.kastel.objects.monsters.Monster;
import edu.kit.kastel.objects.monsters.StatisticOfMonster;
import edu.kit.kastel.logistics.Randomizer;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

/**
 * Class representing inflictStatChange effect.
 * @author upgcv
 */
public class StatChangeEffect implements Effect, ExecutableEffect {
    private static final String DEBUG_QUESTION_EFFECT_NAME = "change %s offset";
    private static final String NO_DMG_REGEX = "--";
    private static final int TARGET_NOT_NEEDED_FACTOR = 1;

    private final StatisticOfMonster statisticToChange;
    private final boolean requiresTarget;
    private final int statisticOffset;
    private final int successRate;

    /**
     * Creates an instance of the inflictStatChange effect.
     * @param requiresTarget indicates if this effect requires a target to be used on
     * @param statisticToChange the stat to be changed
     * @param statisticOffset offset which is going to be inflicted
     * @param successRate hit rate of this effect
     */
    public StatChangeEffect(boolean requiresTarget, StatisticOfMonster statisticToChange, int statisticOffset, int successRate) {
        this.statisticToChange = statisticToChange;
        this.requiresTarget = requiresTarget;
        this.statisticOffset = statisticOffset;
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
        Optional<Boolean> possibleSuccessValue = randomizer.getSuccessOrFail(this.successRate * userTargetInteraction);
        if (possibleSuccessValue.isEmpty()) {
            return Result.needsDebug(DebugType.BOOLEAN, DEBUG_QUESTION_EFFECT_NAME.formatted(this.statisticToChange));
        } else if (!possibleSuccessValue.get()) {
            return Result.failed();
        }
        return target.changeStatisticOffsetValue(this.statisticToChange, this.statisticOffset, user.equals(target),
                randomizer.isProcessFirstExecution());
    }

    @Override
    public boolean requiresTarget() {
        return this.requiresTarget;
    }

    @Override
    public Effect copyEffect() {
        return new StatChangeEffect(this.requiresTarget, this.statisticToChange, this.statisticOffset, this.successRate);
    }

    @Override
    public ExecutableEffect copyExecutableEffect() {
        return new StatChangeEffect(this.requiresTarget, this.statisticToChange, this.statisticOffset, this.successRate);
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
