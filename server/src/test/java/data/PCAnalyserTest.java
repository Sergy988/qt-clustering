
package data;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.fail;

import data.Data;
import data.PCAnalyser;
import java.sql.SQLException;
import database.NoValueException;
import database.EmptySetException;
import database.DatabaseConnectionException;

import stats.StatisticException;

/**
 * PCAnalyser class tester.
 */
public class PCAnalyserTest {

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



