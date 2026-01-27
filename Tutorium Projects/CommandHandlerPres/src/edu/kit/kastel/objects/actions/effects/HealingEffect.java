package edu.kit.kastel.objects.actions.effects;

import edu.kit.kastel.commands.Result;
import edu.kit.kastel.logistics.DebugType;
import edu.kit.kastel.objects.Element;
import edu.kit.kastel.objects.monsters.Monster;
import edu.kit.kastel.logistics.Randomizer;
import edu.kit.kastel.objects.monsters.StatisticOfMonster;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

/**
 * Class representing the healing effect.
 * @author upgcv
 */
public class HealingEffect extends StrengthDefinedEffect {
    private static final String DEBUG_NAME_OF_CRITICAL_FACTOR = "critical hit";
    private static final String DEBUG_NAME_OF_RANDOM_FACTOR = "random factor";
    private static final String DEBUG_NAME_OF_HIT_RATE = "healing hit";

    private static final String NO_DMG_REGEX = "--";
    private static final int TARGET_NOT_NEEDED_FACTOR = 1;

    private final boolean isFirstStrengthEffectInAction;

    /**
     * Constructs an effect, that heals the target.
     * @param element the element this effect has
     * @param isFirstStrengthEffect indicates if this strength defined effect is the first in an action
     * @param requiresTarget indicates if this effect requires a target monster to be used on
     * @param strength strength value of this effect
     * @param strengthType strength type of this effect
     * @param successRate hit rate value
     * @throws IllegalArgumentException if relative strength value is out of bounds [0,100]
     */
    public HealingEffect(Element element, boolean isFirstStrengthEffect, boolean requiresTarget,
                         int strength, StrengthType strengthType, int successRate) {
        super(element, requiresTarget, strength, strengthType, successRate);
        this.isFirstStrengthEffectInAction = isFirstStrengthEffect;
    }

    @Override
    public Queue<ExecutableEffect> provideQueueOfSelf(Randomizer randomizer) {
        return new LinkedList<>(List.of(this.copyExecutableEffect()));
    }

    @Override
    public Result executeEffect(Monster user, Monster possibleTarget, Randomizer randomizer) {
        Monster target = super.requiresTarget ? possibleTarget : user;
        double userTargetInteraction =  user.getStatisticEffectiveValue(StatisticOfMonster.PRC)
                / (super.requiresTarget ? target.getStatisticEffectiveValue(StatisticOfMonster.AGL) : TARGET_NOT_NEEDED_FACTOR);
        Optional<Boolean> possibleSuccessIndicator = randomizer.getSuccessOrFail(super.successRate * userTargetInteraction);
        if (possibleSuccessIndicator.isEmpty()) {
            return Result.needsDebug(DebugType.BOOLEAN, DEBUG_NAME_OF_HIT_RATE);
        } else if (!possibleSuccessIndicator.get()) {
            return Result.failed();
        }
        int totalHealing;
        Optional<Double> possibleTotalDMGValue = calcStrengthValue(user, target, randomizer);
        if (possibleTotalDMGValue.isEmpty()) {
            return Result.needsDebug(randomizer.expectedValueType(), randomizer.expectedValueType() == DebugType.DOUBLE
                    ? DEBUG_NAME_OF_RANDOM_FACTOR : DEBUG_NAME_OF_CRITICAL_FACTOR,
                    randomizer.expectedValueType() == DebugType.DOUBLE
                            ? givePossibleCriticalHitMessage() : givePossibleEffectivenessMessage(target));
        } else {
            totalHealing = (int) Math.ceil(possibleTotalDMGValue.get());
        }
        String ifFirstDmgOrCriticalHitHappened = randomizer.isDebugOn() ? ""
                : givePossibleEffectivenessMessage(target) + givePossibleCriticalHitMessage();
        Result healingResult = target.healHP(totalHealing, randomizer.isProcessFirstExecution());
        return Result.success(ifFirstDmgOrCriticalHitHappened + healingResult.getMessage());
    }

    private String givePossibleEffectivenessMessage(Monster target) {
        return (super.strengthType == StrengthType.BASE) && isFirstStrengthEffectInAction
                && !getEffectivenessMessage(Element.getEffectivenessGrade(this.element, target.element)).isBlank()
                ? getEffectivenessMessage(Element.getEffectivenessGrade(this.element, target.element)) + System.lineSeparator()
                : "";
    }

    private String givePossibleCriticalHitMessage() {
        String stringToReturn = super.directHitMessage.isEmpty() ? "" : super.directHitMessage + System.lineSeparator();
        super.directHitMessage = "";
        return stringToReturn;
    }

    @Override
    public String getDMGValue() {
        return NO_DMG_REGEX;
    }

    @Override
    public Effect copyEffect() {
        return new HealingEffect(super.element, this.isFirstStrengthEffectInAction, super.requiresTarget,
                super.strength, super.strengthType, super.successRate);
    }

    @Override
    public ExecutableEffect copyExecutableEffect() {
        return new HealingEffect(super.element, this.isFirstStrengthEffectInAction, super.requiresTarget,
                super.strength, super.strengthType, super.successRate);
    }
}
