
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import data.Data;
import data.PCAnalyser;
import java.sql.SQLException;
import database.NoValueException;
import database.EmptySetException;
import database.DatabaseConnectionException;

import stats.StatisticException;

import org.la4j.Matrix;

/**
 * PCAnalyser class tester.
 */
public class PCAnalyserTest {

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
	 * Test the project() method.
	 */
	@Test
	public void testProject() {
		PCAnalyser pca = new PCAnalyser(1);

		try {
			pca.project(data);
		} catch (StatisticException e) {
			fail(e.toString());
		}
	}

	/**
	 * Test the project() method
	 * (PCAnalyser dimensions equalor greater than columns count).
	 */
	@Test
	public void testProjectException() {
		PCAnalyser pca = new PCAnalyser(3);

		try {
			pca.project(data);
		} catch (StatisticException e) {
			fail(e.toString());
		}
	}
}



