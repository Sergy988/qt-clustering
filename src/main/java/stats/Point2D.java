
package stats;

/**
 * 2D space point class.
 */
public class Point2D {

	/**
	 * The X axis coordinate.
	 */
	private double x;

	/**
	 * The Y axis coordinate.
	 */
	private double y;

	/**
	 * Default constructor.
	 */
	public Point2D() {
		// nothing
	}

	/**
	 * Construct a point.
	 * @param x The X axis coordinate
	 * @param y The Y axis coordiante
	 */
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the X coordinate.
	 * @return The X coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the Y coordinate.
	 * @return The Y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Set the X coordinate.
	 * @param x The X coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Set the Y coordinate.
	 * @param y The Y coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Convert the point to a string rappresentation.
	 * @return The string rappresentation
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}

