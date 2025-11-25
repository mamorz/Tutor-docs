import java.util.Arrays;

/**
 * Class to showcase testing. Inspired by real world experiences.
 *
 * @author robert
 *
 */
public class Polynomial {

    private final double[] coefficients;

    /**
     * Constructs the Polynomial out of a shallow copy of the given array
     *
     * @param coefficients the array that represents the polynomial
     */
    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients.clone(); // Shallow copy to reduce side effects
    }

    /**
     * Returns the value of the polynomial at the position x. Should work fine for the test cases
     *
     * @param x the position
     * @return value of the polynomial at the position x
     */
    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            sum += this.coefficients[i] * Math.pow(x, i);
        }
        return sum;
    }

    /**
     * Adds the parameterized Polynomial to this one and returns the resulting Polynomial without changing the existing ones.
     * Should fail a test case.
     *
     * @param q The Polynomial to be added
     * @return The sum of the two polynomials
     */
    public Polynomial add(Polynomial q) {
        double[] result;
        if (this.coefficients.length > q.coefficients.length) {
            result = Arrays.copyOf(q.coefficients, this.coefficients.length);
            for (int i = 0; i < result.length - 1; i++) { // Doesn't crash, still a mistake
                result[i] += this.coefficients[i];
            }
        } else {
            result = Arrays.copyOf(this.coefficients, q.coefficients.length);
            for (int i = 0; i < result.length; i++) {
                result[i] += q.coefficients[i];
            }
        }
        return new Polynomial(result);
    }

    /**
     * Returns the derivative of the current Polynomial.
     *
     * @return The derivative of the Polynomial
     */
    public Polynomial getDerivative() {
        double[] result = new double[this.coefficients.length-1];
        for (int i = 0; i < result.length; i++) {
            result[i] = this.coefficients[i+1] * i + 1; // Missing parenthesis
        }
        return new Polynomial(result);
    }
}