package edu.kit.kastel.objects.actions.effects;

import edu.kit.kastel.objects.Element;
import edu.kit.kastel.objects.monsters.Monster;
import edu.kit.kastel.objects.monsters.StatisticOfMonster;
import edu.kit.kastel.logistics.Randomizer;

import java.util.Optional;

/**
 * Class representing a strength-related effect.
 * @author upgcv
 */
public abstract class StrengthDefinedEffect implements Effect, ExecutableEffect {
    private static final String ILLEGAL_RELATIVE_STRENGTH_VALUE =
            "Error, this percentage as value of relative strength factor is not allowed!";
    private static final String DIRECT_HIT_MESSAGE = "Critical hit!";
    private static final String EFFECTIVE_AGAINST_MONSTER = "It is very effective!";
    private static final String NOT_EFFECTIVE_AGAINST_MONSTER = "It is not very effective...";

    private static final double NOT_EFFECTIVE_VALUE = 0.5;
    private static final double VERY_EFFECTIVE_VALUE = 2.0;
    private static final int MAX_PERCENTAGE_RATE = 100;
    private static final int DIRECT_HIT_MODIFIER = 10;
    private static final int DIRECT_HIT_FACTOR = 2;
    private static final double SAME_ELEMENT_FACTOR = 1.5;
    private static final int DEFAULT_MULTIPLICATION_FACTOR = 1;
    private static final double UPPER_THRESHOLD_RELATIVE_STRENGTH_VALUE = 100.0;
    private static final double BOTTOM_THRESHOLD_RELATIVE_STRENGTH_VALUE = 0.0;
    private static final double UPPER_THRESHOLD_RANDOM_FACTOR = 1.0;
    private static final double BOTTOM_THRESHOLD_RANDOM_FACTOR = 0.85;
    private static final double NORMALISATION_FACTOR = 1.0 / 3.0;


    /**
     * Indicates if this effect requires a target to be executed.
     */
    protected final boolean requiresTarget;
    /**
     * Marks which element the action that has this effect has.
     */
    protected final Element element;
    /**
     * Strength value of this effect.
     */
    protected final int strength;
    /**
     * Type of strength this effect has.
     */
    protected final StrengthType strengthType;
    /**
     * Hit rate this effect has.
     */
    protected final int successRate;

    /**
     * Keeps the message if the direct hit was done, and an empty line if it was not done/effect was not executed yet.
     */
    protected String directHitMessage = "";

    /**
     * Constructs an effect, that is related to strength evaluations.
     * @param element the element this effect has
     * @param requiresTarget indicates if this effect requires a target monster to be used on
     * @param strength strength value of this effect
     * @param strengthType strength type of this effect
     * @param successRate hit rate value
     * @throws IllegalArgumentException if relative strength value is out of bounds [0,100]
     */
    protected StrengthDefinedEffect(Element element, boolean requiresTarget, int strength,
                                    StrengthType strengthType, int successRate) {
        this.strengthType = strengthType;
        this.element = element;
        this.requiresTarget = requiresTarget;

        if (strengthType.equals(StrengthType.REL)) {
            if (strength > UPPER_THRESHOLD_RELATIVE_STRENGTH_VALUE
                    || strength < BOTTOM_THRESHOLD_RELATIVE_STRENGTH_VALUE) {
                throw new IllegalArgumentException(ILLEGAL_RELATIVE_STRENGTH_VALUE);
            }
        }
        this.strength = strength;
        this.successRate = successRate;
    }

    @Override
    public String getHitRateValue() {
        return String.valueOf(this.successRate);
    }

    @Override
    public boolean requiresTarget() {
        return this.requiresTarget;
    }
    /**
     * Calculates strength value of this effect if the type is BASE.
     * @param user the monster that uses this effect
     * @param target the monster on which the effect is to be used
     * @param randomizer the randomizer that gets the values
     * @return damage/healing to be inflicted or null if this effect needs debugging
     */
    protected Optional<Double> calcStrengthValue(Monster user, Monster target, Randomizer randomizer) {
        if (this.strengthType.equals(StrengthType.BASE)) {
            double elementalFactor = Element.getEffectivenessGrade(this.element, target.element);

            double statFactor = user.getStatisticEffectiveValue(StatisticOfMonster.ATK)
                    / target.getStatisticEffectiveValue(StatisticOfMonster.DEF);

            Optional<Double> directHitFactor = calcDirectHitFactor(user, target, randomizer);
            if (directHitFactor.isEmpty()) {
                return Optional.empty();
            }
            double sameElementFactor = element.equals(user.element)
                    ? SAME_ELEMENT_FACTOR : DEFAULT_MULTIPLICATION_FACTOR;
            Optional<Double> possibleRandomFactor = randomizer
                    .getRandomDouble(BOTTOM_THRESHOLD_RANDOM_FACTOR, UPPER_THRESHOLD_RANDOM_FACTOR);
            double randomFactor;
            if (possibleRandomFactor.isPresent()) {
                randomFactor = possibleRandomFactor.get();
            } else {
                return Optional.empty();
            }
            return Optional.of(strength * elementalFactor * statFactor * directHitFactor.get()
                    * sameElementFactor * randomFactor * NORMALISATION_FACTOR);
        } else {
            return Optional.of(this.strengthType.equals(StrengthType.REL)
                    ? target.getBaseHPValue() * this.strength / UPPER_THRESHOLD_RELATIVE_STRENGTH_VALUE
                    : this.strength);
        }
    }


    private Optional<Double> calcDirectHitFactor(Monster user, Monster target, Randomizer randomizer) {
        Optional<Boolean> isDirectHit = randomizer.getSuccessOrFail(Math.pow(DIRECT_HIT_MODIFIER,
                -(target.getStatisticEffectiveValue(StatisticOfMonster.SPD) / user.getStatisticEffectiveValue(StatisticOfMonster.SPD)))
                * MAX_PERCENTAGE_RATE);
        if (isDirectHit.isEmpty()) {
            return Optional.empty();
        }
        this.directHitMessage = isDirectHit.get() ? DIRECT_HIT_MESSAGE : this.directHitMessage;
        return Optional.of(isDirectHit.get() ? ((double) DIRECT_HIT_FACTOR) : ((double) DEFAULT_MULTIPLICATION_FACTOR));
    }

    /**
     * This method returns a message if the grade of the effectiveness can be represented with this message.
     * @param effectivenessGrade value of the grade
     * @return the message if the grade corresponds a possible effectiveness message, otherwise an empty String
     */
    protected static String getEffectivenessMessage(double effectivenessGrade) {
        if (effectivenessGrade == NOT_EFFECTIVE_VALUE) {
            return NOT_EFFECTIVE_AGAINST_MONSTER;
        } else if (effectivenessGrade == VERY_EFFECTIVE_VALUE) {
            return EFFECTIVE_AGAINST_MONSTER;
        }
        return "";
    }

}
