
package stats;

/**
 * Covariance processor class.
 */
class Covariance {

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
	static double covariance(
		double[] firstSamples, double[] secondSamples)
		throws StatisticException {
		if (firstSamples.length != secondSamples.length) {
			throw new StatisticException("samples length mismatch");
		}

		int samplesCount = firstSamples.length;

		double deviation = 0.0;

		double firstMean = Mean.arithmeticMean(firstSamples);
		double secondMean = Mean.arithmeticMean(secondSamples);

		for (int i = 0; i < samplesCount; i++) {
			double firstDiff = firstSamples[i] - firstMean;
			double secondDiff = secondSamples[i] - secondMean;

			deviation += firstDiff * secondDiff;
		}

		return deviation / samplesCount;
	}
}

