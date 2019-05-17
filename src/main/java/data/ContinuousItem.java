
package data;

/**
 * The discrete realization of Item.
 */
class ContinuousItem extends Item {
	/**
	 * Instantiate a discrete item.
	 * @param attribute The attribute
	 * @param value The continuous value
	 */
	ContinuousItem(Attribute attribute, Double value) {
		super(attribute, value);
	}

	/**
	 * Get the distance from a discrete item to a generic object.
	 * @param o The object from which calculate the distance
	 * @return The actual distance
	 */
	double distance(Object o) {
		ContinuousAttribute attr = (ContinuousAttribute) this.getAttribute();

		double a = attr.getScaledValue((double) o);
		double b = attr.getScaledValue((double) this.getValue());

		return Math.abs(a - b);
	}
}
