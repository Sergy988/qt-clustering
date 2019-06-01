
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import stats.MultiVariation;
import stats.StatisticException;

import org.la4j.Vector;
import org.la4j.Matrix;
import org.la4j.vector.dense.BasicVector;
import org.la4j.matrix.dense.Basic2DMatrix;

/**
 * MultiVariation class tester.
 */
public class MultiVariationTest {

	/**
	 * The accuracy of the test.
	 */
	static private final double EPS = 1e-8;

	/**
	 * MultiVariation.majorEigenvectors() tester.
	 */
	@Test
	public void testMajorEigenvectors() {
		Matrix correlation = new Basic2DMatrix(2, 2);
		correlation.set(0, 0, 1.0); correlation.set(0, 1, 0.5);
		correlation.set(1, 0, 0.2); correlation.set(1, 1, 1.0);
		
		try {
			Matrix eigenvectors = MultiVariation.majorEigenvectors(
				1, correlation
			);

			assertEquals(eigenvectors.get(0, 0), 0.845154255, EPS);
			assertEquals(eigenvectors.get(1, 0), 0.534522484, EPS);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * MultiVariation.majorEigenvectors() test (columns and rows mismatch).
	 */
	@Test(expected = StatisticException.class)
	public void testMajorEigenvectorsDimMismatch() throws StatisticException {
		Matrix matrix = new Basic2DMatrix(3, 2);

		MultiVariation.majorEigenvectors(1, matrix);
	}

	/**
	 * MultiVariation.majorEigenvectors() test
	 * (correlation dimention less than dim).
	 */
	@Test(expected = StatisticException.class)
	public void testMajorEigenvectorsInvalidDim() throws StatisticException {
		Matrix matrix = new Basic2DMatrix(3, 3);

		MultiVariation.majorEigenvectors(4, matrix);
	}
}

