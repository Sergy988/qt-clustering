
package mining;

import data.Data;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

/**
 * A set of clusters.
 */
public class ClusterSet implements Iterable<Cluster> {

	/**
	 * The clusters.
	 */
	private Set<Cluster> clusters = new TreeSet<Cluster>();

	/**
	 * Add a new cluster to the cluster set.
	 * @param c The cluster to add
	 */
	public void add(Cluster c) {
		clusters.add(c);
	}

	/**
	 * Get the iterator to the clusters.
	 * @return An iterator to the clusters.
	 */
	public Iterator<Cluster> iterator() {
		return clusters.iterator();
	}

	/**
	 * Convert the cluster set to a String.
	 * @return The textual rappresentation of the cluster set
	 */
	public String toString() {
		String str = "";

		int i = 0;

		for (Cluster c : clusters) {
			if (c != null) {
				str += i + ": " + c.toString() + "\n";
				i++;
			}
		}

		return str;
	}

	/**
	 * Convert the cluster set to a string based on source data.
	 * @param data The source data
	 * @return The textual rappresentation of the cluster set
	 */
	public String toString(Data data) {
		String str = "";

		int i = 0;

		for (Cluster c : clusters) {
			if (c != null) {
				str += i + ": " + c.toString(data) + "\n";
				i++;
			}
		}

		return str;
	}
}
