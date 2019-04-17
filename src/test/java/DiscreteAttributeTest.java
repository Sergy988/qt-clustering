
import org.junit.Test;
import static org.junit.Assert.*;

public class DiscreteAttributeTest {
	@Test
	public void getNumberOfDistinctValues() {
		DiscreteAttribute attribute = new DiscreteAttribute(
			"attr", 0,
			new String[]{ "foo", "bar", "cin", "out" }
		);

		assertEquals(attribute.getNumberOfDistinctValues(), 4);
	}

	@Test
	public void getValue() {
		DiscreteAttribute attribute = new DiscreteAttribute(
			"attr", 0,
			new String[]{ "foo", "bar", "cin", "out" }
		);

		assertEquals(attribute.getValue(1), "bar");
	}
}

