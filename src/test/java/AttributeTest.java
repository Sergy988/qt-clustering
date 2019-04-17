
import org.junit.Test;
import static org.junit.Assert.*;

public class AttributeTest {
	@Test
	public void getName() {
		DiscreteAttribute attribute = new DiscreteAttribute(
			"discrete_attribute", 0,
			new String[]{ "foo", "bar", "cin", "out" }
		);

		assertEquals(attribute.getName(), "discrete_attribute");
	}

	@Test
	public void getIndex() {
		DiscreteAttribute attribute = new DiscreteAttribute(
			"discrete_attribute", 42,
			new String[]{ "foo", "bar", "cin", "out" }
		);

		assertEquals(attribute.getIndex(), 42);
	}

	@Test
	public void testToString() {
		DiscreteAttribute attribute = new DiscreteAttribute(
			"discrete_attribute", 42,
			new String[]{ "foo", "bar", "cin", "out" }
		);

		assertEquals(attribute.toString(), "discrete_attribute");
	}
}

