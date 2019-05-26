
package stats;

import org.la4j.Vector;

/**
 * Mean processor class.
 */
public class Mean {

	/**
	 * Calculate the arithmetic mean.
	 * @param samples The samples data
	 * @return The arithmetic mean
	 */
	public static double arithmeticMean(Vector samples) {
		double sum = 0.0;

		for (double x : samples) {
			sum += x;
		}

		return sum / samples.length();
	}
}

