
package data;

/**
 * The discrete realization of Item.
 */
class DiscreteItem extends Item {
	/**
	 * Instantiate a discrete item.
	 * @param attribute The attribute
	 * @param value The discrete value
	 */
	DiscreteItem(Attribute attribute, Object value) {
		super(attribute, value);
	}

	/**
	 * Get the distance from a discrete item to a generic object.
	 * @param a The object from which calculate the distance
	 * @return The actual distance
	 */
	double distance(Object a) {
		return getValue().equals(a) ? 0.0 : 1.0;
	}
}
