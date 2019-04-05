
public class Data {
	
	private Object data[][];
	private int numberOfExamples;
	private Attribute attributeSet[];
	
	Data() {
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

		attributeSet = new Attribute[5];
		
		attributeSet[0] = new DiscreteAttribute("Outlook", 0, outlookValues);
		attributeSet[1] = new DiscreteAttribute("Temperature", 1, temperatureValues);
		attributeSet[2] = new DiscreteAttribute("Humidity", 2, humidityValues);
		attributeSet[3] = new DiscreteAttribute("Wind", 3, windValues);
		attributeSet[4] = new DiscreteAttribute("PlayTennis", 4, playtennisValues);
	}
	
	public int getNumberOfExamples() {
		return numberOfExamples;
	}
	
	public int getNumberOfAttributes() {
		return attributeSet.length;
	}
	
	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return data[exampleIndex][attributeIndex];
	}
	
	public Attribute getAttribute(int i) {
		return attributeSet[i];
	}
	
	public String toString() {
		String output = new String("");
		
		for(int i = 0; i < getNumberOfAttributes(); i++) {
			output += attributeSet[i] + " ";
		}
		
		for(int i = 0; i < getNumberOfExamples(); i++) {
			output += "\n" + (i + 1) + ". ";
			
			for(int j = 0; j < getNumberOfAttributes(); j++) {
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
