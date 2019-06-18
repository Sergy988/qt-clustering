
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
	 * Test the data constructor.
	 */
	@Test
	public void testData() {
		try {
			Data data = new Data("playtennis");
		} catch (ClassNotFoundException
			| SQLException
			| NoValueException
			| DatabaseConnectionException
			| EmptySetException e) {
			fail(e.toString());
		}
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

