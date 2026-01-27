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
 * Class representing a protection effect.
 * @author upgcv
 */
public class ProtectionEffect implements Effect, ExecutableEffect {
    private static final String ILLEGAL_DURATION_MESSAGE = "Error, this duration value is not allowed!";
    private static final String DEBUG_HIT_QUESTION_NAME = "protection hit";
    private static final String DEBUG_DURATION_QUESTION_NAME = "protection duration";
    private static final String NO_DMG_REGEX = "--";
    private static final int DEFAULT_DURATION = -1;

    private final ProtectionTarget whatToProtect;

    private final int successRate;
    private final int duration;
    private final int bottomBorder;
    private final int upperBorder;

    /**
     * Creates a new Protection effect with provided properties.
     * @param whatToProtect string representation of the value to protect
     *                      ("health" or "stats"; otherwise throws an Exception)
     * @param duration duration in rounds for this effect
     * @param successRate hit rate of this effect
     */
    public ProtectionEffect(ProtectionTarget whatToProtect, int duration, int successRate) {
        this(whatToProtect, duration, DEFAULT_DURATION, DEFAULT_DURATION, successRate);
    }

    /**
     * Creates a new Protection effect with provided properties.
     * @param whatToProtect string representation of the value to protect
     *                      ("health" or "stats"; otherwise throws an Exception)
     * @param bottomBorder bottom border for a random duration
     * @param upperBorder upper border for a random duration
     * @param successRate hit rate of this effect
     */
    public ProtectionEffect(ProtectionTarget whatToProtect, int bottomBorder, int upperBorder, int successRate) {
        this(whatToProtect, DEFAULT_DURATION, bottomBorder, upperBorder, successRate);
    }

    private ProtectionEffect(ProtectionTarget whatToProtect, int duration, int bottomBorder, int upperBorder, int successRate) {
        this.whatToProtect = whatToProtect;
        this.duration = duration;
        this.successRate = successRate;
        this.bottomBorder = bottomBorder;
        this.upperBorder = upperBorder;
    }

    @Override
    public Queue<ExecutableEffect> provideQueueOfSelf(Randomizer randomizer) {
        return new LinkedList<>(List.of(this.copyExecutableEffect()));
    }

    @Override
    public Result executeEffect(Monster user, Monster target, Randomizer randomizer) {
        Optional<Boolean> possibleSuccessIndicator = randomizer.getSuccessOrFail(this.successRate
                * user.getStatisticEffectiveValue(StatisticOfMonster.PRC));
        if (possibleSuccessIndicator.isEmpty()) {
            return Result.needsDebug(DebugType.BOOLEAN, DEBUG_HIT_QUESTION_NAME);
        } else if (!possibleSuccessIndicator.get()) {
            return Result.failed();
        }
        int durationOfThisExecution = DEFAULT_DURATION;
        if (this.duration == DEFAULT_DURATION && !(DEFAULT_DURATION < this.bottomBorder && this.bottomBorder < this.upperBorder)) {
            throw new IllegalArgumentException(ILLEGAL_DURATION_MESSAGE);
        } else if (this.duration == DEFAULT_DURATION) {
            Optional<Integer> possibleIntegerValue = randomizer.getRandomInt(this.bottomBorder, this.upperBorder);
            if (possibleIntegerValue.isEmpty()) {
                return Result.needsDebug(DebugType.INTEGER, DEBUG_DURATION_QUESTION_NAME);
            } else {
                durationOfThisExecution = possibleIntegerValue.get();
            }
        }
        durationOfThisExecution = (durationOfThisExecution == DEFAULT_DURATION) ? this.duration : durationOfThisExecution;
        Result resultOfProtection;
        if (this.whatToProtect == ProtectionTarget.HEALTH) {
            resultOfProtection = user.protectHP(durationOfThisExecution);
        } else {
            resultOfProtection = user.protectStats(durationOfThisExecution);
        }
        return resultOfProtection;
    }


    @Override
    public boolean requiresTarget() {
        return false;
    }

    @Override
    public Effect copyEffect() {
        return new ProtectionEffect(this.whatToProtect, this.duration, this.bottomBorder, this.upperBorder, this.successRate);
    }

    @Override
    public ExecutableEffect copyExecutableEffect() {
        return new ProtectionEffect(this.whatToProtect, this.duration, this.bottomBorder, this.upperBorder, this.successRate);
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
