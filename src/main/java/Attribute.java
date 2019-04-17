
/**
 * Generic attribute
 */
public abstract class Attribute {

	/**
	 * The name of the attribute
	 */
	private String name;

	/**
	 * An unique identifier
	 */
	private int index;

	/**
	 * Construct a new attribute
	 * @param name The name of the attribute
	 * @param index An unique identifier
	 */
	public Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * Get the name
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the index
	 * @return The unique identifier
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Convert the attribute to a String
	 * @return The textual rappresentation of the attribute
	 */
	public String toString() {
		return getName();
	}
}
