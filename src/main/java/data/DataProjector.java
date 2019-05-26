
package data;

import stats.Point2D;
import stats.Standard;
import stats.Covariance;
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
	 * The samples matrix.
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

		// Build the samples matrix
		for (int i = 0; i < samplesCount; i++) {
			samples.setRow(i, data.getTuple(i).toNumericVector());
		}

		// Standardize the samples
		for (int i = 0; i < attributesCount; i++) {
			samples.setColumn(i, Standard.standardize(samples.getColumn(i)));
		}

		// Build the correlation matrix
		Matrix correlation = Covariance.covariance(samples);

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
		Vector samplePoint = samples.getRow(i);

		// Project the n-dimensional point to the first projection axis
		double x = samplePoint.innerProduct(projectionAxes[0]);

		// Project the n-dimensional point to the second projection axis
		double y = samplePoint.innerProduct(projectionAxes[1]);

		return new Point2D(x, y);
	}
}
