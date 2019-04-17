
/**
 * The Cluster class
 */
class Cluster {

	/**
	 * The centroid tuple of the cluster
	 */
	private Tuple centroid;

	/**
	 * The ids of the other tuple in the cluster
	 */
	private ArraySet clusteredData;

	/**
	 * Instantiate a Cluster
	 * @param centroid The centroid tuple
	 */
	public Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new ArraySet();
	}

	/**
	 * Get the centroid tuple
	 * @return The centroid tuple
	 */
	public Tuple getCentroid() {
		return centroid;
	}

	/**
	 * Add a tuple id to the cluster
	 * @param id The tuple id
	 * @return False if clustered data contains the id, otherwise true
	 */
	public boolean addData(int id) {
		return clusteredData.add(id);
	}

	/**
	 * Check if the cluster contains a tuple id
	 * @param id The tuple id
	 * @return true if the cluster contains the tuple specified by the id
	 */
	public boolean contain(int id) {
		return clusteredData.get(id);
	}

	/**
	 * Remove a tuple id from the cluster
	 * @param id The tuple id to remove
	 */
	public void removeTuple(int id) {
		clusteredData.delete(id);
	}

	/**
	 * Get the size of the cluster
	 * @return The size of the cluster
	 */
	public int getSize() {
		return clusteredData.size();
	}

	/**
	 * Get an array of clustered tuple ids
	 * @return An array that contains the clustered tuple ids
	 */
	public int[] iterator() {
		return clusteredData.toArray();
	}

	/**
	 * Convert a Cluster to a String
	 * @return The textual rappresentation of the Cluster
	 */
	public String toString() {
		String str = "Centroid = ( ";

		for(int i = 0; i < centroid.getLength(); i++) {
			str += centroid.get(i) + " ";
		}

		str += ")";

		return str;
	}

	/**
	 * Convert a Cluster to a String based on source data
	 * @param data The source data
	 * @return The textual rappresentation of the Cluster
	 */
	public String toString(Data data) {
		String str= "Centroid = ( ";

		for(int i = 0; i < centroid.getLength(); i++) {
			str += centroid.get(i) + " ";
		}

		str += ")\nExamples:\n";

		int array[] = clusteredData.toArray();

		for(int i = 0; i < array.length; i++) {
			str += "[";
			for(int j = 0; j < data.getNumberOfExplanatoryAttributes(); j++) {
				str += data.getValue(array[i], j) + " ";
			}

			str += "] dist=";
			str += getCentroid().getDistance(data.getItemSet(array[i])) + "\n";
		}
		str += "AvgDistance=" + getCentroid().avgDistance(data, array) + "\n";

		return str;
	}
}
