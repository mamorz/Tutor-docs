package edu.kit.kastel.logistics;

import edu.kit.kastel.objects.actions.Action;
import edu.kit.kastel.objects.monsters.Monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Represents the setup for a game, managing monsters and actions.
 * @author upgcv
 */
public class GameSetup {
    private final Map<String, Action> mapOfActions;
    private final List<String> actionNames;
    private final List<String> monsterNames;
    private final Map<String, Monster> mapOfMonsters;

    /**
     * Constructs a GameSetup instance.
     */
    public GameSetup() {
        this.actionNames = new ArrayList<>();
        this.mapOfMonsters = new HashMap<>();
        this.mapOfActions = new HashMap<>();
        this.monsterNames = new ArrayList<>();
    }

    private GameSetup(GameSetup newGame) {
        this.actionNames = new ArrayList<>(newGame.actionNames);
        this.mapOfActions = new HashMap<>();
        for (String actionName : this.actionNames) {
            this.mapOfActions.put(actionName, newGame.mapOfActions.get(actionName).copy());
        }
        this.monsterNames = new ArrayList<>(newGame.monsterNames);
        this.mapOfMonsters = new HashMap<>();
        for (String monsterName : this.monsterNames) {
            this.mapOfMonsters.put(monsterName, newGame.mapOfMonsters.get(monsterName).copy());
        }
    }
    /**
     * Creates a copy of an existing GameSetup instance.
     * @return a new GameSetup instance with the same data.
     */
    public GameSetup copy() {
        return new GameSetup(this);
    }
    /**
     * Returns a string representation of all monsters in the setup.
     * @return a formatted string listing all monsters.
     */
    public String showMonsters() {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        for (String monsterName : monsterNames) {
            result.add(mapOfMonsters.get(monsterName).showSelf());
        }
        return result.toString();
    }
    /**
     * Adds a new action to the setup if it does not already exist.
     * @param action the action to add.
     * @return true if the action was successfully added, false if it already exists.
     */
    public boolean addNewAction(Action action) {
        if (actionNames.contains(action.getName())) {
            return false;
        }
        mapOfActions.put(action.getName(), action.copy());
        actionNames.add(action.getName());
        return true;
    }
    /**
     * Gets the number of actions in the setup.
     * @return the number of actions.
     */
    public int getActionsNumber() {
        return this.mapOfActions.size();
    }
    /**
     * Gets the number of monsters in the setup.
     * @return the number of monsters.
     */
    public int getMonstersNumber() {
        return this.mapOfMonsters.size();
    }
    /**
     * Retrieves a copy of an action by its name.
     * @param actionName the name of the action to retrieve.
     * @return a copy of the action, or null if it does not exist.
     */
    public Action getAction(String actionName) {
        Action actionToGet = this.mapOfActions.get(actionName);
        return actionToGet == null ? null : actionToGet.copy();
    }
    /**
     * Retrieves a copy of a monster by its name.
     * @param monsterName the name of the monster to retrieve.
     * @return a copy of the monster, or null if it does not exist.
     */
    public Monster getMonster(String monsterName) {
        Monster monsterToGet = this.mapOfMonsters.get(monsterName);
        return monsterToGet == null ? null : monsterToGet.copy();
    }
    /**
     * Adds a new monster to the setup if it does not already exist.
     * @param monster the monster to add.
     * @return true if the monster was successfully added, false if it already exists.
     */
    public boolean addNewMonster(Monster monster) {
        if (mapOfMonsters.containsKey(monster.getName())) {
            return false;
        }
        mapOfMonsters.put(monster.getName(), monster.copy());
        monsterNames.add(monster.getName());
        return true;
    }
}
