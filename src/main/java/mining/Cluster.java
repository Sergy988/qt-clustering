
package mining;

import data.Data;
import data.Tuple;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.io.Serializable;

/**
 * The Cluster class.
 */
public class Cluster
	implements Iterable<Integer>, Comparable<Cluster>, Serializable {
	/**
	 * The centroid tuple of the cluster.
	 */
	private Tuple centroid;

	/**
	 * The ids of the other tuples in the cluster.
	 */
	private Set<Integer> clusteredData = new HashSet<Integer>();

	/**
	 * Instantiate a Cluster.
	 * @param centroid The centroid tuple
	 */
	public Cluster(Tuple centroid) {
		this.centroid = centroid;
	}

	/**
	 * Get the centroid tuple.
	 * @return The centroid tuple
	 */
	public Tuple getCentroid() {
		return centroid;
	}

	/**
	 * Add a tuple id to the cluster.
	 * @param id The tuple id
	 * @return False if clustered data contains the id, otherwise true
	 */
	public boolean addData(int id) {
		return clusteredData.add(id);
	}

	/**
	 * Check if the cluster contains a tuple id.
	 * @param id The tuple id
	 * @return true if the cluster contains the tuple specified by the id
	 */
	public boolean contain(int id) {
		return clusteredData.contains(id);
	}

	/**
	 * Remove a tuple id from the cluster.
	 * @param id The tuple id to remove
	 */
	public void removeTuple(int id) {
		clusteredData.remove(id);
	}

	/**
	 * Get the size of the cluster.
	 * @return The size of the cluster
	 */
	public int getSize() {
		return clusteredData.size();
	}

	/**
	 * Get an iterator of clustered tuple ids.
	 * @return An iterator over the clustered tuple ids
	 */
	public Iterator<Integer> iterator() {
		return clusteredData.iterator();
	}

	/**
	 * Compare to another cluster.
	 * @param other The other cluster
	 * @return 1 If this has more data, 0 if equal, -1 otherwise
	 */
	public int compareTo(final Cluster other) {
		if (getSize() <= other.getSize()) {
			return -1;
		} else {
			return 1;
		}
	}

	/**
	 * Convert a Cluster to a String.
	 * @return The textual rappresentation of the Cluster
	 */
	public String toString() {
		String str = "Centroid = ( ";

		for (int i = 0; i < centroid.getLength(); i++) {
			str += centroid.get(i) + " ";
		}

		str += ")";

		return str;
	}

	/**
	 * Convert a Cluster to a String based on source data.
	 * @param data The source data
	 * @return The textual rappresentation of the Cluster
	 */
	public String toString(Data data) {
		String str = "Centroid = ( ";

		for (int i = 0; i < centroid.getLength(); i++) {
			str += centroid.get(i) + " ";
		}

		str += ")\nExamples:\n";

		for (Integer i : clusteredData) {
			str += "[";
			for (int j = 0; j < data.getNumberOfExplanatoryAttributes(); j++) {
				str += data.getValue(i, j) + " ";
			}

			str += "] dist=";
			str += getCentroid().getDistance(data.getItemSet(i)) + "\n";
		}

		str += "AvgDistance=";
		str += getCentroid().avgDistance(data, clusteredData) + "\n";

		return str;
	}
}
