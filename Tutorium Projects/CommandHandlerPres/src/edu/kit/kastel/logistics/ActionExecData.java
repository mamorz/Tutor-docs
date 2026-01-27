package edu.kit.kastel.logistics;

import edu.kit.kastel.objects.monsters.CompetitiveMonster;

/**
 * Record class representing the data needed to execute an action in this round during a {@link Competition}.
 * @param user Monster in competition that is using current action
 * @param target Monster in competition current action is to be used on
 * @author upgcv
 */
public record ActionExecData(CompetitiveMonster user, CompetitiveMonster target) {
}
