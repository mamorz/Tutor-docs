import java.util.Optional;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleCommandSystem {

    /**
     * A simple calculator mock.
     */
    static class Calculator {

        /**
         * Adds two numbers. Ignores overflows.
         *
         * @param first the first number
         * @param second the second number
         * @return the result of the summation
         */
        public int add(int first, int second) {
            return first + second;
        }
    }

    /**
     * A command the user can execute.
     */
    public enum Command {
        /**
         * Allows adding two numbers.
         */
        ADD(Command.CALCULATOR_NUMBER + " \\+ " + Command.CALCULATOR_NUMBER) {
            @Override
            protected void evaluate(MatchResult matchResult, Calculator calculator) {
                int sum = calculator.add(
                        Integer.parseInt(matchResult.group(1)),
                        Integer.parseInt(matchResult.group(2))
                        );
                this.output = "The result is: " + sum;
            }
        },
        /**
         * Quits the program.
         */
        QUIT("quit") {
            @Override
            protected void evaluate(MatchResult matchResult, Calculator calculator) {
                this.exit = true;
            }
        };


        private static final String CALCULATOR_NUMBER = "([1-9]\\d*)";

        private Pattern pattern;

        protected boolean exit;
        protected String output;

        /**
         * Creates a new command with the given pattern as activator.
         *
         * @param pattern the pattern to expect
         */
        Command(String pattern) {
            this.pattern = Pattern.compile(pattern);
        }

        /**
         * Returns true if the program should exit.
         *
         * @return true if the program should exit
         */
        public boolean shouldExit() {
            return exit;
        }

        /**
         * Returns the output of the last command.
         *
         * @return the output of the last command if any
         */
        public Optional<String> getOutput() {
            return Optional.ofNullable(output);
        }

        /**
         * Evaluates the given command, modifying and using the calculator where needed.
         *
         * @param matchResult the regex match result
         * @param calculator the calculator instance
         */
        protected abstract void evaluate(MatchResult matchResult, Calculator calculator);

        /**
         * Searches for an executes a matching command.
         *
         * @param input the user input
         * @param calculator the calculator instance to use
         * @return the executed command
         * @throws RuntimeException if no matching command could be found
         */
        public static Command executeMatching(String input, Calculator calculator) {
            for (Command command : values()) {
                // Clear the output ?
                command.output = null;

                Matcher matcher = command.pattern.matcher(input);
                if (matcher.matches()) {
                    command.evaluate(matcher, calculator);
                    return command;
                }
            }
            throw new RuntimeException("Your custom exception");
        }
    }

    public static void main(String... args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            try {
                String input = scanner.nextLine();
                Command command = Command.executeMatching(input, calculator);

                command.getOutput().ifPresent(System.out::println);

                // Exit the program
                if (command.shouldExit()) {
                    return;
                }
            } catch (RuntimeException e) {
                System.err.println("Wrong input!");
            }
        }
    }
}
