
package stats;

import org.la4j.Vector;
import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;

/**
 * Correlation processor class.
 */
public class Correlation {

	/**
	 * Disable the constructor.
	 */
	private Correlation() {
		// nothing
	}

	/**
	 * Calculate the correlation matrix of a samples matrix.
	 * @param samples The samples matrix
	 * @return The correlation matrix
	 * @throws StatisticException Thrown when a static error occurs
	 */
	public static Matrix correlation(Matrix samples)
		throws StatisticException {
		Matrix correlation = new Basic2DMatrix(
			samples.columns(), samples.columns()
		);

		// Build the correlation matrix
		for (int i = 0; i < samples.columns(); i++) {
			for (int j = i + 1; j < samples.columns(); j++) {
				double value = Correlation.correlation(
					samples.getColumn(i),
					samples.getColumn(j)
				);

				correlation.set(i, j, value);
				correlation.set(j, i, value);
			}

			correlation.set(i, i, 1.0);
		}

		return correlation;
	}

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

