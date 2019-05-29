
package data;

import java.util.Map;
import java.util.HashMap;

/**
 * Attribute characterized by a discrete value.
 */
class DiscreteAttribute extends Attribute {

	/**
	 * The map of possible values that the attribute can be.
	 */
	private Map<String, Integer> values = new HashMap<String, Integer>();

	/**
	 * Instantiate a new discrete attribute.
	 * @param name The name of the attribute
	 * @param index An unique identifier
	 * @param values The possible values
	 */
	DiscreteAttribute(String name, int index, String[] values) {
		super(name, index);

		for (int i = 0; i < values.length; i++) {
			this.values.put(values[i], i);
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
	 * Get the id of the value.
	 * @param val The value
	 * @return The id of val
	 */
	Integer getValueID(String val) {
		return values.get(val);
	}
}
