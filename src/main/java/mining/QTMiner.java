
/**
 * The Quality Threshold miner class.
 */
public class QTMiner {

	/**
	 * The radius to use.
	 */
	private double radius;

	/**
	 * The set of clusters.
	 */
	private ClusterSet clusterSet;

	/**
	 * Instantiate a Quality Threshold miner.
	 * @param radius The radius to use
	 */
	public QTMiner(double radius) {
		this.radius = radius;
		this.clusterSet = new ClusterSet();
	}

	/**
	 * Get the cluster set.
	 * @return The cluster set
	 */
	public ClusterSet getClusterSet() {
		return clusterSet;
	}

	/**
	 * Execute the algorithm.
	 * @param data The source data
	 * @return The number of clusters
	 * @throws ClusteringRadiusException When the result is a single cluster
	 */
	public int compute(Data data) throws ClusteringRadiusException {
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

		if(numClusters == 1)
			throw new ClusteringRadiusException("radius is too small!");

		return numClusters;
	}

	/**
	 * Build the candidate cluster.
	 * @param data The source data
	 * @param isClustered The lookup table of removed examples
	 * @return The candidate cluster
	 */
	private Cluster buildCandidateCluster(Data data, boolean isClustered[]) {
		Cluster candidate = null;

		for(int i = 0; i < data.getNumberOfExamples(); i++) {
			if(isClustered[i]) {
				continue;
			}

			Tuple centroid = data.getItemSet(i);
			Cluster cluster = new Cluster(centroid);

			for(int j = 0; j < data.getNumberOfExamples(); j++) {
				if(isClustered[j]) {
					continue;
				}

				Tuple other = data.getItemSet(j);

				if(centroid.getDistance(other) <= radius) {
					cluster.addData(j);
				}
			}

			if(candidate == null || candidate.getSize() < cluster.getSize()) {
				candidate = cluster;
			}
		}

		return candidate;
	}
}

