package edu.kit.kastel.objects.monsters;

import java.util.Comparator;

/**
 * Record class representing a monster entered a competition.
 * @param id the competition number of a monster
 * @param monster the instance of a monster
 * @author upgcv
 */
public record CompetitiveMonster(int id, Monster monster) {
    private static final int BOTH_ARGUMENTS_ARE_EQUAL = 0;
    private static final int AN_ARGUMENT_IS_GREATER = 1;

    /**
     * Creates a comparator to sort a Collection of CompetitiveMonsters by their {@code SPD} value.
     * @return new Comparator to use in method sort
     */
    public static Comparator<CompetitiveMonster> getComparatorForSPDValues() {
        return (o1, o2) -> {
            double difference = o2.monster.getStatisticEffectiveValue(StatisticOfMonster.SPD)
                    - o1.monster.getStatisticEffectiveValue(StatisticOfMonster.SPD);
            return difference < BOTH_ARGUMENTS_ARE_EQUAL ? -AN_ARGUMENT_IS_GREATER
                    : difference > BOTH_ARGUMENTS_ARE_EQUAL
                    ? AN_ARGUMENT_IS_GREATER : o1.id - o2.id;
        };
    }
    /**
     * Creates a comparator to sort a Collection of CompetitiveMonsters by their {@code ID} value.
     * @return new Comparator to use in method sort
     */
    public static Comparator<CompetitiveMonster> getComparatorForMonsterNumber() {
        return Comparator.comparingInt(o -> o.id);
    }


}