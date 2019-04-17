
import org.junit.Test;
import static org.junit.Assert.*;

public class ContinuousAttributeTest {
	@Test
	public void getScaledValue() {
		ContinuousAttribute attribute = new ContinuousAttribute(
			"attr", 0,
			-128, 256
		);

		double value = attribute.getScaledValue(50.0);

		assert(Double.compare(value, 0.4635416666666667) == 0);
	}
}

