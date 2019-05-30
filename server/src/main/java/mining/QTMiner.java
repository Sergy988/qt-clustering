
package mining;

import data.Data;
import data.Tuple;
import data.EmptyDatasetException;
import java.util.Arrays;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
	 * @param radius The radius
	 */
	public QTMiner(double radius) {
		this.radius = radius;
		clusterSet = new ClusterSet();
	}

	/**
	 * Instantiate a Quality Threshold miner.
	 * @param filename The filename of the cluster set file
	 * @throws FileNotFoundException Thrown when an error occurred
	 *                               opening the file
	 * @throws IOException Thrown when an input/output error occurs
	 * @throws ClassNotFoundException Thrown when the cast from
	 *                                object to ClusterSet fails
	 */
	public QTMiner(String filename)
		throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream(filename);
		ObjectInputStream objectStream = new ObjectInputStream(stream);

		clusterSet = (ClusterSet) objectStream.readObject();
	}

	/**
	 * Get the radius.
	 * @return The radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Get the cluster set.
	 * @return The cluster set
	 */
	public ClusterSet getClusterSet() {
		return clusterSet;
	}

	/**
	 * Save the cluster set data to file.
	 * @param filename The filename of the cluster set file
	 * @throws FileNotFoundException Thrown when an error occurred
	 *                               opening the file
	 * @throws IOException Thrown when an input/output error occurs
	 */
	public void save(String filename)
		throws FileNotFoundException, IOException {
		FileOutputStream stream = new FileOutputStream(filename);
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);

		objectStream.writeObject(clusterSet);
	}

	/**
	 * Execute the algorithm.
	 * @param data The source data
	 * @return The number of clusters
	 * @throws ClusteringRadiusException When the result is a single cluster
	 * @throws EmptyDatasetException When data has no entries
	 */
	public int compute(Data data) throws
		ClusteringRadiusException, EmptyDatasetException {
		if (data.getNumberOfExamples() == 0) {
			throw new EmptyDatasetException("dataset has not entries!");
		}

		int numClusters = 0;
		boolean isClustered[] = new boolean[data.getNumberOfExamples()];

		Arrays.fill(isClustered, false);

		int countClustered = 0;

		while (countClustered != data.getNumberOfExamples()) {
			Cluster cluster = buildCandidateCluster(data, isClustered);
			clusterSet.add(cluster);
			numClusters++;

			for (Integer i : cluster) {
				isClustered[i] = true;
			}

			countClustered += cluster.getSize();
		}

		if (numClusters == 1) {
			throw new ClusteringRadiusException("radius is too large!");
		}

		return numClusters;
	}

	/**
	 * Convert the miner to a stirng.
	 * @return The string rappresentation
	 */
	public String toString() {
		return clusterSet.toString();
	}

	/**
	 * Build the candidate cluster.
	 * @param data The source data
	 * @param isClustered The lookup table of removed examples
	 * @return The candidate cluster
	 */
	private Cluster buildCandidateCluster(Data data, boolean isClustered[]) {
		Cluster candidate = null;

		for (int i = 0; i < data.getNumberOfExamples(); i++) {
			if (isClustered[i]) {
				continue;
			}

			Tuple centroid = data.getTuple(i);
			Cluster cluster = new Cluster(centroid);

			for (int j = 0; j < data.getNumberOfExamples(); j++) {
				if (isClustered[j]) {
					continue;
				}

				Tuple other = data.getTuple(j);

				if (centroid.getDistance(other) <= radius) {
					cluster.addData(j);
				}
			}

			if (candidate == null || candidate.getSize() < cluster.getSize()) {
				candidate = cluster;
			}
		}

		return candidate;
	}
}

