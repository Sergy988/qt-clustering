
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
		Matrix correlation = new Basic2DMatrix(
			samples.columns(), samples.columns()
		);

		// Build the correlation matrix
		for (int i = 0; i < samples.columns(); i++) {
			for (int j = i + 1; j < samples.columns(); j++) {
				double value = Correlation.correlation(
					correlation.getColumn(i),
					correlation.getColumn(j)
				);

				correlation.set(i, j, value);
				correlation.set(j, i, value);
			}

			correlation.set(i, i, 1.0);
		}

		EigenDecompositor eigen = new EigenDecompositor(correlation);

		// Get the autovectors
		Matrix[] decomposition = eigen.decompose();
		Matrix autovectors = decomposition[0];

		// Get the first and the second autovectors
		Vector firstAutovector = autovectors.getColumn(0);
		Vector secondAutovector = autovectors.getColumn(1);

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

