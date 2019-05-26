
package data;

import stats.Point2D;
import stats.Correlation;
import stats.MultiVariation;
import stats.StatisticException;

import org.la4j.Vector;
import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;

/**
 * Data projector to a bidimensional plane.
 */
public class DataProjector {

	/**
	 * The numeric samples matrix.
	 */
	private Matrix samples;

	/**
	 * The projection axes.
	 */
	private Vector[] projectionAxes;

	/**
	 * Construct a DataProjector.
	 * @param data The dataset to project
	 * @throws StatisticException Thrown when a statistic error occurs
	 */
	public DataProjector(final Data data) throws StatisticException {
		int samplesCount = data.getNumberOfExamples();
		int attributesCount = data.getNumberOfExplanatoryAttributes();

		samples = new Basic2DMatrix(samplesCount, attributesCount);

		// Build the numeric samples matrix
		for (int i = 0; i < samplesCount; i++) {
			samples.setRow(i, data.getTuple(i).toNumericVector());
		}

		// Build the correlation matrix
		Matrix correlation = Correlation.correlation(samples);

		// Get the major autovectors
		projectionAxes = MultiVariation.majorAutovectors2D(correlation);
	}

	/**
	 * Get a plane-projected point of the dataset.
	 * @param i The index of the tuple in the dataset
	 * @return A plane-projected point that rappresents the tuple at position i
	 */
	public Point2D getPoint2D(int i) {
		Vector samplePoint = samples.getRow(i);

		// Project the n-dimensional point to the first projection axis
		double x = samplePoint.innerProduct(projectionAxes[0]);

		// Project the n-dimensional point to the second projection axis
		double y = samplePoint.innerProduct(projectionAxes[1]);

		return new Point2D(x, y);
	}
}
