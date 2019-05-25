
package stats;

/**
 * Correlation processor class.
 */
public class Correlation {

	/**
	 * Calculate the correlation between two samples.
	 * @param firstSamples The first samples data
	 * @param secondSamples The second samples data
	 * @return The correlation factor [-1, +1]
	 * @throws StatisticException Thrown when the number of samples
	 *                            of the first character is not equal
	 *                            to the number of samples of the
	 *                            second character
	 */
	public static double correlation(
		double[] firstSamples, double[] secondSamples)
		throws StatisticException {
		double covariance = Covariance.covariance(firstSamples, secondSamples);

		double firstDeviation = Math.sqrt(Variance.variance(firstSamples));
		double secondDeviation = Math.sqrt(Variance.variance(secondSamples));

		return covariance / (firstDeviation * secondDeviation);
	}
}

