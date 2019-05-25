
package stats;

/**
 * Variance processor class.
 */
class Variance {

	/**
	 * Calculate the variance.
	 * @param samples The samples data
	 * @return The variance
	 */
	static double variance(double[] samples) {
		double deviation = 0.0;
		double mean = Mean.arithmeticMean(samples);

		for (double x : samples) {
			double diff = x - mean;
			deviation += diff * diff;
		}

		return deviation / samples.length;
	}
}

