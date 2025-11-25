/**
 * A point
 * @author nb
 * @version 1.0
 */
public class Point {
	
    private double x;
    private double y;
    
    /**
     * Constructor to create a point
     * @param x coordinate
     * @param y coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Returns x coord
     * @return x coord
     */
    public double getX() {
        return this.x;
    }
    
    /**
     * Returns y coord
     * @return y coord
     */
    public double getY() {
        return this.y;
    }
    
    /**
     * String representation of point
     */
    public String toString() {
    	return this.x + " / " + this.y;
    }
    
    /**
     * Compares a given point to this point
     * @param p point to compare to
     * @return true if equal, false if not
     */
    public boolean equals(Point p) {
        return this.x == p.x &&
        this.y == p.y;
    }
}