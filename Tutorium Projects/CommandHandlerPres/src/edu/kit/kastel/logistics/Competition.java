package edu.kit.kastel.logistics;

import edu.kit.kastel.commands.InvalidArgumentException;
import edu.kit.kastel.commands.Result;
import edu.kit.kastel.commands.ResultType;
import edu.kit.kastel.objects.actions.Action;
import edu.kit.kastel.objects.actions.StatusCondition;
import edu.kit.kastel.objects.actions.effects.BurnEffects;
import edu.kit.kastel.objects.monsters.CompetitiveMonster;
import edu.kit.kastel.objects.monsters.Monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
/**
 * Class representing a competition that can be started in the application.
 * @author upgcv
 */
public class Competition {
    private static final String COMPETITION_STARTED_MESSAGE = "The %d monsters enter the competition!";
    private static final String SHOW_HIS_TURN_CHAR = "*";
    private static final String HAS_NO_ACTION_NAME_MESSAGE = "Error, %s has no action named %s!";
    private static final String HAS_NO_MONSTER_NAME_MESSAGE = "Error, there is no monster in the competition named %s!";
    private static final String ONE_MONSTER_LEFT_ANNOUNCEMENT = System.lineSeparator()
            + "%s has no opponents left and wins the competition!";
    private static final String NO_MONSTER_LEFT_ANNOUNCEMENT = System.lineSeparator()
            + "All monsters have fainted. The competition ends without a winner!";
    private static final String NO_TARGET_FOR_BIG_COMPETITION_MESSAGE
            = "Error, you should have entered the target for the chosen action!";
    private static final String DECIDE_MONSTER_ACTION = System.lineSeparator() + "What should %s do?";
    private static final String ANNOUNCEMENT_WHOSE_TURN = System.lineSeparator() + "It's %s's turn.";
    private static final String ANNOUNCEMENT_ACTION_IS_NULL = "%s passes!";

    private static final int CYCLE_FIRST_INDEX = 0;
    private static final int THE_ONLY_MONSTER_ALIVE_INDEX = 0;
    private static final int MIN_MONSTERS_TO_RUN_COMPETITION = 2;
    private static final int COMPETITIVE_INDEX_DIFFERENCE = 1;
    private static final int START_INDEX_COUNT_MULTIPLE_OCCURRENCES = 1;
    private static final int OFFSET_TO_CHOOSE_OTHER_MONSTER = 1;

    private final Randomizer randomizer;
    private final List<ActionExecData> dataForActions = new ArrayList<>();
    private final Map<CompetitiveMonster, Action> actionsForThisRound = new HashMap<>();
    private final Map<String, CompetitiveMonster> monsterMap = new HashMap<>();
    private final List<CompetitiveMonster> monsters = new ArrayList<>();
    private final List<CompetitiveMonster> losers = new ArrayList<>();
    private final List<String> monsterNames = new ArrayList<>();

