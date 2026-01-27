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
 * Class representing a DamageEffect of the Application.
 * @author upgcv
 */
public class DamageEffect extends StrengthDefinedEffect {
    private static final String MONSTER_FAINTED_MESSAGE = "%s faints!";
    private static final String DEBUG_NAME_OF_HIT_RATE = "attack hit";
    private static final String DEBUG_NAME_OF_CRITICAL_FACTOR = "critical hit";
    private static final String DEBUG_NAME_OF_RANDOM_FACTOR = "random factor";

    private static final int TARGET_NOT_NEEDED_FACTOR = 1;

    private final boolean isFirstStrengthEffectInAction;

    /**
     * Creates a new Damage effect with given properties.
     * @param element element of the action this effect belongs to
     * @param isFirstStrengthEffect indicates if this effect first in action is
     * @param requiresTarget indicates if this effect needs a target to be used on
     * @param strength start strength value
     * @param strengthType type of strength
     * @param successRate hit rate
     */
    public DamageEffect(Element element, boolean isFirstStrengthEffect, boolean requiresTarget,
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
        if (user.isDead()) {
            return Result.failed();
        }
        Monster target = super.requiresTarget ? possibleTarget : user;
        double userTargetInteraction =  user.getStatisticEffectiveValue(StatisticOfMonster.PRC)
                / (super.requiresTarget ? target.getStatisticEffectiveValue(StatisticOfMonster.AGL) : TARGET_NOT_NEEDED_FACTOR);
        Optional<Boolean> isSuccess = randomizer.getSuccessOrFail(super.successRate * userTargetInteraction);
        if (isSuccess.isEmpty()) {
            return Result.needsDebug(DebugType.BOOLEAN, DEBUG_NAME_OF_HIT_RATE);
        } else if (!isSuccess.get()) {
            return Result.failed();
        }
        int totalDamage;
        Optional<Double> possibleTotalDMGValue = calcStrengthValue(user, target, randomizer);
        if (possibleTotalDMGValue.isEmpty()) {
            String infoToAdd = (randomizer.expectedValueType() == DebugType.DOUBLE)
                    ? givePossibleCriticalHitMessage() : givePossibleEffectivenessMessage(target);
            return Result.needsDebug((randomizer.expectedValueType() == DebugType.DOUBLE) ? DebugType.DOUBLE : DebugType.BOOLEAN,
                    (randomizer.expectedValueType() == DebugType.DOUBLE) ? DEBUG_NAME_OF_RANDOM_FACTOR : DEBUG_NAME_OF_CRITICAL_FACTOR,
                    infoToAdd);
        } else {
            totalDamage = (int) Math.ceil(possibleTotalDMGValue.get());
        }
        String ifFirstDmgOrCriticalHitHappened = randomizer.isDebugOn() ? ""
                : givePossibleEffectivenessMessage(target) + givePossibleCriticalHitMessage();

        Result damageResult = target.dealDamage(totalDamage, user.equals(target), randomizer.isProcessFirstExecution());
        if (target.isDead()) {
            return Result.success(ifFirstDmgOrCriticalHitHappened + damageResult.getMessage()
                    + System.lineSeparator() + (MONSTER_FAINTED_MESSAGE.formatted(target.getName())));
        }
        return Result.success(ifFirstDmgOrCriticalHitHappened + damageResult.getMessage());
    }

    private String givePossibleCriticalHitMessage() {
        String stringToReturn = super.directHitMessage.isEmpty() ? "" : super.directHitMessage + System.lineSeparator();
        super.directHitMessage = "";
        return stringToReturn;
    }

    private String givePossibleEffectivenessMessage(Monster target) {
        return (super.strengthType == StrengthType.BASE) && isFirstStrengthEffectInAction
                && !getEffectivenessMessage(Element.getEffectivenessGrade(this.element, target.element)).isBlank()
                ? getEffectivenessMessage(Element.getEffectivenessGrade(this.element, target.element)) + System.lineSeparator()
                : "";
    }

    @Override
    public String getDMGValue() {
        return this.strengthType.getDmgPrefix() + super.strength;
    }

    @Override
    public Effect copyEffect() {
        return new DamageEffect(super.element, this.isFirstStrengthEffectInAction, super.requiresTarget,
                super.strength, super.strengthType, super.successRate);
    }

    @Override
    public ExecutableEffect copyExecutableEffect() {
        return new DamageEffect(super.element, this.isFirstStrengthEffectInAction, super.requiresTarget,
                super.strength, super.strengthType, super.successRate);
    }

}
