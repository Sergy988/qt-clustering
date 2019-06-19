
package mining;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import data.Data;
import data.EmptyDatasetException;
import java.sql.SQLException;
import database.NoValueException;
import database.EmptySetException;
import database.DatabaseConnectionException;

import mining.QTMiner;
import mining.ClusterSet;
import mining.ClusteringRadiusException;

/**
 * QTMiner class tester.
 */
public class QTMinerTest {

	/**
	 * The dataset.
	 */
	private static Data data;

	/**
	 * Construct the dataset.
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
	 * Test the compute() method (minimum radius).
	 */
	@Test
	public void testCompute() {
		QTMiner qt = new QTMiner(0.001);
		int clustersCount = 0;

		try {
			clustersCount = qt.compute(data);
		} catch (ClusteringRadiusException
			| EmptyDatasetException e) {
			fail(e.toString());
		}

		assertEquals(clustersCount, 3);

		ClusterSet clusterSet = qt.getClusterSet();

		assertEquals(clusterSet.toString(),
			  "0: Centroid = ( C 2.0 )\n"
			+ "1: Centroid = ( B 1.5 )\n"
			+ "2: Centroid = ( A 1.0 )\n");
	}

	/**
	 * Test the compute() method and ClusterSet toString(Data) method
	 * (minimum radius).
	 */
	@Test
	public void testComputeToString() {
		QTMiner qt = new QTMiner(0.001);
		int clustersCount = 0;

		try {
			clustersCount = qt.compute(data);
		} catch (ClusteringRadiusException
			| EmptyDatasetException e) {
			fail(e.toString());
		}

		assertEquals(clustersCount, 3);

		ClusterSet clusterSet = qt.getClusterSet();

		assertEquals(clusterSet.toString(data),
			  "0: Centroid = ( C 2.0 )\n"
			+ "Examples:\n"
			+ "[ C 2.0 ] dist=0.0\n"
			+ "AvgDistance=0.0\n\n"
			+ "1: Centroid = ( B 1.5 )\n"
			+ "Examples:\n"
			+ "[ B 1.5 ] dist=0.0\n"
			+ "AvgDistance=0.0\n\n"
			+ "2: Centroid = ( A 1.0 )\n"
			+ "Examples:\n"
			+ "[ A 1.0 ] dist=0.0\n"
			+ "AvgDistance=0.0\n\n");
	}

	/**
	 * Test the compute() method (maximum radius).
	 * @throws ClusteringRadiusException Thrown when the radius is too much
	 */
	@Test(expected = ClusteringRadiusException.class)
	public void testComputeException() throws ClusteringRadiusException {
		QTMiner qt = new QTMiner(1000.0);
		int clustersCount = 0;

		try {
			clustersCount = qt.compute(data);
		} catch (EmptyDatasetException e) {
			fail(e.toString());
		}
	}
}
