
package data;

/**
 * The continuous realization of Item.
 */
class ContinuousItem extends Item {
	/**
	 * Instantiate a continuous item.
	 * @param attribute The attribute
	 * @param value The continuous value
	 */
	ContinuousItem(Attribute attribute, Double value) {
		super(attribute, value);
	}

	/**
	 * Get the distance from a continuous item to a generic object.
	 * @param o The object from which calculate the distance
	 * @return The actual distance
	 */
	double distance(Object o) {
		return Math.abs(distance(o));
	}

	/**
	 * Get the difference from a continuous item to a generic object.
	 * @param o The object from which calculate the distance
	 * @return The actual distance
	 */
	double difference(Object o) {
		ContinuousAttribute attr = (ContinuousAttribute) this.getAttribute();

		double a = attr.getScaledValue((double) o);
		double b = attr.getScaledValue((double) this.getValue());

		return a - b;
	}
}
