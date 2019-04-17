
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemTest {
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

