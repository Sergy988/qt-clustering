
/**
 * Source data class
 */
public class Data {
	
	/**
	 * The source data samples
	 */
	private Object data[][];

	/**
	 * The number of samples
	 */
	private int samplesCount;

	/**
	 * The attribute scheme which is based the data
	 */
	private Attribute attributeScheme[];
	
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

		samplesCount = 14;

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

		attributeScheme = new Attribute[5];
		
		attributeScheme[0] = new DiscreteAttribute("Outlook", 0, outlookValues);
		attributeScheme[1] = new DiscreteAttribute("Temperature", 1, temperatureValues);
		attributeScheme[2] = new DiscreteAttribute("Humidity", 2, humidityValues);
		attributeScheme[3] = new DiscreteAttribute("Wind", 3, windValues);
		attributeScheme[4] = new DiscreteAttribute("PlayTennis", 4, playtennisValues);
	}
	
	/**
	 * Get the number of samples
	 * @return The number of samples
	 */
	public int getSampleCount() {
		return samplesCount;
	}

	/**
	 * Get the scheme which is based the source data
	 * @return The attribute scheme
	 */
	public Attribute[] getAttributeScheme() {
		return attributeScheme;
	}
	
	/**
	 * Get the number of attributes
	 * @return The size of the attribute scheme
	 */
	public int getAttributesCount() {
		return attributeScheme.length;
	}

	/**
	 * Get an attribute
	 * @param i The attribute index
	 * @return The attribute at position i in the attribute scheme
	 */
	public Attribute getAttribute(int i) {
		return attributeScheme[i];
	}

	/**
	 * Get an attribute value from the source data
	 * @param sampleIndex The index of the sample
	 * @param attributeIndex The index of the attribute
	 * @return The attribute value from the source data
	 */
	public Object getAttributeValue(int sampleIndex, int attributeIndex) {
		return data[sampleIndex][attributeIndex];
	}
	
	/**
	 * Convert the source data to a string
	 * @return The textual rappresentation of the source data
	 */
	public String toString() {
		String output = new String("");
		
		for(int i = 0; i < getAttributesCount(); i++) {
			output += attributeScheme[i] + " ";
		}
		
		for(int i = 0; i < getSampleCount(); i++) {
			output += "\n" + (i + 1) + ". ";
			
			for(int j = 0; j < getAttributesCount(); j++) {
				output += getAttributeValue(i, j) + " ";
			}
		}
		
		return output;
	}

	public static void main(String args[]) {
		Data trainingSet = new Data();
		System.out.println(trainingSet);
	}
}
