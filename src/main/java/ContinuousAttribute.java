
/**
 * Attribute characterized by a real value
 */
public class ContinuousAttribute extends Attribute {

	/**
	 * The minimum value
	 */
	private double min;

	/**
	 * The maximum value
	 */
	private double max;

	/**
	 * Instantiate a new continuous attribute
	 * @param name The name of the attribute
	 * @param index An unique identifier
	 * @param min The minimum value
	 * @param max The maximum value
	 */
	public ContinuousAttribute(String name, int index, int min, int max) {
		super(name, index);
		this.min = min;
		this.max = max;
	}

	/**
	 * Given a certain real value, get the scaled value
	 * @param v The value to scale
	 * @return The scaled value
	 */
	public double getScaledValue(double v) {
		return (v - min) / (max - min);
	}
}
