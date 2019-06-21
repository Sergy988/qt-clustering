
package stats;

import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.decomposition.EigenDecompositor;

/**
 * Multivariation processor class.
 */
public class MultiVariation {

	/**
	 * Disable the constructor.
	 */
	private MultiVariation() {
		// nothing
	}

	/**
	 * Calculate the major eigenvectors of a correlation matrix.
	 * @param dim The number of major eigenvectors.
	 * @param correlation The correlation matrix
	 * @return A matrix of sorted eigenvectors
	 * @throws StatisticException Thrown when the correlation matrix is
	 *                            not quadratic or if its number of columns
	 *                            is less than dimension.
	 */
	public static Matrix majorEigenvectors(int dim, Matrix correlation)
		throws StatisticException {
		// Check if the correlation matrix is valid
		if (correlation.rows() != correlation.columns()) {
			throw new StatisticException("Correlation matrix is not square");
		}

		if (correlation.columns() < dim) {
			throw new StatisticException("Correlation matrix is too small");
		}

		EigenDecompositor eigen = new EigenDecompositor(correlation);

		// Get the eigen decomposition
		Matrix[] decomposition = eigen.decompose();

		Matrix eigenvectors = decomposition[0];
		Matrix eigenvalues  = decomposition[1];

		Matrix majorEigenvectors = new Basic2DMatrix(eigenvectors.rows(), dim);

		// Build the major eigevectors matrix
		for (int i = 0; i < dim; i++) {
			int evIndex = maximumEigevalue(eigenvalues);
			eigenvalues.set(evIndex, evIndex, -0.0);

			majorEigenvectors.setColumn(
				i, eigenvectors.getColumn(evIndex)
			);
		}

		return majorEigenvectors;
	}

	/**
	 * Get the position of the maximum eigenvalue.
	 * @param eigenvalues The eigenvalues matrix
	 * @return The position of the maximum eigenvalue
	 */
	private static int maximumEigevalue(Matrix eigenvalues) {
		int index = 0;
		double max = eigenvalues.get(0, 0);

		for (int i = 1; i < eigenvalues.columns(); i++) {
			if (eigenvalues.get(i, i) > max) {
				max = eigenvalues.get(i, i);
				index = i;
			}
		}

		return index;
	}
}

