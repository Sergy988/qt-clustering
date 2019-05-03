
import org.junit.Test;
import static org.junit.Assert.*;

import data.ContinuousAttribute;

public class ContinuousAttributeTest {
	@Test
	public void testGetScaledValue() {
		ContinuousAttribute attribute = new ContinuousAttribute(
			"attr", 0,
			-128, 256
		);

		double bot = attribute.getScaledValue(-128.0);
		assert(Double.compare(bot, 0.0) == 0);

		double mid = attribute.getScaledValue(50.0);
		assert(Double.compare(mid, 0.4635416666666667) == 0);

		double top = attribute.getScaledValue(256.0);
		assert(Double.compare(top, 1.0) == 0);
	}
}

