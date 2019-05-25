
package stats;

/**
 * Mean processor class.
 */
public class Mean {

	/**
	 * Calculate the arithmetic mean.
	 * @param samples The samples data
	 * @return The arithmetic mean
	 */
	public static double arithmeticMean(double[] samples) {
		double sum = 0.0;

		for (double x : samples) {
			sum += x;
		}

		return sum / samples.length;
	}

	/**
	 * Calculate the geometric mean.
	 * @param samples The samples data
	 * @return The geometric mean
	 */
	public static double geometricMean(double[] samples) {
		double prod = 1.0;

		for (double x : samples) {
			prod *= x;
		}

		return Math.pow(prod, 1.0 / samples.length);
	}

	/**
	 * Calculate the armonic mean.
	 * @param samples The samples data
	 * @return The armonic mean
	 */
	public static double armonicMean(double[] samples) {
		double invSum = 0.0;

		for (double x : samples) {
			invSum += 1.0 / x;
		}

		return 1.0 / (invSum / samples.length);
	}

	/**
	 * Calculate the quadratic mean.
	 * @param samples The samples data
	 * @return The quadratic mean
	 */
	public static double quadraticMean(double[] samples) {
		double quadSum = 0.0;

		for (double x : samples) {
			quadSum += x * x;
		}

		return Math.sqrt(quadSum / samples.length);
	}
}

