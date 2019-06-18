
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import data.Data;
import data.Tuple;
import java.sql.SQLException;
import database.NoValueException;
import database.EmptySetException;
import database.DatabaseConnectionException;

import org.la4j.Vector;

/**
 * Tuple class tester.
 */
public class TupleTest {

	/**
	 * The accuracy of the test.
	 */
	static private final double EPS = 1e-8;

	/**
	 * The dataset.
	 */
	private static Data data;

	/**
	 * Construct the attributes.
	 */
	@BeforeClass
	public static void initialize() {
		try {
			data = new Data("test");
		} catch (ClassNotFoundException
			| SQLException
			| NoValueException
			| DatabaseConnectionException
			| EmptySetException e) {
			fail(e.toString());
		}
	}

	/**
	 * Test Tuple getLength() method.
	 */
	@Test
	public void testGetLength() {
		Tuple tuple = data.getTuple(0);
		assertEquals(tuple.getLength(), 2);
	}

	/**
	 * Test Tuple toNumericVector() method.
	 */
	@Test
	public void testToNumericVector() {
		Tuple tuple = data.getTuple(0);
		Vector vector = tuple.toNumericVector();
		assertEquals(vector.get(0), 0.0, EPS);
		assertEquals(vector.get(1), 1.0, EPS);
	}

	/**
	 *  Test Tuple getDistance() method.
	 */
	@Test
	public void testGetDistance() {
		Tuple first = data.getTuple(0);
		Tuple second = data.getTuple(2);
		double dist = first.getDistance(second);
		assertEquals(dist, 2.0, EPS);
	}

	/**
	 * Test Tuple avgDistance() method.
	 */
	@Test
	public void avgDistance() {
		Tuple tuple = data.getTuple(0);

		Set<Integer> clusteredData = new TreeSet<Integer>();
		clusteredData.add(0);
		clusteredData.add(1);
		clusteredData.add(2);

		double dist = tuple.avgDistance(data, clusteredData);
		assertEquals(dist, (0.0 + 1.5 + 2.0) / 3.0, EPS);
	}
}