    private String pendingMessage;
    private int turnIndex;
    private DebugCompetitionInfo debugInfo;
    private boolean isDecided;
    /**
     * Constructs a competition with the given monsters and debug mode flag.
     * @param monsters    an array of participating monsters
     * @param isDebugMode whether the competition should run in debug mode
     */
    public Competition(Monster[] monsters, boolean isDebugMode) {
        initializeMonstersCollectionsAndNonFinalAttributes(monsters);
        this.randomizer = new Randomizer();
        if (isDebugMode) {
            this.randomizer.turnDebugOn();
        }
    }
    /**
     * Constructs a competition with the given monsters, running in normal mode.
     * Does not have a seed nor is in debug mode.
     * @param monsters an array of participating monsters
     */
    public Competition(Monster[] monsters) {
        this(monsters, false);
    }
    /**
     * Constructs a competition with the given monsters and a specific seed for randomizer.
     * @param monsters an array of participating monsters
     * @param seed     the seed for the randomizer
     */
    public Competition(Monster[] monsters, long seed) {
        initializeMonstersCollectionsAndNonFinalAttributes(monsters);
        this.randomizer = new Randomizer(seed);
    }
    /**
     * Constructs a new competition using the same randomizer from a previous competition.
     * @param monsters       an array of participating monsters
     * @param oldCompetition an existing competition whose randomizer should be reused
     */
    public Competition(Monster[] monsters, Competition oldCompetition) {
        initializeMonstersCollectionsAndNonFinalAttributes(monsters);
        this.randomizer = oldCompetition.randomizer;
    }
    private void initializeMonstersCollectionsAndNonFinalAttributes(Monster[] monsters) {
        for (int index = CYCLE_FIRST_INDEX; index < monsters.length; index++) {
            CompetitiveMonster monsterToAdd = new CompetitiveMonster(index + COMPETITIVE_INDEX_DIFFERENCE, monsters[index].copy());
            this.monsters.add(monsterToAdd);
            this.monsterNames.add(monsters[index].getName());
        }
        if (Set.of(this.monsterNames).size() != this.monsters.size()) {
            renameMultipleNameOccurrences();
        }
        for (CompetitiveMonster monster : this.monsters) {
            monsterMap.put(monster.monster().getName(), monster);
        }
        this.isDecided = false;
        this.turnIndex = CYCLE_FIRST_INDEX;
        this.pendingMessage = "";
        this.debugInfo = new DebugCompetitionInfo(false, null);
    }
    private void renameMultipleNameOccurrences() {
        for (int index = CYCLE_FIRST_INDEX; index < this.monsterNames.size(); index++) {
            if (this.monsterNames.lastIndexOf(this.monsterNames.get(index)) != index) {
                renameAllNameOccurrences(this.monsterNames.get(index));
            }
        }
    }
    private void renameAllNameOccurrences(String nameToReplace) {
        int countOfMonster = START_INDEX_COUNT_MULTIPLE_OCCURRENCES;
        for (int monsterIndex = CYCLE_FIRST_INDEX; monsterIndex < this.monsterNames.size(); monsterIndex++) {
            if (this.monsterNames.get(monsterIndex).equals(nameToReplace)) {
                this.monsters.set(monsterIndex, new CompetitiveMonster((monsterIndex + COMPETITIVE_INDEX_DIFFERENCE),
                        this.monsters.get(monsterIndex).monster().updateMonsterNumberAndCopy(countOfMonster++)));
            }
        }
    }
    /**
     * Gets the debug info that is currently needed.
     * @return a debug info object containing the info needed for debug
     */
    public DebugCompetitionInfo getDebugInfo() {
        return this.debugInfo;
    }
    /**
     * Checks if one or zero monsters are left in the competition, also if the competition is decided.
     * @return true if the competition is decided, false otherwise
     */
    public boolean isDecided() {
        return this.isDecided;
    }
    /**
     * Gets the pending message to be displayed.
     * @return the pending message as a string
     */
    public String getPendingMessage() {
        return this.pendingMessage + (this.pendingMessage.isBlank() ? "" : System.lineSeparator());
    }
    /**
     * Retrieves the current status information of all monsters in the competition.
     * @return a formatted string containing the status of each monster
     */
    public String getShowInfo() {
        StringJoiner output = new StringJoiner(System.lineSeparator());
        List<CompetitiveMonster> allMonstersTogether = new ArrayList<>(this.monsters);
        allMonstersTogether.addAll(this.losers);
        allMonstersTogether.sort(CompetitiveMonster.getComparatorForMonsterNumber());
        for (CompetitiveMonster monster : allMonstersTogether) {
            output.add(monster.monster().getShowInfo().formatted(monster.id(), charIfMonstersTurn(monster)));
        }
        return output.toString();
    }
    private String charIfMonstersTurn(CompetitiveMonster monster) {
        boolean ifHisTurn = this.monsters
                .get((this.actionsForThisRound.size() + this.turnIndex) % (this.monsters.size() + this.losers.size()))
                .equals(monster);
        return ifHisTurn ? SHOW_HIS_TURN_CHAR : "";
    }
    private void updatePendingMessage() {
        if (this.actionsForThisRound.size() < this.monsters.size()) {
            this.pendingMessage = DECIDE_MONSTER_ACTION
                    .formatted(this.monsters.get(this.actionsForThisRound.size()).monster().getName());
        } else {
            this.pendingMessage = "";
        }
    }
    /**
     *  Sets next Boolean value of the randomizer queue to be used in the second phase of competition for the debug mode.
     * @param nextRandomDouble Boolean value to set
     * @throws InvalidArgumentException if randomizer does not accept the given info
     * @see Randomizer
     */
    public void setRandomizerDebugData(boolean nextRandomDouble) throws InvalidArgumentException {
        this.randomizer.setNextValue(nextRandomDouble);
    }
    /**
     *  Sets next Integer value of the randomizer queue to be used in the second phase of competition for the debug mode.
     * @param nextRandomDouble Integer value to set
     * @throws InvalidArgumentException if randomizer does not accept the given info
     * @see Randomizer
     */
    public void setRandomizerDebugData(int nextRandomDouble) throws InvalidArgumentException {
        this.randomizer.setNextValue(nextRandomDouble);
    }
    /**
     *  Sets next Double value of the randomizer queue to be used in the second phase of competition for the debug mode.
     * @param nextRandomDouble Double value to set
     * @throws InvalidArgumentException if randomizer does not accept the given info
     * @see Randomizer
     */
    public void setRandomizerDebugData(double nextRandomDouble) throws InvalidArgumentException {
        this.randomizer.setNextValue(nextRandomDouble);
    }
    /**
     * Gets the monster whose turn it currently is.
     * @return a copy of the monster on turn
     */
    public Monster getMonsterOnTurn() {
        return this.monsters.get(this.actionsForThisRound.size()).monster().copy();
    }
    /**
     * Starts the competition.
     * @return a Result object containing the outcome of the start attempt
     */
    public Result start() {
        handlePhaseZero();
        handlePhaseOneAndTwo();
        return Result.success(COMPETITION_STARTED_MESSAGE.formatted(this.monsters.size()));
    }
    private Result handlePhaseZero() {
        for (CompetitiveMonster monster : this.monsterMap.values()) {
            if (monster.monster().isDead() && this.monsters.contains(monster)) {
                this.monsters.remove(monster);
                this.losers.add(monster);
            }
        }
        if (this.monsters.size() < MIN_MONSTERS_TO_RUN_COMPETITION) {
            this.isDecided = true;
            return Result.success(this.monsters.isEmpty()
                    ? NO_MONSTER_LEFT_ANNOUNCEMENT
                    : ONE_MONSTER_LEFT_ANNOUNCEMENT.formatted(this.monsters.get(THE_ONLY_MONSTER_ALIVE_INDEX).monster().getName()));
        }
        this.turnIndex = CYCLE_FIRST_INDEX;
        this.actionsForThisRound.clear();
        this.dataForActions.clear();
        sortAllMonstersBy(false);
        updatePendingMessage();
        return Result.success();
    }
    private void sortAllMonstersBy(boolean sortBySPD) {
        if (sortBySPD) {
            this.monsters.sort(CompetitiveMonster.getComparatorForSPDValues());
            this.losers.sort(CompetitiveMonster.getComparatorForSPDValues());
        } else {
            this.monsters.sort(CompetitiveMonster.getComparatorForMonsterNumber());
            this.losers.sort(CompetitiveMonster.getComparatorForMonsterNumber());
        }
    }
    /**
     *  Starts handling the second phase if there is sufficient info from the first phase, handles the first otherwise.
     * @return result containing info about this iteration of handling the second two phases of the competition.
     */
    public Result handlePhaseOneAndTwo() {
        if (this.actionsForThisRound.size() < this.monsters.size()) {
            updatePendingMessage();
            return Result.success();
        }
        sortAllMonstersBy(true);
        StringJoiner resultOfActionsInRound = new StringJoiner(System.lineSeparator());
        while (this.turnIndex < this.actionsForThisRound.size()) {
            CompetitiveMonster monsterOnTurn = this.monsters.get(turnIndex);
            if (!monsterOnTurn.monster().isDead()) {
                if (randomizer.isProcessFirstExecution()) {
                    resultOfActionsInRound.add(ANNOUNCEMENT_WHOSE_TURN.formatted(monsterOnTurn.monster().getName()));
                }
                Result endMonsterConditionResult = monsterOnTurn.monster().tryEndStatCondition(this.randomizer);
                if (endMonsterConditionResult.getType() == ResultType.NEEDS_DEBUG) {
                    this.debugInfo = new DebugCompetitionInfo(true, endMonsterConditionResult.getTypeToDebug());
                    this.pendingMessage = randomizer.getPendingMessage().formatted(endMonsterConditionResult.getDebugMessageLabel());
                    return Result.success(resultOfActionsInRound.toString());
                }
                if (randomizer.isProcessFirstExecution()) {
                    handleEffectResult(resultOfActionsInRound, endMonsterConditionResult);
                }
                if (this.actionsForThisRound.get(monsterOnTurn) == null) {
                    resultOfActionsInRound.add(ANNOUNCEMENT_ACTION_IS_NULL.formatted(monsterOnTurn.monster().getName()));
                } else {
                    Result effectResult = this.actionsForThisRound.get(monsterOnTurn)
                            .executeAction(monsterOnTurn.monster(), getTargetMonster(monsterOnTurn.id()), this.randomizer);
                    if (effectResult.getType() == ResultType.NEEDS_DEBUG) {
                        this.debugInfo = new DebugCompetitionInfo(true, effectResult.getTypeToDebug());
                        this.pendingMessage = randomizer.getPendingMessage().formatted(effectResult.getDebugMessageLabel());
                        if (!effectResult.getMessage().isBlank()) { //method handleEffectResult does not use trim() !
                            resultOfActionsInRound.add(effectResult.getMessage().trim());
                        }
                        return Result.success(resultOfActionsInRound.toString());
                    } else if (randomizer.isProcessFirstExecution()) {
                        handleEffectResult(resultOfActionsInRound, effectResult);
                    }
                }
                if (monsterOnTurn.monster().getMonsterStatus() == StatusCondition.BURN
                        && randomizer.isProcessFirstExecution()) {
                    resultOfActionsInRound.add(BurnEffects.executeEffect(monsterOnTurn.monster()).getMessage());
                }
            }
            this.randomizer.resetMove();
            turnIndex++;
        }
        this.debugInfo = new DebugCompetitionInfo(false, null);
        handleEffectResult(resultOfActionsInRound, handlePostRound());
        handleEffectResult(resultOfActionsInRound, handlePhaseZero());
        return Result.success(resultOfActionsInRound.toString());
    }
    private Result handlePostRound() {
        StringJoiner resultEndRound = new StringJoiner(System.lineSeparator());
        for (CompetitiveMonster monster : this.monsters) {
            Result nextRound = monster.monster().nextRound();
            if (!nextRound.getMessage().isBlank()) {
                resultEndRound.add(nextRound.getMessage());
            }
        }
        return Result.success(resultEndRound.toString());
    }
    private Monster getTargetMonster(int index) {
        for (ActionExecData data : dataForActions) {
            if (data.user() != null && data.user().id() == index) {
                return data.target().monster();
            }
        }
        return null;
    }
    /**
     * Determines if an action requires a target.
     * @param actionName the name of the action to check
     * @return true if there are enough monsters to continue the competition, action exists and requires a target, false otherwise
     */
    public boolean actionNeedsTarget(String actionName) {
        return this.monsters.size() > MIN_MONSTERS_TO_RUN_COMPETITION
                && this.monsters.get(this.actionsForThisRound.size()).monster().getAction(actionName) != null
                && this.monsters.get(this.actionsForThisRound.size()).monster().getAction(actionName).requiresTarget();
    }
    /**
     * Sets the next action to be a pass (no action).
     * @return a Result object representing the outcome of the pass action
     */
    public Result setNextPassAction() {
        this.actionsForThisRound.put(this.monsters.get(this.actionsForThisRound.size()), null);
        this.dataForActions.add(new ActionExecData(null, null));
        updatePendingMessage();
        return handlePhaseOneAndTwo();
    }
    /**
     * Sets the next action for the current monster.
     * @param actionName the name of the action to be performed
     * @param targetName the name of the target monster, if applicable
     * @return a Result object containing the outcome of setting the action
     */
    public Result setNextAction(String actionName, String targetName) {
        int currentMonsterIndex = this.actionsForThisRound.size();
        if (this.monsters.get(currentMonsterIndex).monster().getAction(actionName) != null) {
            Action actionToUse = this.monsters.get(currentMonsterIndex).monster().getAction(actionName).copy();
            CompetitiveMonster targetMonster = !actionToUse.requiresTarget() ? this.monsters.get(currentMonsterIndex)
                    : targetName.isBlank() ? getAnotherMonster() : this.monsterMap.get(targetName);
            if (targetMonster == null) {
                return Result.failed(HAS_NO_MONSTER_NAME_MESSAGE.formatted(targetName));
            }
            this.dataForActions.add(new ActionExecData(this.monsters.get(currentMonsterIndex), targetMonster));
            this.actionsForThisRound.put(this.monsters.get(currentMonsterIndex), actionToUse);
            updatePendingMessage();
            return handlePhaseOneAndTwo();
        }
        return Result.failed(HAS_NO_ACTION_NAME_MESSAGE
                .formatted(this.monsters.get(currentMonsterIndex).monster().getName(), actionName));
    }
    private CompetitiveMonster getAnotherMonster() {
        if (this.monsters.size() != MIN_MONSTERS_TO_RUN_COMPETITION) {
            throw new IllegalArgumentException(NO_TARGET_FOR_BIG_COMPETITION_MESSAGE);
        }
        return this.monsters.get((this.actionsForThisRound.size() + OFFSET_TO_CHOOSE_OTHER_MONSTER) % MIN_MONSTERS_TO_RUN_COMPETITION);
    }
    private static void handleEffectResult(StringJoiner joiner, Result result) {
        if (!result.getMessage().isBlank()) {
            joiner.add(result.getMessage());
        }
    }
}