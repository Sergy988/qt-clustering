
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

/**
 * MultiServer class tester.
 */
public class MultiServerTest {

	/**
	 * Test the MultiServer constructor (check for conflicting ports exception).
	 */
	@Test
	public void testMultiServer() {
		MultiServer first = null;

		try {
			first = new MultiServer(8080);
		} catch (IOException e) {
			fail(e.toString());
		}

		try {
			MultiServer second = new MultiServer(8080);
			second.close();
			fail();
		} catch (IOException e) {
			// nothing
		} finally {
			first.close();
		}
	}
}

