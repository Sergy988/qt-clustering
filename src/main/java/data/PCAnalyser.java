
package data;

import java.util.List;
import java.util.LinkedList;

import stats.Standard;
import stats.Covariance;
import stats.MultiVariation;
import stats.StatisticException;

import org.la4j.Vector;
import org.la4j.Matrix;
import org.la4j.vector.dense.BasicVector;
import org.la4j.matrix.dense.Basic2DMatrix;

/**
 * Principal Component Analyser.
 */
public class PCAnalyser {

	/**
	 * The dimensions of the projected data.
	 */
	private int dimensions;

	/**
	 * The projection of the points of the dataset.
	 */
	private Matrix points;

	/**
	 * Construct a Principal Component Analyser.
	 * @param dim The dimensions of the projected data.
	 *            Note that if dim is zero the points will
	 *            be projected to the Zero axes.
	 */
	public PCAnalyser(int dim) {
		this.dimensions = dim;
	}

	/**
	 * Project the dataset specified by data.
	 * @param data The dataset to project
	 * @throws StatisticException Thrown when a statistic error occurs
	 */
	public void project(Data data) throws StatisticException {
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
			projectionAxes = MultiVariation.majorEigenvectors(
				dimensions, covariance
			);
		} catch (StatisticException e) {
			// Build versor eigenvectors
			projectionAxes = new BasicVector[dimensions];

			for (int i = 0; i < dimensions; i++) {
				projectionAxes[i] = BasicVector.zero(attributesCount);
			}

			for (int i = 0; i < dimensions; i++) {
				if (i < attributesCount) {
					projectionAxes[i].set(i, 1.0);
				}
			}
		}

		// Instantiate the projected points matrix
		points = new Basic2DMatrix(samplesCount, dimensions);

		// Project the samples to the projection axes
		for (int i = 0; i < samplesCount; i++) {
			points.setRow(i, projectSample(samples.getRow(i), projectionAxes));
		}
	}

	/**
	 * Get the i-th point coordinate at index j.
	 * @param i The index of the point in the projected dataset
	 * @param j The index of the coordinate
	 * @return The j-th coordinate of the i-th point
	 */
	public double get(int i, int j) {
		return points.get(i, j);
	}

	/**
	 * Project the sample vector to the projection axes.
	 * @param sample The sample vector to project
	 * @param axes The projection axes
	 * @return A projected point that rappresents the sample
	 */
	private static Vector projectSample(Vector sample, Vector[] axes) {
		Vector point = BasicVector.zero(axes.length);

		for (int i = 0; i < point.length(); i++) {
			point.set(i, sample.innerProduct(axes[i]));
		}

		return point;
	}
}
