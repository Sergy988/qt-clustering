
package stats;

import org.la4j.Vector;

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
		Vector firstSamples, Vector secondSamples)
		throws StatisticException {
		double covariance = Covariance.covariance(firstSamples, secondSamples);

		double firstVariance = Variance.variance(firstSamples);
		double secondVariance = Variance.variance(secondSamples);

		if (firstVariance < 1e-12) {
			firstVariance = 1.0;
		}

		if (secondVariance < 1e-12) {
			secondVariance = 1.0;
		}

		return covariance / Math.sqrt(firstVariance * secondVariance);
	}
}

