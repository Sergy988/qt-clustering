
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import data.Data;
import java.sql.SQLException;
import database.NoValueException;
import database.EmptySetException;
import database.DatabaseConnectionException;

/**
 * Data class tester.
 */
public class DataTest {

	/**
	 * Test the toString() method.
	 */
	@Test
	public void testToString() {
		Data data = null;

		try {
			data = new Data("test");
		} catch (ClassNotFoundException
			| SQLException
			| NoValueException
			| DatabaseConnectionException
			| EmptySetException e) {
			fail(e.toString());
		}

		String string = data.toString();

		assertEquals(string,
			  "discrete continuous "
			+ "\n1. A 1.0 "
			+ "\n2. B 1.5 "
			+ "\n3. C 2.0 ");
	}

	/**
	 * Test the SQL exception.
	 * @throws SQLException Thrown when the table was not found
	 */
	@Test(expected = SQLException.class)
	public void testDataSQLException() throws SQLException {
		try {
			Data data = new Data("table that doesn't exist");
		} catch (ClassNotFoundException
			| NoValueException
			| DatabaseConnectionException
			| EmptySetException e) {
			fail(e.toString());
		}
	}
}

