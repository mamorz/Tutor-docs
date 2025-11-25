import static org.junit.Assert.*; // Wildcard… yeah, i know.
import org.junit.Test;

/**
 * "Private" tests for the class {@link Polynomial}.
 *
 * @author Ralf Huell
 * @version 1.0 / December 06, 2019
 */
public class PolynomialTest {

    /**
     * First private test.
     */
    @Test
    public void private1() {
        Polynomial p = new Polynomial(new double[]{1, 3, 3, 7});
        Polynomial q = new Polynomial(new double[]{3, -1, -3, -7});
        Polynomial r = new Polynomial(new double[]{1, 3, 3, 7});

        assertEquals(75.0, p.evaluate(2), 0.1); // OK: evaluate is correct
        assertEquals(-67.0, q.evaluate(2), 0.1); // OK: evaluate works with negative numbers
        assertEquals(10.0, p.add(q).evaluate(3), 0.1); // {4, 2}, OK: add is correct
        assertEquals(75.0, p.evaluate(2), 0.1); // Error: p is overwritten by add operation
        assertEquals(-67.0, q.evaluate(2), 0.1); // Error: q is overwritten by add operation
        assertEquals(99.0, p.getDerivative().evaluate(2), 0.1);
            // {3.0, 6.0, 21.0}, OK: derivative is correct
        assertEquals(-97.0, q.getDerivative().evaluate(2), 0.1); // derivative of negative values is correct
        assertEquals(99.0, r.getDerivative().evaluate(2), 0.1);
            // if p is overwritten by add, this should show that derivative is calculated correctly still
    }

    @Test
    public void testEvaluate() {
    	Polynomial p = new Polynomial(new double[]{1, 1, 1});
    	assertEquals(7.0, p.evaluate(2), 0.1);
    }

    /**
     * Second private test.
     */
    @Test
    public void private2() {
        Polynomial p = new Polynomial(new double[]{7, 0, 0, 2, 0, 5});
        Polynomial q = new Polynomial(new double[]{1});
        Polynomial r = new Polynomial(new double[]{0, 0, 0, 1});
        Polynomial s = new Polynomial(new double[]{0, 0, 0, 1, 5});

        assertEquals(5255.0, p.evaluate(4), 0.1); // OK: evaluate is correct
        assertEquals(1.0, q.evaluate(2), 0.1); // OK: evaluate is correct
        assertEquals(8.0, r.evaluate(2), 0.1); // OK: evaluate is correct

        assertEquals(1304.0, p.add(q).add(r).evaluate(3), 0.1);
                // {8, 0, 0, 3, 0, 5}, OK: double add is correct
        assertEquals(5255.0, p.evaluate(4), 0.1); // Error: p is modified in the process
        assertEquals(1.0, q.evaluate(2), 0.1); // Error: q is modified in the process
        assertEquals(8.0, r.evaluate(2), 0.1); // Error: r is modified in the process

        assertEquals(172.0, s.getDerivative().evaluate(2), 0.1); // derivate works
    }
    
    @Test
    public void addGreater() {
        Polynomial p = new Polynomial(new double[]{7, 0, 0, 2, 0, 5});
        Polynomial q = new Polynomial(new double[]{1});
        Polynomial s = q.add(p);
        assertEquals(1.0, s.evaluate(-1), 0.1);
        assertEquals(-168.0, s.evaluate(-2), 0.1);
        
    }
    
    @Test
    public void addSmaller() {
        Polynomial p = new Polynomial(new double[]{7, 0, 0, 2, 0, 5});
        Polynomial q = new Polynomial(new double[]{1});
        Polynomial s = q.add(p);
        assertEquals(1.0, s.evaluate(-1), 0.1);
        assertEquals(-168.0, s.evaluate(-2), 0.1);
    }
    
    @Test
    public void derivative() {
        Polynomial s = new Polynomial(new double[]{0, 0, 0, 1, 5});
        assertEquals(172.0, s.getDerivative().evaluate(2), 0.1); // derivate works

    }

}
