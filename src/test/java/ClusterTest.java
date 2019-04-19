
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import java.util.Arrays;
import static org.junit.Assert.*;

public class ClusterTest {

	private Cluster cluster;

	private static Tuple centroid;
	private static DiscreteAttribute attribute;

	@BeforeClass
	public static void setupClass() {
		attribute = new DiscreteAttribute(
			"attr", 0,
			new String[]{ "foo", "bar", "cin", "out" }
		);

		centroid = new Tuple(3);
		centroid.add(new DiscreteItem(attribute, "bar"), 0);
		centroid.add(new DiscreteItem(attribute, "foo"), 1);
		centroid.add(new DiscreteItem(attribute, "cin"), 2);
	}

	@AfterClass
	public static void cleanupClass() {
		// nothing
	}

	@Before
	public void setupTest() {
		cluster = new Cluster(centroid);
	}

	@After
	public void cleanupTest() {
		// nothing
	}

	@Test
	public void testGetCentroid() {
		assertEquals(cluster.getCentroid().getLength(), 3);
		assertEquals(cluster.getCentroid().get(0).toString(), "bar");
		assertEquals(cluster.getCentroid().get(1).toString(), "foo");
		assertEquals(cluster.getCentroid().get(2).toString(), "cin");
	}

	@Test
	public void testAddData() {
		cluster.addData(42);

		assertEquals(cluster.getSize(), 1);
		assertEquals(cluster.contain(42), true);
		assertEquals(cluster.contain(37), false);
	}

	@Test
	public void testRemoveTuple() {
		cluster.addData(17);
		cluster.addData(42);
		cluster.addData(99);

		cluster.removeTuple(42);
		assertEquals(cluster.getSize(), 2);
		assertEquals(cluster.contain(42), false);

		cluster.removeTuple(17);
		assertEquals(cluster.getSize(), 1);
		assertEquals(cluster.contain(17), false);

		cluster.removeTuple(99);
		assertEquals(cluster.getSize(), 0);
		assertEquals(cluster.contain(99), false);
	}

	@Test
	public void testIterator() {
		cluster.addData(17);
		cluster.addData(42);
		cluster.addData(99);

		int[] data = cluster.iterator();

		assertEquals(Arrays.equals(data, new int[]{ 17, 42, 99 }), true);
	}

	@Test
	public void testToString() {
		assertEquals(cluster.toString(), 
			"Centroid = ( " +
			"bar foo cin" +
			" )"
		);
	}
}

