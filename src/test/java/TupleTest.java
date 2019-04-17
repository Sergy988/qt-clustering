
import org.junit.Test;
import static org.junit.Assert.*;

public class TupleTest {
	@Test
	public void testGetDistance() {
		Attribute attribute = new DiscreteAttribute(
			"attr", 0,
			new String[]{ "foo", "bar", "cin" }
		);

		Tuple first  = new Tuple(4);
		first.add(new DiscreteItem(attribute, "foo"), 0);
		first.add(new DiscreteItem(attribute, "bar"), 1);
		first.add(new DiscreteItem(attribute, "cin"), 2);
		first.add(new DiscreteItem(attribute, "foo"), 3);

		Tuple second = new Tuple(4);
		second.add(new DiscreteItem(attribute, "bar"), 0);
		second.add(new DiscreteItem(attribute, "foo"), 1);
		second.add(new DiscreteItem(attribute, "bar"), 2);
		second.add(new DiscreteItem(attribute, "foo"), 3);

		assert(Double.compare(first.getDistance(second), 3.0) == 0);
	}
}
