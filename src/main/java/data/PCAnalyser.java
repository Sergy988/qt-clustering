
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

		Matrix projectionMatrix = null;

		try {
			// Get the major eigenvectors
			projectionMatrix = MultiVariation.majorEigenvectors(
				dimensions, covariance
			);
		} catch (StatisticException e) {
			// Build versor eigenvectors
			projectionMatrix = Basic2DMatrix.zero(attributesCount, dimensions);

			for (int i = 0; i < attributesCount; i++) {
				projectionMatrix.set(i, i, 1.0);
			}
		}

		// Transpose the projection matrix
		projectionMatrix.transpose();

		// Instantiate the projected points matrix
		points = new Basic2DMatrix(samplesCount, dimensions);

		// Project the samples to the projection axes
		for (int i = 0; i < samplesCount; i++) {
			points.setRow(i, projectionMatrix.multiply(samples.getRow(i)));
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
}
