
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The database access class.
 */
public class DbAccess {

	/**
	 * The driver class name.
	 */
	private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

	/**
	 * The DBMS name.
	 */
	private static final String DBMS = "jdbc:mysql";

	/**
	 * The server identifier.
	 */
	private static final String SERVER = "localhost";

	/**
	 * The name of the database.
	 */
	private static final String DATABASE = "MapDB";

	/**
	 * The port of the connection.
	 */
	private static final String PORT = "3306";

	/**
	 * The user id used by the connection to the database.
	 */
	private static final String USER_ID = "MapUser";

	/**
	 * The password used by the connection to the database.
	 */
	private static final String PASSWORD = "map";

	/**
	 * The connection object.
	 */
	private Connection connection;

	/**
	 * Initialize the connection to the database.
	 * @throws ClassNotFoundException Thrown when jdbc driver wasn't loaded
	 * @throws DatabaseConnectionException Thrown when the connection
	 *                                     to the database failed
	 */
	public void initConnection()
		throws ClassNotFoundException, DatabaseConnectionException {

		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new DatabaseConnectionException(
				"Invalid conncector driver"
			);
		}

		String url = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
			+ "?&serverTimezone=UTC";

		try {
			connection = DriverManager.getConnection(url, USER_ID, PASSWORD);
		} catch (SQLException e) {
			throw new DatabaseConnectionException(
				"Failed to connect to the database: " + e.getMessage()
			);
		}
	}

	/**
	 * Get the connection.
	 * @return The connection object
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Close the connection.
	 * @throws SQLException Thrown when an sql error occurs
	 */
	public void closeConnection() throws SQLException {
		connection.close();
	}
}

