
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import data.DiscreteAttribute;

public class DiscreteAttributeTest {

	private static DiscreteAttribute attribute;

	@BeforeClass
	public static void setupClass() {
		attribute = new DiscreteAttribute(
			"attr", 0,
			new String[]{ "foo", "bar", "cin", "out" }
		);
	}

	@AfterClass
	public static void clenupClass() {
		// nothing
	}

	@Test
	public void testGetNumberOfDistinctValues() {
		assertEquals(attribute.getNumberOfDistinctValues(), 4);
	}

	@Test
	public void testGetValue() {
		assertEquals(attribute.getValue(0), "foo");
		assertEquals(attribute.getValue(1), "bar");
		assertEquals(attribute.getValue(2), "cin");
		assertEquals(attribute.getValue(3), "out");
	}
}

