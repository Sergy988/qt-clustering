
package data;

import java.util.List;

import stats.Mean;
import stats.Mode;

/**
 * Multivariation statistics processor.
 */
public class Multivariation {

	/**
	 * Calculate the mean tuple of a dataset.
	 * @param data The dataset
	 * @return The mean tuple of the dataset
	 */
	public static Tuple meanTuple(final Data data) {
		List<Attribute> attributes = data.getExplanatorySet();
		int attributesCount = attributes.size();
		int samplesCount = data.getNumberOfExamples();

		Tuple result = new Tuple(attributesCount);

		int i = 0;

		for (Attribute attr : attributes) {

			if (attr instanceof DiscreteAttribute) {
				Object[] samples = new Object[samplesCount];

				for (int j = 0; j < samplesCount; j++) {
					Item item = data.getValue(j, i);
					samples[j] = item.getValue();
				}

				Object mode = Mode.mode(samples);

				result.add(new DiscreteItem(attr, mode), i);
			} else {
				double[] samples = new double[samplesCount];

				for (int j = 0; j < samplesCount; j++) {
					Item item = data.getValue(j, i);
					samples[j] = (double) item.getValue();
				}

				double mean = Mean.arithmeticMean(samples);

				result.add(new ContinuousItem(attr, mean), i);
			}

			i++;
		}

		return result;
	}

}
