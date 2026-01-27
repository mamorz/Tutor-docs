package edu.kit.kastel.objects.actions.effects;

import edu.kit.kastel.commands.Result;
import edu.kit.kastel.logistics.Randomizer;
import edu.kit.kastel.objects.monsters.Monster;

/**
 * Interface representing an executable Effect, also all effects except for repeat.
 *
 * @author upgcv
 */
public interface ExecutableEffect {

    /**
     * Method which executes the current effect of an action, that was used by monster {@code user}.
     * Gives back the status of the execution of this effect and possible message to display.
     * @param user the monster which uses the effect
     * @param target the monster on which the effect is being used. If the effect doesn't need a target,
     *               this parameter will be ignored
     * @param randomizer the current object, deciding whether the effect succeeds
     * @return {@link Result} with Type SUCCESS containing a possible message to display if succeeded,
     *      {@link Result} with Type FAILURE
     */
    Result executeEffect(Monster user, Monster target, Randomizer randomizer);

    /**
     * Copies this executable effect.
     * @return a new Object of this executable effect
     */
    ExecutableEffect copyExecutableEffect();
}
