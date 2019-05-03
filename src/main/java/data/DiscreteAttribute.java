
package data;

/**
 * Attribute characterized by a discrete value.
 */
class DiscreteAttribute extends Attribute {

	/**
	 * The set of possible values that the attribute can be.
	 */
	private String[] values;

	/**
	 * Instantiate a new discrete attribute.
	 * @param name The name of the attribute
	 * @param index An unique identifier
	 * @param values The possible values
	 */
	DiscreteAttribute(String name, int index, String[] values) {
		super(name, index);
		this.values = values;
	}

	/**
	 * Get the number of distinct values.
	 * @return The size of values
	 */
	int getNumberOfDistinctValues() {
		return values.length;
	}

	/**
	 * Get a value from the set of values.
	 * @param i The index of the value
	 * @return The value at i
	 */
	String getValue(int i) {
		return values[i];
	}
}
