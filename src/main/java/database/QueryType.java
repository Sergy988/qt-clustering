
package database;

/**
 * The aggregation type (MIN or MAX).
 */
public enum QueryType {
	/**
	 * Append 'min' after 'select' in the query.
	 */
	MIN,

	/**
	 * Append 'max' after 'select' in the query.
	 */
	MAX
}
