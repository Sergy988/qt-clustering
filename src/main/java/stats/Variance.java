
package stats;

/**
 * Variance processor class.
 */
public class Variance {

	/**
	 * Calculate the variance.
	 * @param samples The samples data
	 * @return The variance
	 */
	public static double variance(double[] samples) {
		double deviation = 0.0;
		double mean = Mean.arithmeticMean(samples);

		for (double x : samples) {
			double diff = x - mean;
			deviation += diff * diff;
		}

		return deviation / samples.length;
	}
}

