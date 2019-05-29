
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
	DiscreteItem(Attribute attribute, String value) {
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
	 * Get the numeric rappresentation of the discrete item.
	 * @return The id of the value of the discrete item
	 */
	double toNumeric() {
		DiscreteAttribute attr = (DiscreteAttribute) getAttribute();

		return (double) attr.getValueID((String) getValue());
	}
}
