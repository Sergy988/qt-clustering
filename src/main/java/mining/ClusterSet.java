
/**
 * A set of clusters.
 */
public class ClusterSet {

	/**
	 * The clusters.
	 */
	private Cluster clusters[] = new Cluster[0];

	/**
	 * Add a new cluster to the cluster set.
	 * @param c The cluster to add
	 */
	public void add(Cluster c) {
		Cluster tmp[] = new Cluster[clusters.length + 1];

		for(int i = 0; i < clusters.length; i++)
			tmp[i] = clusters[i];

		tmp[clusters.length] = c;
		clusters = tmp;
	}

	/**
	 * Convert the cluster set to a String.
	 * @return The textual rappresentation of the cluster set
	 */
	public String toString() {
		String str = "";

		for(int i = 0; i < clusters.length; i++) {
			if(clusters[i] != null) {
				str += i + ": " + clusters[i].toString() + "\n";
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

		for(int i = 0; i < clusters.length; i++) {
			if(clusters[i] != null) {
				str += i + ": " + clusters[i].toString(data) + "\n";
			}
		}

		return str;
	}
}
