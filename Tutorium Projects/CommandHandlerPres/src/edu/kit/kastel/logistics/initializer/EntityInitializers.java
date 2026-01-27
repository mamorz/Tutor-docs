package edu.kit.kastel.logistics.initializer;

import edu.kit.kastel.logistics.GameSetup;
import edu.kit.kastel.objects.Element;

import edu.kit.kastel.objects.actions.StatusCondition;
import edu.kit.kastel.objects.actions.effects.ContinueEffect;
import edu.kit.kastel.objects.actions.effects.DamageEffect;
import edu.kit.kastel.objects.actions.effects.Effect;
import edu.kit.kastel.objects.actions.effects.HealingEffect;
import edu.kit.kastel.objects.actions.effects.ProtectionEffect;
import edu.kit.kastel.objects.actions.effects.ProtectionTarget;
import edu.kit.kastel.objects.actions.effects.StatChangeEffect;
import edu.kit.kastel.objects.actions.effects.StateConditionEffect;
import edu.kit.kastel.objects.actions.effects.StrengthType;
import edu.kit.kastel.objects.monsters.Monster;
import edu.kit.kastel.objects.monsters.StatisticOfMonster;

import java.util.Arrays;

/**
 * Utility class for initializing entities like effects and monsters from input data.
 * This class is not meant to be instantiated.
 * @author upgcv
 */
public final class EntityInitializers {
    private static final String ILLEGAL_EFFECT_TO_CREATE_MESSAGE = "Error, this method does not support creating Repeat effect!";
    private static final String INPUT_LINE_SEPARATOR = " ";
    private static final String REQUIRES_MONSTER_LINE_REGEX = "target";
    private static final String NOT_SPECIFIED_DURATION_TYPE = "random";

    private static final int DURATION_TYPE_POSITION = 2;
    private static final int HIT_RATE_STAT_CHANGE_DMG_HEAL = 4;
    private static final int HIT_RATE_POSITION_STAT_COND_PROTECT_STAT = 3;
    private static final int STRENGTH_VALUE_POSITION = 3;
    private static final int STRENGTH_TYPE_POSITION = 2;
    private static final int STAT_OFFSET_VALUE_POSITION = 3;
    private static final int HIT_RATE_PROTECT_STAT_IF_RANDOM_POSITION = 5;
    private static final int DURATION_POSITION = 2;

    private static final int PROTECT_STAT_BOTTOM_BORDER_POSITION = 3;
    private static final int PROTECT_STAT_UPPER_BORDER_POSITION = 4;
    private static final int USER_TARGET_POSITION = 1;
    private static final int EFFECT_NAME_POSITION = 0;
    private static final int IS_HP_TO_PROTECT_TARGET_POSITION = 1;
    private static final int HIT_RATE_CONTINUE_POSITION = 1;
    private static final int STATUS_CONDITION_POSITION = 2;
    private static final int STAT_TO_PROTECT_POSITION = 2;

    private static final int MONSTER_NAME_POSITION = 1;
    private static final int MONSTER_ELEMENT_POSITION = 2;
    private static final int MAX_HEALTH_VALUE_POSITION = 3;
    private static final int ATK_VALUE_POSITION = 4;
    private static final int DEF_VALUE_POSITION = 5;
    private static final int SPD_VALUE_POSITION = 6;
    private static final int FIRST_ACTION_OF_MONSTER_POSITION = 7;

    private EntityInitializers() {
        //utility class
    }
    /**
     * Initializes an effect based on the provided input. It does not support creating Repeat effects.
     *
     * @param element the element associated with the effect.
     * @param isFirstOfDamageType a flag indicating whether this is the first effect.
     * @param input the input string representing the effect details.
     * @return an initialized effect based on the input, or null if the input is invalid.
     * @throws IllegalArgumentException if the input is for a Repeat effect.
     */
    public static Effect initializeNotRepeatEffect(Element element, boolean isFirstOfDamageType, String input) {
        EffectConfigName effectName = EffectConfigName
                .getFromStringRepresentation(input.trim().split(INPUT_LINE_SEPARATOR)[EFFECT_NAME_POSITION]);
        if (effectName == null) {
            return null;
        }
        String[] splitInput = input.trim().split(INPUT_LINE_SEPARATOR);
        return switch (effectName) {
            case DAMAGE -> initializeDamageOrHealingEffect(element, splitInput, isFirstOfDamageType, true);
            case INFLICT_STATUS_CONDITION -> initializeInflictStatusCondEffect(splitInput);
            case INFLICT_STAT_CHANGE -> initializeInflictStatusChange(splitInput);
            case PROTECT_STAT -> initializeProtectStatus(splitInput);
            case HEAL -> initializeDamageOrHealingEffect(element, splitInput, isFirstOfDamageType, false);
            case REPEAT -> throw new IllegalArgumentException(ILLEGAL_EFFECT_TO_CREATE_MESSAGE);
            case CONTINUE -> initializeContinueEffect(splitInput);
        };
    }
    /**
     * Initializes a monster from a string input and updates the provided game setup configuration.
     *
     * @param inputLine the input string containing the monster details.
     * @param configToUpdate the game setup configuration to update with the monster's actions.
     * @return the initialized monster, or null if the monster couldn't be created or its actions couldn't be added.
     */
    public static Monster initializeMonsterFromInput(String inputLine, GameSetup configToUpdate) {
        String[] splitInputLine = inputLine.trim().split(INPUT_LINE_SEPARATOR);
        String monsterName = splitInputLine[MONSTER_NAME_POSITION];
        Element element = Element.valueOf(splitInputLine[MONSTER_ELEMENT_POSITION]);
        int maxHealth = Integer.parseInt(splitInputLine[MAX_HEALTH_VALUE_POSITION]);
        int baseAttack = Integer.parseInt(splitInputLine[ATK_VALUE_POSITION]);
        int baseDefence = Integer.parseInt(splitInputLine[DEF_VALUE_POSITION]);
        int baseSpeed = Integer.parseInt(splitInputLine[SPD_VALUE_POSITION]);
        Monster newMonster = new Monster(monsterName, element, maxHealth, baseAttack, baseDefence, baseSpeed);
        String[] actions = Arrays.copyOfRange(splitInputLine, FIRST_ACTION_OF_MONSTER_POSITION, splitInputLine.length);
        for (String action : actions) {
            if (configToUpdate.getAction(action) == null || !newMonster.addAction(configToUpdate.getAction(action))) {
                return null;
            }
        }
        return newMonster;
    }

