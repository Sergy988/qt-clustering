
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;

public class AttributeTest {

	private static DiscreteAttribute attribute;

	@BeforeClass
	public static void setupClass() {
		attribute = new DiscreteAttribute(
			"discrete_attribute", 42,
			new String[]{ "foo", "bar", "cin", "out" }
		);
	}

	@AfterClass
	public static void cleanupClass() {
		// nothing
	}

	@Test
	public void testGetName() {
		assertEquals(attribute.getName(), "discrete_attribute");
	}

	@Test
	public void testGetIndex() {
		assertEquals(attribute.getIndex(), 42);
	}

	@Test
	public void testToString() {
		assertEquals(attribute.toString(), "discrete_attribute");
	}
}

