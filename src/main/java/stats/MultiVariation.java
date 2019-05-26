
package stats;

import java.util.List;
import java.util.LinkedList;

import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.decomposition.EigenDecompositor;

/**
 * Multivariation processor class.
 */
public class MultiVariation {

	/**
	 * Project a matrix of samples to a bidimensional plane.
	 * @param samples The samples
	 * @return A list of 2D projected points
	 * @throws StatisticException Thrown when a statistic exception occurs
	 */
	static public List<Point2D> projectPoints2D(Matrix samples)
		throws StatisticException {
		// Calculate the correlation matrix
		Matrix correlation = Correlation.correlation(samples);

		EigenDecompositor eigen = new EigenDecompositor(correlation);

		// Get the eigen decomposition
		Matrix[] decomposition = eigen.decompose();

		// Get the first and the second autovectors
		Vector[] autovectors = majorAutovectors2D(decomposition);

		List<Point2D> result = new LinkedList<Point2D>();

		// Project the points
		for (int i = 0; i < samples.rows(); i++) {
			Vector samplePoint = samples.getRow(i);

			double x = samplePoint.innerProduct(autovectors[0]);
			double y = samplePoint.innerProduct(autovectors[1]);

			result.add(new Point2D(x, y));
		}

		return result;
	}

	/**
	 * Calculate the major two autovectors.
	 * @param decomposition The Eigen decomposition
	 * @return An array of two vectors
	 */
	static private Vector[] majorAutovectors2D(Matrix[] decomposition) {
		Matrix autovectors = decomposition[0];
		Matrix autovalues = decomposition[1];

		double max = 0.0;

		// Find the first maximum autovalue index
		int firstIndex = 0;
		for (int i = 0; i < autovalues.columns(); i++) {
			if (autovalues.get(i, i) > max) {
				max = autovalues.get(i, i);
				firstIndex = i;
			}
		}

		max = 0.0;

		// Find the second maximum autovalue index
		int secondIndex = 0;
		for (int i = 0; i < autovalues.columns(); i++) {
			if (i != firstIndex && autovalues.get(i, i) > max) {
				max = autovalues.get(i, i);
				secondIndex = i;
			}
		}

		return new Vector[] {
			autovectors.getColumn(firstIndex),
			autovectors.getColumn(secondIndex)
		};
	}
}

