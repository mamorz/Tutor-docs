package edu.kit.informatik;

import java.util.Scanner;

/**
 * A small parser for a toy language supporting addition, subtraction and parentheses.
 */
public class MyParser {

    private StringReader stringReader;

    /**
     * Creates a new parser with the given string as input.
     */
    public MyParser(StringReader stringReader) {
        this.stringReader = stringReader;
    }

    /**
     * Parses the input to a node.
     *
     * May only be called once!
     */
    public Node parse() throws ParseException {
        return null;
    }

    /**
     * A small interactive demo of the parser to try it out!.
     *
     * @param args ignored.
     */
    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("> ");
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("quit")) {
                break;
            }
            try {
                Node result = new MyParser(new StringReader(line)).parse();
                System.out.println(result.evaluate());
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
            System.out.print("> ");
        }
        System.out.println("\nGooodbye!");
    }

    /**
     * A node in the syntax tree of the grammar.
     *
     * Can be evaluated to a single double.
     */
    public interface Node {

        /**
         * Evaluates this node, yielding its value.
         *
         * @return the value of the node
         */
        double evaluate();
    }

    /**
     * A node performing an Addition.
     */
    public static class Addition implements Node {

        private Node left;
        private Node right;

        public Addition(Node left, Node right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public double evaluate() {
            return left.evaluate() + right.evaluate();
        }
    }

    /**
     * A node performing a subtraction.
     */
    public static class Subtraction implements Node {

        private Node left;
        private Node right;

        public Subtraction(Node left, Node right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public double evaluate() {
            return left.evaluate() - right.evaluate();
        }
    }

    /**
     * A literal node just storing a number.
     */
    public static class Literal implements Node {

        private double value;

        public Literal(double value) {
            this.value = value;
        }

        @Override
        public double evaluate() {
            return value;
        }
    }
}
