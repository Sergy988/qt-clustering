
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
	 * @param o The object from which calculate the distance
	 * @return The actual distance
	 */
	double distance(Object o) {
		return getValue().equals(o) ? 0.0 : 1.0;
	}

	/**
	 * Get the difference from a discrete item to a generic object.
	 * @param o A generic object
	 * @return The actual difference
	 */
	double difference(Object o) {
		return distance(o);
	}
}
