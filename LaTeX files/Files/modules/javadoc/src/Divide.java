class Divide {

    /**
     * Divides two integers, returning the result of the
     * integer division.
     *
     * The result is an integer, so any fractional part is
     * discarded: {@code divide(20, 3)} is 6,
     * {@code divide(2, 4)} is 0.
     *
     * @param numerator the numerator, i.e. the number to divide
     * @param denominator i.e. the number to divide by.
     *        Must not be zero
     * @return the result of the integer division
     * @throws ArithmeticException if the denominator is zero
     */
    public static int divide(int numerator, int denominator) {
        return numerator / denominator;
    }
}
