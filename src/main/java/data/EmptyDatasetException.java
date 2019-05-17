
package data;

/**
 * An exception that occurs when the dataset has no entries.
 */
public class EmptyDatasetException extends Exception {
	/**
	 * Instantiate an EmptyDatasetException.
	 * @param msg The helpful message
	 */
	public EmptyDatasetException(String msg) {
		super(msg);
	}
}
