
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
	 * Calculate the major two autovectors of a correlation matrix.
	 * @param correlation The correlation matrix
	 * @return An array of two vectors
	 */
	static public Vector[] majorAutovectors2D(Matrix correlation) {
		EigenDecompositor eigen = new EigenDecompositor(correlation);

		// Get the eigen decomposition
		Matrix[] decomposition = eigen.decompose();

		Matrix autovectors = decomposition[0];
		Matrix autovalues  = decomposition[1];
		System.out.println("Autovectors:");
		System.out.println(autovectors);

		System.out.println("Autovalues:");
		System.out.println(autovalues);

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

