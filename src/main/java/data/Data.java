
package data;

import java.util.List;
import java.util.LinkedList;

/**
 * Source data class.
 */
public class Data {

	/**
	 * The source data samples.
	 */
	private Object data[][];

	/**
	 * The number of examples.
	 */
	private int numberOfExamples;

	/**
	 * The attribute scheme which is based the data.
	 */
	private List<Attribute> explanatorySet = new LinkedList<Attribute>();

	/**
	 * Instantiate a source data.
	 */
	public Data() {
		data = new Object[14][5];

		data[ 0] = new Object[]{"sunny", 30.3, "high", "weak", "no" };
		data[ 1] = new Object[]{"sunny", 30.3, "high", "strong", "no" };
		data[ 2] = new Object[]{"overcast", 30.0, "high", "weak", "yes" };
		data[ 3] = new Object[]{"rain", 13.0, "high", "weak", "yes" };
		data[ 4] = new Object[]{"rain", 0.0, "normal", "weak", "yes" };
		data[ 5] = new Object[]{"rain", 0.0, "normal", "strong", "no" };
		data[ 6] = new Object[]{"overcast", 0.1, "normal", "strong", "yes" };
		data[ 7] = new Object[]{"sunny", 13.0, "high", "weak", "no" };
		data[ 8] = new Object[]{"sunny", 0.1, "normal", "weak", "yes" };
		data[ 9] = new Object[]{"rain", 12.0, "normal", "weak", "yes" };
		data[10] = new Object[]{"sunny", 12.5, "normal", "strong", "yes" };
		data[11] = new Object[]{"overcast", 12.5, "high", "strong", "yes" };
		data[12] = new Object[]{"overcast", 29.21, "normal", "weak", "yes" };
		data[13] = new Object[]{"rain", 12.5, "high", "strong", "no" };

		numberOfExamples = 14;

		String outlookValues[] = {
			"overcast",
			"rain",
			"sunny"
		};

		String humidityValues[] = {
			"low",
			"normal",
			"high"
		};

		String windValues[] = {
			"weak",
			"strong"
		};

		String playtennisValues[] = {
			"no",
			"yes"
		};

		explanatorySet.add(
			new DiscreteAttribute("Outlook", 0, outlookValues)
		);

		explanatorySet.add(
			new ContinuousAttribute("Temperature", 1, 0.0, 30.3)
		);

		explanatorySet.add(
			new DiscreteAttribute("Humidity", 2, humidityValues)
		);

		explanatorySet.add(
			new DiscreteAttribute("Wind", 3, windValues)
		);

		explanatorySet.add(
			new DiscreteAttribute("PlayTennis", 4, playtennisValues)
		);
	}

	/**
	 * Get the number of examples.
	 * @return The number of examples
	 */
	public int getNumberOfExamples() {
		return numberOfExamples;
	}

	/**
	 * Get the scheme which is based the source data.
	 * @return The attribute scheme
	 */
	public List<Attribute> getExplanatorySet() {
		return explanatorySet;
	}

	/**
	 * Get the number of attributes.
	 * @return The size of the attribute scheme
	 */
	public int getNumberOfExplanatoryAttributes() {
		return explanatorySet.size();
	}

	/**
	 * Get an attribute.
	 * @param i The attribute index
	 * @return The attribute at position i in the attribute scheme
	 */
	public Attribute getAttribute(int i) {
		return explanatorySet.get(i);
	}

	/**
	 * Get an attribute value from the source data.
	 * @param sampleIndex The index of the sample
	 * @param attributeIndex The index of the attribute
	 * @return The attribute value from the source data
	 */
	public Object getValue(int sampleIndex, int attributeIndex) {
		return data[sampleIndex][attributeIndex];
	}

	/**
	 * Create a Tuple of a row in data.
	 * @param index The index of the row
	 * @return A new tuple
	 */
	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(getNumberOfExplanatoryAttributes());

		for (int i = 0; i < getNumberOfExplanatoryAttributes(); i++) {
			Attribute attr = explanatorySet.get(i);

			if (attr.getClass() == DiscreteAttribute.class) {
				tuple.add(new DiscreteItem(attr,
					(String) data[index][i]), i);
			} else {
				tuple.add(new ContinuousItem(attr,
					(Double) data[index][i]), i);
			}
		}

		return tuple;
	}

	/**
	 * Convert the source data to a string.
	 * @return The textual rappresentation of the source data
	 */
	public String toString() {
		String output = "";

		for (Attribute attr : explanatorySet) {
			output += attr + " ";
		}

		for (int i = 0; i < getNumberOfExamples(); i++) {
			output += "\n" + (i + 1) + ". ";

			for (int j = 0; j < getNumberOfExplanatoryAttributes(); j++) {
				output += getValue(i, j) + " ";
			}
		}

		return output;
	}
}
