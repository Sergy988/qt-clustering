
package stats;

import org.la4j.Vector;

/**
 * Variance processor class.
 */
public class Variance {

	/**
	 * Disable the constructor.
	 */
	private Variance() {
		// nothing
	}

	/**
	 * Calculate the variance.
	 * @param samples The samples data
	 * @return The variance
	 */
	public static double variance(Vector samples) {
		double variance = 0.0;
		double mean = Mean.arithmeticMean(samples);

		for (double x : samples) {
			double diff = x - mean;
			variance += diff * diff;
		}

		return variance / samples.length();
	}
}

