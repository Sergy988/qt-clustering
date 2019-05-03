
package mining;

/**
 * An exception that occurs when the result of the clustering
 * algorithm is one single cluster.
 * This occurs when the radius is too large.
 */
public class ClusteringRadiusException extends Exception {
	/**
	 * Instantiate a ClusteringRadiusException.
	 * @param msg The helpful message
	 */
	public ClusteringRadiusException(String msg) {
		super(msg);
	}
}
