package edu.kit.kastel.commands;

import edu.kit.kastel.logistics.GameSetup;
import edu.kit.kastel.logistics.initializer.EntityInitializers;
import edu.kit.kastel.objects.Element;
import edu.kit.kastel.objects.actions.Action;
import edu.kit.kastel.objects.actions.effects.Effect;
import edu.kit.kastel.objects.actions.effects.RepeatEffect;
import edu.kit.kastel.objects.monsters.Monster;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Class representing a load command, which loads new config for the game.
 * @author upgcv
 */
public class LoadCommand implements Command<CommandHandler> {
    private static final String IMPOSSIBLE_IN_DEBUG_PROCESS_MESSAGE = "Error, you cannot use this command in debug mode!";
    private static final String NUMBER_REGEX = "[1-9][0-9]*";
    private static final String INPUT_LINE_SEPARATOR = " ";
    private static final String NOT_SPECIFIED_DURATION_TYPE = "random";
    private static final String CONFIG_SUCCESSFUL_LOADED = System.lineSeparator() + "Loaded %d actions, %d monsters.";
    //Regex checking if it is a valid action first line input
    private static final String ACTION_FIRST_LINE_REGEX = "\\s*action [A-Za-z]+ (NORMAL|WATER|FIRE|EARTH)\\s*";
    //Regex checking if it is a valid monster line input
    private static final String MONSTER_LINE_REGEX =
            String.format("\\s*monster [A-Za-z]+ (WATER|FIRE|EARTH|NORMAL) %s %s %s %s( [A-Za-z]+){1,4}\\s*",
                    NUMBER_REGEX, NUMBER_REGEX, NUMBER_REGEX, NUMBER_REGEX);
    private static final String PERCENTAGE_REGEX = "(100|[0-9]|[1-9][0-9])";

    //All Regexes below are checking if it is a correct effect input line, named after the effects themselves
    private static final String DAMAGE_EFFECT_LINE_REGEX =
            String.format("\\s*damage (user|target) (base \\d+|abs \\d+|rel %s) %s\\s*",
                    PERCENTAGE_REGEX, PERCENTAGE_REGEX);
    private static final String INFLICT_STAT_CONDITION_EFFECT_LINE_REGEX =
            String.format("\\s*inflictStatusCondition (user|target) (WET|BURN|SLEEP|QUICKSAND) %s\\s*", PERCENTAGE_REGEX);
    private static final String INFLICT_STAT_CHANGE_EFFECT_LINE_REGEX =
            String.format("\\s*inflictStatChange (user|target) (ATK|DEF|SPD|PRC|AGL) (-|\\+|)\\d+ %s\\s*", PERCENTAGE_REGEX);
    private static final String PROTECT_STAT_EFFECT_LINE_REGEX =
            String.format("\\s*protectStat (health|stats) (%s|random %s %s) %s\\s*",
                    NUMBER_REGEX, NUMBER_REGEX, NUMBER_REGEX, PERCENTAGE_REGEX);
    private static final String HEAL_EFFECT_LINE_REGEX =
            String.format("\\s*heal (user|target) (base %s|abs %s|rel %s) %s\\s*",
                    NUMBER_REGEX, NUMBER_REGEX, PERCENTAGE_REGEX, PERCENTAGE_REGEX);
    private static final String REPEAT_EFFECT_LINE_REGEX =
            String.format("\\s*repeat (%s|random %s %s)\\s*", NUMBER_REGEX, NUMBER_REGEX, NUMBER_REGEX);
    private static final String CONTINUE_EFFECT_LINE_REGEX = String.format("\\s*continue %s\\s*", PERCENTAGE_REGEX);

    //Regex checking if it is a non-repeat effect entered in input line
    private static final String POSSIBLE_NOT_REPEAT_EFFECT_LINE_REGEX =
            String.format("(%s|%s|%s|%s|%s|%s)", DAMAGE_EFFECT_LINE_REGEX, INFLICT_STAT_CONDITION_EFFECT_LINE_REGEX,
                    INFLICT_STAT_CHANGE_EFFECT_LINE_REGEX, PROTECT_STAT_EFFECT_LINE_REGEX, HEAL_EFFECT_LINE_REGEX,
                    CONTINUE_EFFECT_LINE_REGEX);
    private static final String CLOSE_REPEAT_EFFECT_REGEX = "\\s*end repeat\\s*";
    private static final String NEXT_ACTION_REGEX = "\\s*end action\\s*";
    private static final String SEPARATOR_REGEX = " ";
    private static final String BASE_DMG_REGEX = "b\\d+";
    private static final String INVALID_SRC_PATH_MESSAGE = "Error, no file found at %s!";
    private static final String FAIL_AT_PARSING = "Error, parsing the file was not successful!";
    private static final String INVALID_CFG_MESSAGE = "Error, invalid config!";
    private static final String NONE_EFFECTS_MESSAGE = "Error, an action must include at least one effect!";
    private static final String DUPLICATE_ACTIONS_MESSAGE = "Error, a config must not include two actions of the same name!";
    private static final String DUPLICATE_MONSTERS_MESSAGE = "Error, a config must not include two monsters of the same name!";
    private static final String INVALID_MONSTER_NAME = "Error, %s is not a valid monster description!";
    private static final String INVALID_EFFECT_MESSAGE = "Error, %s is not a valid description of an effect!";
    private static final String INVALID_REPEAT_MESSAGE = "Error, your repeat effect is not of valid description!";