    private static Effect initializeDamageOrHealingEffect(Element element, String[] params, boolean isFirstEffect, boolean isDMGToCreate) {
        boolean requiresTarget = params[USER_TARGET_POSITION].equals(REQUIRES_MONSTER_LINE_REGEX);
        StrengthType strengthType = StrengthType.valueOf(params[STRENGTH_TYPE_POSITION].toUpperCase());
        int strengthValue = Integer.parseInt(params[STRENGTH_VALUE_POSITION]);
        int hitRate = Integer.parseInt(params[HIT_RATE_STAT_CHANGE_DMG_HEAL]);
        return isDMGToCreate ? new DamageEffect(element, isFirstEffect, requiresTarget, strengthValue, strengthType, hitRate)
                : new HealingEffect(element, isFirstEffect, requiresTarget, strengthValue, strengthType, hitRate);
    }

    private static Effect initializeInflictStatusCondEffect(String[] params) {
        boolean requireTargets = params[USER_TARGET_POSITION].equals(REQUIRES_MONSTER_LINE_REGEX);
        StatusCondition condition = StatusCondition.valueOf(params[STATUS_CONDITION_POSITION]);
        int hitRate = Integer.parseInt(params[HIT_RATE_POSITION_STAT_COND_PROTECT_STAT]);
        return new StateConditionEffect(requireTargets, condition, hitRate);
    }

    private static Effect initializeInflictStatusChange(String[] params) {
        boolean requireTargets = params[USER_TARGET_POSITION].equals(REQUIRES_MONSTER_LINE_REGEX);
        StatisticOfMonster statToProtect = StatisticOfMonster.valueOf(params[STAT_TO_PROTECT_POSITION]);
        int statOffset = Integer.parseInt(params[STAT_OFFSET_VALUE_POSITION]);
        int hitRate = Integer.parseInt(params[HIT_RATE_STAT_CHANGE_DMG_HEAL]);
        return new StatChangeEffect(requireTargets, statToProtect, statOffset, hitRate);
    }

    private static Effect initializeProtectStatus(String[] params) {
        ProtectionTarget whatToProtect = ProtectionTarget.valueOf(params[IS_HP_TO_PROTECT_TARGET_POSITION].toUpperCase());
        if (params[DURATION_TYPE_POSITION].equals(NOT_SPECIFIED_DURATION_TYPE)) {
            int bottomBorder = Integer.parseInt(params[PROTECT_STAT_BOTTOM_BORDER_POSITION]);
            int upperBorder = Integer.parseInt(params[PROTECT_STAT_UPPER_BORDER_POSITION]);
            if (upperBorder < bottomBorder) {
                return null;
            }
            int hitRate = Integer.parseInt(params[HIT_RATE_PROTECT_STAT_IF_RANDOM_POSITION]);
            return new ProtectionEffect(whatToProtect, bottomBorder, upperBorder, hitRate);
        } else {
            int duration = Integer.parseInt(params[DURATION_POSITION]);
            int hitRate = Integer.parseInt(params[HIT_RATE_POSITION_STAT_COND_PROTECT_STAT]);
            return new ProtectionEffect(whatToProtect, duration, hitRate);
        }
    }

    private static Effect initializeContinueEffect(String[] params) {
        int hitRate = Integer.parseInt(params[HIT_RATE_CONTINUE_POSITION]);
        return new ContinueEffect(hitRate);
    }
}
