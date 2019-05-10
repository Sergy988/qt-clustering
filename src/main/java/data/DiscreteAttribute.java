
package data;

import java.util.TreeSet;
import java.util.Iterator;

/**
 * Attribute characterized by a discrete value.
 */
class DiscreteAttribute extends Attribute {

	/**
	 * The set of possible values that the attribute can be.
	 */
	private TreeSet<String> values;

	/**
	 * Instantiate a new discrete attribute.
	 * @param name The name of the attribute
	 * @param index An unique identifier
	 * @param values The possible values
	 */
	DiscreteAttribute(String name, int index, String[] values) {
		super(name, index);

		for (String val : values) {
			this.values.add(val);
		}
	}

	/**
	 * Get the number of distinct values.
	 * @return The size of values
	 */
	int getNumberOfDistinctValues() {
		return values.size();
	}

	/**
	 * Get the iterator to the possible values.
	 * @return An iterator to the possible values.
	 */
	Iterator<String> iterator() {
		return values.iterator();
	}
}
