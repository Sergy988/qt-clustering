
package stats;

/**
 * Standardizer class.
 */
public class Standard {

	/**
	 * Calculate the standardized samples.
	 * @param samples The samples data
	 * @return The standardized samples
	 */
	public static double[] standardize(double[] samples) {
		double mean = Mean.arithmeticMean(samples);
		double deviation = Math.sqrt(Variance.variance(samples));

		double[] standardSamples = new double[samples.length];

		for (int i = 0; i < samples.length; i++) {
			standardSamples[i] = (samples[i] - mean) / deviation;
		}

		return standardSamples;
	}
}

