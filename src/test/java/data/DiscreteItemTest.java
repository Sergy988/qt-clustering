
import org.junit.Test;
import static org.junit.Assert.*;

import data.DiscreteItem;
import data.DiscreteAttribute;

public class DiscreteItemTest {
	@Test
	public  void testGetAttribute() {
		DiscreteAttribute attribute = new DiscreteAttribute(
			"attr", 42,
			new String[]{ "foo", "bar", "cin", "out" }
		);

		DiscreteItem item = new DiscreteItem(attribute, "bar");

		assertEquals(item.getAttribute().getIndex(), 42);
		assertEquals(item.getAttribute().getName(), "attr");
	}
}

