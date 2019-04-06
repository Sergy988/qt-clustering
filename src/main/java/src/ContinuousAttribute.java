
package src;

public class ContinuousAttribute extends Attribute {

	private double min;
	private double max;
	
	ContinuousAttribute(String name, int index, int min, int max) {
		super(name, index);
		this.min = min;
		this.max = max;
	}
	
	public double getScaledValue(double v) {
		return (v - min) / (max - min);
	}
}
