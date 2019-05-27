
package stats;

/**
 * 3D space point class.
 */
public class Point3D implements Comparable<Point3D> {

	/**
	 * The X axis coordinate.
	 */
	private double x;

	/**
	 * The Y axis coordinate.
	 */
	private double y;

	/**
	 * The Z axis coordinate.
	 */
	private double z;

	/**
	 * Default constructor.
	 */
	public Point3D() {
		// nothing
	}

	/**
	 * Construct a point.
	 * @param x The X axis coordinate
	 * @param y The Y axis coordiate
	 * @param z The Z axis coordinate
	 */
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	 * Get The Z coordinate.
	 * @return The Z coordinate
	 */
	public double getZ() {
		return z;
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
	 * Set the Z coordinate.
	 * @param z The Z coordinate
	 */
	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * Convert the point to a string rappresentation.
	 * @return The string rappresentation
	 */
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	/**
	 * Compare to another point.
	 * @param p The other point
	 * @return 1 if this point X coordinate is greater than p's X coordinate,
	 *        -1 if this point X coordinte is less than p's X coordinate
	 *         0 otherwise
	 */
	public int compareTo(Point3D p) {
		if (x > p.x) {
			return 1;
		} else if (x < p.x) {
			return -1;
		}

		return 0;
	}
}

