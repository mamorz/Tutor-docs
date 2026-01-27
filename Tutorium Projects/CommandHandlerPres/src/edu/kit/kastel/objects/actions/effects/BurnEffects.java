package edu.kit.kastel.objects.actions.effects;

import edu.kit.kastel.commands.Result;
import edu.kit.kastel.objects.monsters.Monster;

/**
 * Class representing burn effect on the end of a monster's turn, if it is caught on fire.
 *
 * @author upgcv
 */
public final class BurnEffects {
    private static final String MONSTER_FAINTED = System.lineSeparator() + "%s faints!";
    private static final String TAKES_DMG_FROM_BURNING = "%s takes %d damage from burning!";
    private static final double FACTOR_TO_GET_DMG_VALUE = 0.1;

    private BurnEffects() {
        //utility class
    }

    /**
     * Executes a simple burn effect that deals damage to suffering monster at the end of a round.
     * @param user whom to damage
     * @return Success result with a message about the damage inflicted
     */
    public static Result executeEffect(Monster user) {
        int burningDMG = (int) Math.ceil(user.getBaseHPValue() * FACTOR_TO_GET_DMG_VALUE);
        user.dealDamage(burningDMG, true, true);
        String ifMonsterFainted = user.isDead() ? MONSTER_FAINTED.formatted(user.getName()) : "";
        return Result.success(TAKES_DMG_FROM_BURNING.formatted(user.getName(), burningDMG) + ifMonsterFainted);
    }


}
