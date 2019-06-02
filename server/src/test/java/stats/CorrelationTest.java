
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import stats.Correlation;
import stats.StatisticException;

import org.la4j.Vector;
import org.la4j.Matrix;
import org.la4j.vector.dense.BasicVector;
import org.la4j.matrix.dense.Basic2DMatrix;

/**
 * Correlation class tester.
 */
public class CorrelationTest {

	/**
	 * The accuracy of the test.
	 */
	static private final double EPS = 1e-8;

	/**
	 * Correlation.correlation() tester.
	 */
	@Test
	public void testCovariance() {
		Matrix samples = new Basic2DMatrix(3, 2);
		samples.set(0, 0,  1.0); samples.set(0, 1, -0.5);
		samples.set(1, 0,  2.0); samples.set(1, 1,  2.5);
		samples.set(2, 0,  0.0); samples.set(2, 1,  3.0);

		try {
			Matrix correlation = Correlation.correlation(samples);
			assertEquals(correlation.get(0, 0),  1.000000000, EPS);
			assertEquals(correlation.get(0, 1), -0.132067636, EPS);
			assertEquals(correlation.get(1, 0), -0.132067636, EPS);
			assertEquals(correlation.get(1, 1),  1.000000000, EPS);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	/**
	 * Correlation.correlation() test (samples length mismatch).
	 * @throws StatisticException Thrown when a statistic exception occurs
	 */
	@Test(expected = StatisticException.class)
	public void testCorrelationLengthMismatch() throws StatisticException {
		Vector firstSamples = new BasicVector(2);
		Vector secondSamplse = new BasicVector(3);

		Correlation.correlation(firstSamples, secondSamplse);
	}
}
