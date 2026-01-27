package edu.kit.kastel.objects.actions.effects;

import edu.kit.kastel.logistics.Randomizer;

import java.util.Queue;

/**
 * Interface for an effect that can be used in an action in this application.
 * @author upgcv
 */
public interface Effect {

    /**
     * Method that provides a Queue object with all subExecutableEffects this effect contains.
     * @param randomizer the randomizer to get number of repeats with
     * @return queue of all subExecutableEffects
     */
    Queue<ExecutableEffect> provideQueueOfSelf(Randomizer randomizer);

    /**
     * Method that provides an indicator if this effect needs a target to be used on.
     * @return true if it needs a target, false otherwise
     */
    boolean requiresTarget();

    /**
     * Method that provides a String representation of the DMG value that needs to be displayed in the application.
     * @return dmg value or the absent dmg value ("--")
     */
    String getDMGValue();

    /**
     * Method that provides a String representation of this effect's hit rate.
     * @return string representation of this hit rate value
     */
    String getHitRateValue();

    /**
     * Provides a copy of this effect.
     * @return a new instance of an effect identical to used one
     */
    Effect copyEffect();
}
