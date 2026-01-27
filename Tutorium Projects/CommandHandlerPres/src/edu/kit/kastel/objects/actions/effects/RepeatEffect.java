package edu.kit.kastel.objects.actions.effects;

import edu.kit.kastel.logistics.Randomizer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

/**
 * Class representing a repeat effect.
 * @author upgcv
 */
public class RepeatEffect implements Effect {
    private static final String ILLEGAL_DURATION_MESSAGE = "Error, this duration value is not allowed!";
    private static final String NO_DMG_REGEX = "--";
    private static final int CYCLE_START_INDEX = 0;
    private static final int FIRST_EFFECT_IN_QUEUE = 0;
    private static final int DEFAULT_DURATION = 0;

    private final List<Effect> effects = new ArrayList<>();
    private final boolean isRandomDuration;

    private int bottomBorder;
    private int upperBorder;
    private int repeats;

    /**
     * Creates a repeat effect instance with provided arguments.
     * @param effects list of effects that this effect should execute
     * @param repeats number of repeats of listed effects
     */
    public RepeatEffect(List<Effect> effects, int repeats) {
        initializeEffects(effects);
        this.repeats = repeats;
        this.isRandomDuration = false;
    }
    /**
     * Creates a repeat effect instance with provided arguments.
     * @param effects list of effects that this effect should execute
     * @param bottomBorder bottom border for a random duration
     * @param upperBorder upper border for a random duration
     */
    public RepeatEffect(List<Effect> effects, int bottomBorder, int upperBorder) {
        initializeEffects(effects);
        this.isRandomDuration = true;
        this.upperBorder = upperBorder;
        this.bottomBorder = bottomBorder;
    }

    private RepeatEffect(List<Effect> effects, boolean isRandomDuration, int repeats, int bottomBorder, int upperBorder) {
        initializeEffects(effects);
        if (isRandomDuration) {
            this.bottomBorder = bottomBorder;
            this.upperBorder = upperBorder;
        } else {
            this.repeats = repeats;
        }
        this.isRandomDuration = isRandomDuration;
    }

    private void initializeEffects(List<Effect> effects) {
        for (Effect effect : effects) {
            this.effects.add(effect.copyEffect());
        }
    }

    @Override
    public Queue<ExecutableEffect> provideQueueOfSelf(Randomizer randomizer) {
        if (isRandomDuration && !(DEFAULT_DURATION < this.bottomBorder && this.bottomBorder < this.upperBorder)) {
            throw new IllegalArgumentException(ILLEGAL_DURATION_MESSAGE);
        } else if (isRandomDuration) {
            Optional<Integer> possibleRepeatNumber = randomizer.getRandomInt(this.bottomBorder, this.upperBorder);
            if (possibleRepeatNumber.isEmpty()) {
                return null;
            }
            this.repeats = possibleRepeatNumber.get();
        }
        List<ExecutableEffect> listOfThisExecutableEffects = new ArrayList<>();
        for (Effect effect : this.effects) {
            listOfThisExecutableEffects.add(effect.provideQueueOfSelf(randomizer).poll());
        }
        Queue<ExecutableEffect> queueToProvide = new LinkedList<>();
        for (int i = CYCLE_START_INDEX; i < this.repeats; i++) {
            queueToProvide.addAll(listOfThisExecutableEffects);
        }
        return queueToProvide;
    }

    @Override
    public boolean requiresTarget() {
        for (Effect effect : effects) {
            if (effect.requiresTarget()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDMGValue() {
        for (Effect effect : effects) {
            if (!effect.getDMGValue().equals(NO_DMG_REGEX)) {
                return effect.getDMGValue();
            }
        }
        return NO_DMG_REGEX;
    }

    @Override
    public String getHitRateValue() {
        return effects.get(FIRST_EFFECT_IN_QUEUE).getHitRateValue();
    }

    @Override
    public Effect copyEffect() {
        return new RepeatEffect(this.effects, this.isRandomDuration, this.repeats, this.bottomBorder, this.upperBorder);
    }
}
