
package stats;

import org.la4j.Vector;

/**
 * Covariance processor class.
 */
public class Covariance {

	/**
	 * Calculate the covariance between two samples.
	 * @param firstSamples The first samples data
	 * @param secondSamples The second samples data
	 * @return The covariance factor [-1, +1]
	 * @throws StatisticException Thrown when the number of samples
	 *                            of the first character is not equal
	 *                            to the number of samples of the
	 *                            second character
	 */
	public static double covariance(
		Vector firstSamples, Vector secondSamples)
		throws StatisticException {
		if (firstSamples.length() != secondSamples.length()) {
			throw new StatisticException("samples length mismatch");
		}

		int samplesCount = firstSamples.length();

		double deviation = 0.0;

		double firstMean = Mean.arithmeticMean(firstSamples);
		double secondMean = Mean.arithmeticMean(secondSamples);

		for (int i = 0; i < samplesCount; i++) {
			double firstDiff = firstSamples.get(i) - firstMean;
			double secondDiff = secondSamples.get(i) - secondMean;

			deviation += firstDiff * secondDiff;
		}

		return deviation / samplesCount;
	}
}

