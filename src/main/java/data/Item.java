
package data;

import java.io.Serializable;

/**
 * Generic item.
 */
abstract class Item implements Serializable {
	/**
	 * The attribute.
	 */
	private Attribute attribute;

	/**
	 * The actual value.
	 */
	private Object value;

	/**
	 * Construct an Item.
	 * @param attribute The attribute
	 * @param value The generic value
	 */
	Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}

	/**
	 * Get the attribute.
	 * @return The attribute
	 */
	Attribute getAttribute() {
		return attribute;
	}

	/**
	 * Get the value.
	 * @return The value
	 */
	Object getValue() {
		return value;
	}

	/**
	 * Convert the item to a string.
	 * @return Get the textual rappresentation of the item
	 */
	public String toString() {
		return value.toString();
	}

	/**
	 * The abstract function to calculate the distance from a generic object.
	 * @param o A generic object
	 * @return The actual distance
	 */
	abstract double distance(Object o);
}
