
/**
 * Source data class
 */
public class Data {

	/**
	 * The source data samples
	 */
	private Object data[][];

	/**
	 * The number of examples
	 */
	private int numberOfExamples;

	/**
	 * The attribute scheme which is based the data
	 */
	private Attribute explanatorySet[];

	/**
	 * Instantiate a source data
	 */
	public Data() {
		data = new Object[14][5];

		data[ 0] = new String[]{ "sunny", "hot", "high", "weak", "no" };
		data[ 1] = new String[]{ "sunny", "hot", "high", "strong", "no" };
		data[ 2] = new String[]{ "overcast", "hot", "high", "weak", "yes" };
		data[ 3] = new String[]{ "rain", "mild", "high", "weak", "yes" };
		data[ 4] = new String[]{ "rain", "cool", "normal", "weak", "yes" };
		data[ 5] = new String[]{ "rain", "cool", "normal", "strong", "no" };
		data[ 6] = new String[]{ "overcast", "cool", "normal", "strong", "yes" };
		data[ 7] = new String[]{ "sunny", "mild", "high", "weak", "no" };
		data[ 8] = new String[]{ "sunny", "cool", "normal", "weak", "yes" };
		data[ 9] = new String[]{ "rain", "mild", "normal", "weak", "yes" };
		data[10] = new String[]{ "sunny", "mild", "normal", "strong", "yes" };
		data[11] = new String[]{ "overcast", "mild", "high", "strong", "yes" };
		data[12] = new String[]{ "overcast", "hot", "normal", "weak", "yes" };
		data[13] = new String[]{ "rain", "mild", "high", "strong", "no" };

		numberOfExamples = 14;

		String outlookValues[] = {
			"overcast",
			"rain",
			"sunny"
		};

		String temperatureValues[] = {
			"hot",
			"mild",
			"cold"
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

		explanatorySet = new Attribute[5];

		explanatorySet[0] = new DiscreteAttribute("Outlook", 0, outlookValues);
		explanatorySet[1] = new DiscreteAttribute("Temperature", 1, temperatureValues);
		explanatorySet[2] = new DiscreteAttribute("Humidity", 2, humidityValues);
		explanatorySet[3] = new DiscreteAttribute("Wind", 3, windValues);
		explanatorySet[4] = new DiscreteAttribute("PlayTennis", 4, playtennisValues);
	}

	/**
	 * Get the number of examples
	 * @return The number of examples
	 */
	public int getNumberOfExamples() {
		return numberOfExamples;
	}

	/**
	 * Get the scheme which is based the source data
	 * @return The attribute scheme
	 */
	public Attribute[] getExplanatorySet() {
		return explanatorySet;
	}

	/**
	 * Get the number of attributes
	 * @return The size of the attribute scheme
	 */
	public int getNumberOfExplanatoryAttributes() {
		return explanatorySet.length;
	}

	/**
	 * Get an attribute
	 * @param i The attribute index
	 * @return The attribute at position i in the attribute scheme
	 */
	public Attribute getAttribute(int i) {
		return explanatorySet[i];
	}

	/**
	 * Get an attribute value from the source data
	 * @param sampleIndex The index of the sample
	 * @param attributeIndex The index of the attribute
	 * @return The attribute value from the source data
	 */
	public Object getValue(int sampleIndex, int attributeIndex) {
		return data[sampleIndex][attributeIndex];
	}

	/**
	 * Create a Tuple of a row in data
	 * @param index The index of the row
	 * @return A new tuple
	 */
	Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(getNumberOfExplanatoryAttributes());

		for(int i = 0; i < getNumberOfExplanatoryAttributes(); i++) {
			tuple.add(
				new DiscreteItem(explanatorySet[i],
				(String)data[index][i]
			), i);
		}

		return tuple;
	}

	/**
	 * Convert the source data to a string
	 * @return The textual rappresentation of the source data
	 */
	public String toString() {
		String output = "";

		for(int i = 0; i < getNumberOfExplanatoryAttributes(); i++) {
			output += explanatorySet[i] + " ";
		}

		for(int i = 0; i < getNumberOfExamples(); i++) {
			output += "\n" + (i + 1) + ". ";

			for(int j = 0; j < getNumberOfExplanatoryAttributes(); j++) {
				output += getValue(i, j) + " ";
			}
		}

		return output;
	}
}
