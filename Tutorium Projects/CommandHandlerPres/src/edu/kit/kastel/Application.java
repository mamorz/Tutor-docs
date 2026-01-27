package edu.kit.kastel;


import edu.kit.kastel.commands.CommandHandler;

/**
 * The class offering the entry point for the application.
 * @author upgcv
 */
public final class Application {
    private static final String INVALID_FIRST_INPUT_MESSAGE = "Error, your input is not acceptable!";
    private static final int NO_ARGS_ENTERED = 0;
    private static final int FIRST_ARRAY_ELEMENT = 0;
    private static final int SUBTRACT_FOR_CORRECT_BORDER = 1;
    private static final int ENTERED_OPTIONAL_ARGUMENT = 2;

    private Application() {
        //Utility class
    }
    /**
     * The entry point for the application. It is expected to receive a path to source configuration file. An input of
     * a debug mode or a seed for the randomizer factor are optimal.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length > ENTERED_OPTIONAL_ARGUMENT || args.length == NO_ARGS_ENTERED) {
            System.err.println(INVALID_FIRST_INPUT_MESSAGE);
            return;
        }
        String commandHandlerInputData = args.length == ENTERED_OPTIONAL_ARGUMENT ? args[args.length - SUBTRACT_FOR_CORRECT_BORDER] : "";
        CommandHandler commandHandler = new CommandHandler(commandHandlerInputData, System.in, System.out, System.err);
        commandHandler.handleFirstConfig(args[FIRST_ARRAY_ELEMENT]);
        commandHandler.handleUserInput();

    }
}
