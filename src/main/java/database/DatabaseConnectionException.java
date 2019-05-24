
package database;

/**
 * The database connection exception class.
 */
public class DatabaseConnectionException extends Exception {

	/**
	 * Instantiate a DatabaseConnectionException.
	 * @param msg The error message
	 */
	public DatabaseConnectionException(String msg) {
		super(msg);
	}
}
