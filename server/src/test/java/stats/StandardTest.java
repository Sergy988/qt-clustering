
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import stats.Standard;

import org.la4j.Vector;
import org.la4j.Vectors;
import org.la4j.vector.dense.BasicVector;

import stats.Mean;
import stats.Variance;

/**
 * Standard class tester.
 */
public class StandardTest {

	/**
	 * The accuracy of the test.
	 */
	static private final double EPS = 1e-8;

	/**
	 * Standard.standardize() tester.
	 */
	@Test
	public void testStandardize() {
		Vector samples = new BasicVector(4);
		samples.set(0,  1.0);
		samples.set(1, -0.5);
		samples.set(2,  2.0);
		samples.set(3,  2.5);

		samples = Standard.standardize(samples);

		assertEquals(samples.get(0), -0.218217890, EPS);
		assertEquals(samples.get(1), -1.527525231, EPS);
		assertEquals(samples.get(2),  0.654653671, EPS);
		assertEquals(samples.get(3),  1.091089451, EPS);
	}
}

