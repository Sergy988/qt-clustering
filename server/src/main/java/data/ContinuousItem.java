
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
	ContinuousItem(ContinuousAttribute attribute, Double value) {
		super(attribute, value);
	}

	/**
	 * Get the distance from a continuous item to a generic object.
	 * @param o The object from which calculate the distance
	 * @return The actual distance
	 */
	double distance(Object o) {
		ContinuousAttribute attr = (ContinuousAttribute) this.getAttribute();

		double a = attr.getScaledValue((double) o);
		double b = attr.getScaledValue((double) this.getValue());

		return Math.abs(a - b);
	}

	/**
	 * Get the numeric rappresentation of the continuous item.
	 * @return The value of the continuous item
	 */
	double toNumeric() {
		return (double) getValue();
	}
}
