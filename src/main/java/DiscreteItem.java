
/**
 * The discrete realization of Item
 */
public class DiscreteItem extends Item {
	/**
	 * Instantiate a discrete item
	 * @param attribute The attribute
	 * @param value The discrete value
	 */
	public DiscreteItem(Attribute attribute, Object value) {
		super(attribute, value);
	}

	/**
	 * Get the distance from a discrete item to a generic object
	 * @param a The object from which calculate the distance
	 * @return The actual distance
	 */
	public double distance(Object a) {
		return getValue().toString().equals(a.toString()) ? 0.0 : 1.0;
	}
}