    private static final int START_COUNT_DMG_EFFECTS = 0;
    private static final int NAME_POSITION = 1;
    private static final int ELEMENT_POSITION = 2;
    private static final int RANDOM_KEYWORD_POSITION = 1;
    private static final int REPEAT_BOTTOM_BORDER_POSITION = 2;
    private static final int REPEAT_UPPER_BORDER_POSITION = 3;
    private static final int REPEAT_DURATION_POSITION = 1;

    private final String srcPath;
    private String invalidCFGMessage = INVALID_CFG_MESSAGE;
    private int countBaseDMGEffects;
    private boolean isErrorOccurred;

    /**
     * Initializes a new load command instance.
     * @param path path to new config
     */
    public LoadCommand(String path) {
        this.srcPath = path;
        this.countBaseDMGEffects = START_COUNT_DMG_EFFECTS;
        this.isErrorOccurred = false;
    }


    @Override
    public Result execute(CommandHandler handle) {
        if (handle.isInDebugProcess()) {
            return Result.failed(IMPOSSIBLE_IN_DEBUG_PROCESS_MESSAGE);
        }
        GameSetup writtenConfig = new GameSetup();
        StringJoiner outputConfig = new StringJoiner(System.lineSeparator());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.srcPath))) {
            String lineToCheck;
            while ((lineToCheck = bufferedReader.readLine()) != null) {
                outputConfig.add((lineToCheck));
                if (lineToCheck.matches(ACTION_FIRST_LINE_REGEX)) {
                    List<Effect> effects = new ArrayList<>();
                    String actionName = lineToCheck.trim().split(SEPARATOR_REGEX)[NAME_POSITION];
                    Element actionElement = Element.valueOf(lineToCheck.trim().split(SEPARATOR_REGEX)[ELEMENT_POSITION]);
                    lineToCheck = bufferedReader.readLine();
                    if (lineToCheck == null) {
                        break;
                    }
                    outputConfig.add(lineToCheck);
                    lineToCheck = processActionEffects(bufferedReader, lineToCheck, actionElement, effects, outputConfig);
                    if (lineToCheck != null && lineToCheck.matches(NEXT_ACTION_REGEX)) {
                        if (effects.isEmpty()) {
                            setNoEffects();
                        } else if (!writtenConfig.addNewAction(new Action(actionName, actionElement, effects))) {
                            setDuplicateActions();
                        }
                    }
                } else if (lineToCheck.matches(MONSTER_LINE_REGEX)) {
                    Monster newMonster = EntityInitializers.initializeMonsterFromInput(lineToCheck, writtenConfig);
                    if (newMonster == null) {
                        setInvalidMonsterError(lineToCheck);
                    } else if (!writtenConfig.addNewMonster(newMonster)) {
                        setDuplicateMonsters();
                    }
                } else {
                    this.isErrorOccurred = !lineToCheck.isBlank() || this.isErrorOccurred;
                }
            }
        } catch (FileNotFoundException e) {
            return Result.failed(INVALID_SRC_PATH_MESSAGE.formatted(this.srcPath));
        } catch (IOException e) {
            return Result.failed(FAIL_AT_PARSING);
        }
        if (!this.isErrorOccurred) {
            handle.setConfig(writtenConfig);
            outputConfig.add(CONFIG_SUCCESSFUL_LOADED
                    .formatted(writtenConfig.getActionsNumber(), writtenConfig.getMonstersNumber()));
            return Result.success(outputConfig.toString());
        }
        outputConfig.add(this.invalidCFGMessage);
        return Result.success(outputConfig.toString());
    }


    private String processActionEffects(BufferedReader bufferedReader, String firstEffectsLine, Element actionElement,
                                         List<Effect> effects, StringJoiner outputConfig) throws IOException {
        String possibleEffect = firstEffectsLine;
        this.countBaseDMGEffects = START_COUNT_DMG_EFFECTS;
        while (possibleEffect.matches(POSSIBLE_NOT_REPEAT_EFFECT_LINE_REGEX)
                || possibleEffect.matches(REPEAT_EFFECT_LINE_REGEX) || possibleEffect.isBlank()) {
            if (possibleEffect.matches(REPEAT_EFFECT_LINE_REGEX)) {
                Effect repeatEffect = handleRepeatEffect(actionElement,
                        possibleEffect, bufferedReader, outputConfig);
                if (repeatEffect == null) {
                    setInvalidRepeatEffect();
                    return null;
                }
                effects.add(repeatEffect);
            } else if (!possibleEffect.isBlank()) {
                Effect newEffect = EntityInitializers.initializeNotRepeatEffect(actionElement,
                        countBaseDMGEffects == START_COUNT_DMG_EFFECTS, possibleEffect);
                if (newEffect == null) {
                    setInvalidEffect(possibleEffect);
                    return null;
                }
                if (newEffect.getDMGValue().matches(BASE_DMG_REGEX)) {
                    this.countBaseDMGEffects++;
                }
                effects.add(newEffect);
            }
            possibleEffect = bufferedReader.readLine();
            if (possibleEffect == null) {
                break;
            }
            outputConfig.add(possibleEffect);
        }
        return possibleEffect;
    }

    private Effect handleRepeatEffect(Element element, String input, BufferedReader bufferedReader, StringJoiner outputConfig)
            throws IOException {
        String newLine = bufferedReader.readLine();
        List<Effect> repeatEffects = new ArrayList<>();
        boolean isErrorOccurred = false;
        while (newLine.matches(POSSIBLE_NOT_REPEAT_EFFECT_LINE_REGEX) || newLine.isBlank()) {
            outputConfig.add(newLine);
            if (newLine.isBlank()) {
                continue;
            }
            Effect newEffect = EntityInitializers
                    .initializeNotRepeatEffect(element, this.countBaseDMGEffects == START_COUNT_DMG_EFFECTS, newLine);
            if (newEffect == null) {
                isErrorOccurred = true;
            }
            if (newEffect != null && newEffect.getDMGValue().matches(BASE_DMG_REGEX)) {
                this.countBaseDMGEffects++;
            }
            repeatEffects.add(newEffect);
            newLine = bufferedReader.readLine();
        }
        outputConfig.add(newLine);
        if (newLine.matches(CLOSE_REPEAT_EFFECT_REGEX)) {
            String[] splitInput = input.trim().split(INPUT_LINE_SEPARATOR);
            if (splitInput[RANDOM_KEYWORD_POSITION].equals(NOT_SPECIFIED_DURATION_TYPE)) {
                int bottomBorder = Integer.parseInt(splitInput[REPEAT_BOTTOM_BORDER_POSITION]);
                int upperBorder = Integer.parseInt(splitInput[REPEAT_UPPER_BORDER_POSITION]);
                if (bottomBorder >= upperBorder || isErrorOccurred) {
                    return null;
                }
                return new RepeatEffect(repeatEffects, bottomBorder, upperBorder);
            } else {
                int duration = Integer.parseInt(splitInput[REPEAT_DURATION_POSITION]);
                if (isErrorOccurred) {
                    return null;
                }
                return new RepeatEffect(repeatEffects, duration);
            }
        }
        return null;
    }

    private void setDuplicateActions() {
        if (this.invalidCFGMessage.isBlank()) {
            this.invalidCFGMessage = DUPLICATE_ACTIONS_MESSAGE;
        }
        this.isErrorOccurred = true;
    }

    private void setInvalidMonsterError(String monsterName) {
        if (this.invalidCFGMessage.isBlank()) {
            this.invalidCFGMessage = INVALID_MONSTER_NAME.formatted(monsterName);
        }
        this.isErrorOccurred = true;
    }
    private void setDuplicateMonsters() {
        if (this.invalidCFGMessage.isBlank()) {
            this.invalidCFGMessage = DUPLICATE_MONSTERS_MESSAGE;
        }
        this.isErrorOccurred = true;
    }
    private void setInvalidRepeatEffect() {
        if (this.invalidCFGMessage.isBlank()) {
            this.invalidCFGMessage = INVALID_REPEAT_MESSAGE;
        }
        this.isErrorOccurred = true;
    }
    private void setInvalidEffect(String name) {
        if (this.invalidCFGMessage.isBlank()) {
            this.invalidCFGMessage = INVALID_EFFECT_MESSAGE.formatted(name);
        }
        this.isErrorOccurred = true;
    }
    private void setNoEffects() {
        if (this.invalidCFGMessage.isBlank()) {
            this.invalidCFGMessage = NONE_EFFECTS_MESSAGE;
        }
        this.isErrorOccurred = true;
    }
}
