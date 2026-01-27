package edu.kit.kastel.objects.actions.effects;

import edu.kit.kastel.commands.Result;
import edu.kit.kastel.logistics.DebugType;
import edu.kit.kastel.objects.monsters.Monster;
import edu.kit.kastel.logistics.Randomizer;
import edu.kit.kastel.objects.monsters.StatisticOfMonster;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

/**
 * Class representing a continue effect.
 * @author upgcv
 */
public class ContinueEffect implements Effect, ExecutableEffect {
    private static final String NO_DMG_REGEX = "--";
    private static final String DEBUG_HIT_NAME = "continue effect hit";

    private final int successRate;

    /**
     * Creates a new ContinueEffect instance.
     * @param successRate determines the hit Rate of this effect instance
     */
    public ContinueEffect(int successRate) {
        this.successRate = successRate;
    }

    @Override
    public Queue<ExecutableEffect> provideQueueOfSelf(Randomizer randomizer) {
        return new LinkedList<>(List.of(this.copyExecutableEffect()));
    }

    @Override
    public Result executeEffect(Monster user, Monster target, Randomizer randomizer) {
        Optional<Boolean> possibleSuccessIndicator = randomizer.getSuccessOrFail(successRate
                * user.getStatisticEffectiveValue(StatisticOfMonster.PRC));
        if (possibleSuccessIndicator.isEmpty()) {
            return Result.needsDebug(DebugType.BOOLEAN, DEBUG_HIT_NAME);
        } else if (!possibleSuccessIndicator.get()) {
            return Result.failed();
        }
        return Result.success();
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }

    @Override
    public String getDMGValue() {
        return NO_DMG_REGEX;
    }

    @Override
    public String getHitRateValue() {
        return String.valueOf(successRate);
    }

    @Override
    public Effect copyEffect() {
        return new ContinueEffect(this.successRate);
    }

    @Override
    public ExecutableEffect copyExecutableEffect() {
        return new ContinueEffect(this.successRate);
    }
}
