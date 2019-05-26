
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

		// Get the autovectors
		Matrix[] decomposition = eigen.decompose();
		Matrix autovectors = decomposition[0];
		Matrix autovalues = decomposition[1];

		// Find the first maximum autovalue index
		int firstIndex = 0;
		double firstMax = Double.MIN_VALUE;
		for (int i = 0; i < autovalues.columns(); i++) {
			if (autovalues.get(i, i) > firstMax) {
				firstMax = autovalues.get(i, i);
				firstIndex = i;
			}
		}

		// Find the second maximum autovalue index
		int secondIndex = 0;
		double secondMax = Double.MIN_VALUE;
		for (int i = 0; i < autovalues.columns(); i++) {
			if (i == firstIndex) {
				continue;
			}

			if (autovalues.get(i, i) > secondMax) {
				secondMax = autovalues.get(i, i);
				secondIndex = i;
			}
		}

		// Get the first and the second autovectors
		Vector firstAutovector = autovectors.getColumn(firstIndex);
		Vector secondAutovector = autovectors.getColumn(secondIndex);

		List<Point2D> result = new LinkedList<Point2D>();

		// Project the points
		for (int i = 0; i < samples.rows(); i++) {
			Vector samplePoint = samples.getRow(i);

			double x = samplePoint.innerProduct(firstAutovector);
			double y = samplePoint.innerProduct(secondAutovector);

			result.add(new Point2D(x, y));
		}

		return result;
	}
}

