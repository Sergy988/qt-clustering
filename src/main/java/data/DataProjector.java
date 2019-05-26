
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
	 * The dataset.
	 */
	private Data data;

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
		this.data = data;

		int samplesCount = data.getNumberOfExamples();
		int attributesCount = data.getNumberOfExplanatoryAttributes();

		Matrix samples = new Basic2DMatrix(samplesCount, attributesCount);

		// Build the numeric samples matrix
		for (int i = 0; i < samplesCount; i++) {
			samples.setRow(i, data.getTuple(i).toNumericVector());
		}

		// Build the correlation matrix
		Matrix correlation = Correlation.correlation(samples);

		// Get the major autovectors
		projectionAxes = MultiVariation.majorAutovectors2D(correlation);

		System.out.println("Samples:");
		System.out.println(samples);

		System.out.println("Correlation matrix:");
		System.out.println(correlation);

		System.out.println("First axis:");
		System.out.println(projectionAxes[0]);

		System.out.println("Second axis:");
		System.out.println(projectionAxes[1]);
	}

	/**
	 * Get a plane-projected point of the dataset.
	 * @param i The index of the tuple in the dataset
	 * @return A plane-projected point that rappresents the tuple at position i
	 */
	public Point2D getPoint2D(int i) {
		Vector samplePoint = data.getTuple(i).toNumericVector();

		// Project the n-dimensional point to the first projection axis
		double x = samplePoint.innerProduct(projectionAxes[0]);

		// Project the n-dimensional point to the second projection axis
		double y = samplePoint.innerProduct(projectionAxes[1]);

		return new Point2D(x, y);
	}
}
