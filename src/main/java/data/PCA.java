
package data;

import java.util.List;
import java.util.LinkedList;

import stats.Point3D;
import stats.Standard;
import stats.Covariance;
import stats.MultiVariation;
import stats.StatisticException;

import org.la4j.Vector;
import org.la4j.Matrix;
import org.la4j.vector.dense.BasicVector;
import org.la4j.matrix.dense.Basic2DMatrix;

/**
 * Principal Component analyser (PCA with 3-dimensional projection).
 */
public class PCA {

	/**
	 * The 3D projection of the points of the dataset.
	 */
	private List<Point3D> points = new LinkedList<Point3D>();

	/**
	 * Construct a PCA.
	 * @param data The dataset to project
	 * @throws StatisticException Thrown when a statistic error occurs
	 */
	public PCA(final Data data) throws StatisticException {
		int samplesCount = data.getNumberOfExamples();
		int attributesCount = data.getNumberOfExplanatoryAttributes();

		Matrix samples = new Basic2DMatrix(samplesCount, attributesCount);

		// Build the samples matrix
		for (int i = 0; i < samplesCount; i++) {
			samples.setRow(i, data.getTuple(i).toNumericVector());
		}

		// Standardize the samples
		for (int i = 0; i < attributesCount; i++) {
			samples.setColumn(i, Standard.standardize(samples.getColumn(i)));
		}

		// Build the covariance matrix
		Matrix covariance = Covariance.covariance(samples);

		Vector[] projectionAxes = null;

		try {
			// Get the major eigenvectors
			projectionAxes = MultiVariation.majorEigenvectors3D(covariance);
		} catch (StatisticException e) {
			// Build versor eigenvectors
			projectionAxes = new BasicVector[3];
			projectionAxes[0] = BasicVector.zero(attributesCount);
			projectionAxes[1] = BasicVector.zero(attributesCount);
			projectionAxes[2] = BasicVector.zero(attributesCount);

			for (int i = 0; i < 3; i++) {
				if (i < attributesCount) {
					projectionAxes[i].set(i, 1.0);
				}
			}
		}

		// Project the samples to the projection axes
		for (int i = 0; i < samplesCount; i++) {
			points.add(projectSample3D(samples.getRow(i), projectionAxes));
		}

		System.out.println("Samples:");
		System.out.println(samples);

		System.out.println("Covariance matrix:");
		System.out.println(covariance);

		System.out.println("First axis:");
		System.out.println(projectionAxes[0]);

		System.out.println("Second axis:");
		System.out.println(projectionAxes[1]);

		System.out.println("Third axis:");
		System.out.println(projectionAxes[2]);
	}

	/**
	 * Get the point at index i.
	 * @param i The index of the point in the projected dataset
	 * @return The point at index i
	 */
	public Point3D get(int i) {
		return points.get(i);
	}

	/**
	 * Project the sample vector to the projection axes (must be of length 3).
	 * @param sample The sample vector to project
	 * @param axes The projection axes
	 * @return A 3D projected point that rappresents the sample
	 */
	private static Point3D projectSample3D(Vector sample, Vector[] axes) {
		// Project the n-dimensional point to the first projection axis
		double x = sample.innerProduct(axes[0]);

		// Project the n-dimensional point to the second projection axis
		double y = sample.innerProduct(axes[1]);

		// Project the n-dimensional point to the third projection axis
		double z = sample.innerProduct(axes[2]);

		return new Point3D(x, y, z);
	}
}
