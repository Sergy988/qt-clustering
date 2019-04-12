
/**
 * The Quality Threshold miner class
 */
public class QTMiner {

	/**
	 * The set of clusters
	 */
	private ClusterSet clusterSet;

	/**
	 * The radius to use
	 */
	private double radius;

	/**
	 * Instantiate a Quality Threshold miner
	 * @param radius The radius to use
	 */
	public QTMiner(double radius) {
		this.radius = radius;
	}

	/**
	 * Get the cluster set
	 * @return The cluster set
	 */
	public ClusterSet getClusterSet() {
		return clusterSet;
	}

	/**
	 * Execute the algorithm
	 * @param data The source data
	 * @return The number of clusters
	 */
	public int compute(Data data) {
		int numClusters = 0;
		boolean isClustered[] = new boolean[data.getNumberOfExamples()];

		for(int i = 0; i < isClustered.length; i++)
			isClustered[i] = false;

		int countClustered = 0;

		while(countClustered != data.getNumberOfExamples()) {
			Cluster cluster = buildCandidateCluster(data, isClustered);
			clusterSet.add(cluster);
			numClusters++;

			int clusteredTupleId[] = cluster.iterator();

			for(int i = 0; i < clusteredTupleId.length; i++) {
				isClustered[clusteredTupleId[i]] = true;
			}

			countClustered += cluster.getSize();
		}

		return countClustered;
	}

	/**
	 * Build the candidate cluster
	 * @param data The source data
	 * @param isClustered The lookup table of removed examples
	 * @return The candidate cluster
	 */
	private Cluster buildCandidateCluster(Data data, boolean isClustered[]) {
		// TODO
	}
}

