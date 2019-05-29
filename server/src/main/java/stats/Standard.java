
package stats;

import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

/**
 * Standardizer class.
 */
public class Standard {

	/**
	 * Calculate the standardized samples.
	 * @param samples The samples data
	 * @return The standardized samples
	 */
	public static Vector standardize(Vector samples) {
		double mean = Mean.arithmeticMean(samples);
		double deviation = Math.sqrt(Variance.variance(samples));

		Vector standardSamples = new BasicVector(samples.length());

		for (int i = 0; i < samples.length(); i++) {
			standardSamples.set(i, (samples.get(i) - mean) / deviation);
		}

		return standardSamples;
	}
}

