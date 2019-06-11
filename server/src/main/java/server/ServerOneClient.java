
import java.util.List;
import java.util.LinkedList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;

import java.net.Socket;
import java.net.InetAddress;
import java.net.SocketException;

import java.sql.SQLException;

import stats.StatisticException;

import data.Data;
import data.PCAnalyser;
import data.EmptyDatasetException;

import mining.QTMiner;
import mining.Cluster;
import mining.ClusterSet;
import mining.ClusteringRadiusException;

import database.NoValueException;
import database.EmptySetException;
import database.DatabaseConnectionException;

/**
 * Server one client thread class.
 */
class ServerOneClient extends Thread {

	/**
	 * The QT miner object.
	 */
	private QTMiner qt;

	/**
	 * The data read from a database table.
	 */
	private Data data;

	/**
	 * The principal component analyser.
	 */
	private PCAnalyser pca;

	/**
	 * The connection socket.
	 */
	private Socket socket;

	/**
	 * The object input stream to the client.
	 */
	private ObjectInputStream inStream;

	/**
	 * The object output stream to the client.
	 */
	private ObjectOutputStream outStream;

	/**
	 * Construct a ServerOneClient.
	 * @param socket The client socket
	 * @throws IOException Thrown when a I/O exception occurs
	 */
	ServerOneClient(Socket socket) throws IOException {
		this.socket = socket;

		inStream = new ObjectInputStream(socket.getInputStream());
		outStream = new ObjectOutputStream(socket.getOutputStream());

		pca = new PCAnalyser(3);

		start();
	}

	/**
	 * Run the thread.
	 */
	@Override
	public void run() {
		try {
			while (true) {
				Integer operation = (Integer) inStream.readObject();

				switch (operation) {
					case 0:
						storeTableFromDB();
						break;
					case 1:
						learningFromDBTable();
						break;
					case 2:
						storeClusterInFile();
						break;
					case 3:
						learningFromServerFile();
						break;
					case 4:
						sendProjectedClusters();
						break;
					case 5:
						sendProjectedCentroids();
						break;
					default:
						break;
				}
			}
		} catch (IOException e) {
			System.err.println(socket + " disconnected");
		} catch (ClassNotFoundException e) {
			System.err.println(socket + ": " + e);
		} finally {
			close();
		}
	}

	/**
	 * Close the server one client thread.
	 */
	private void close() {
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Store the table from database.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 */
	private void storeTableFromDB()
		throws IOException, ClassNotFoundException {
		String result = "OK";
		String tableName = (String) inStream.readObject();

		try {
			data = new Data(tableName);
		} catch (ClassNotFoundException e) {
			result = e.toString();
		} catch (SQLException e) {
			result = e.toString();
		} catch (NoValueException e) {
			result = e.toString();
		} catch (DatabaseConnectionException e) {
			result = e.toString();
		} catch (EmptySetException e) {
			result = e.toString();
		}

		outStream.writeObject(result);
	}

	/**
	 * Learning from the database table.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 */
	private void learningFromDBTable()
		throws IOException, ClassNotFoundException {
		String result = "OK";
		double radius = (double) inStream.readObject();

		qt = new QTMiner(radius);

		try {
			qt.compute(data);
		} catch (ClusteringRadiusException e) {
			result = e.toString();
		} catch (EmptyDatasetException e) {
			result = e.toString();
		}

		outStream.writeObject(result);

		if (result == "OK") {
			outStream.writeObject(qt.getClusterSet().toString(data));
		}
	}

	/**
	 * Store the cluster in file.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 */
	private void storeClusterInFile()
		throws IOException, ClassNotFoundException {
		String result = "OK";

		try {
			qt.save(clusterSetFileName(data.getTableName(), qt.getRadius()));
		} catch (IOException e) {
			result = e.toString();
		}

		outStream.writeObject(result);
	}

	/**
	 * Retrieve the server file.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 */
	private void learningFromServerFile()
		throws IOException, ClassNotFoundException {
		String result = "OK";
		String tableName = (String) inStream.readObject();
		double radius = (double) inStream.readObject();

		try {
			qt = new QTMiner(clusterSetFileName(tableName, radius));
		} catch (IOException e) {
			result = e.toString();
		} catch (ClassNotFoundException e) {
			result = e.toString();
		}

		outStream.writeObject(result);

		if (result == "OK") {
			outStream.writeObject(qt.getClusterSet().toString());
		}
	}

	/**
	 * Send the projected clusters set samples to the client.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 */
	private void sendProjectedClusters()
		throws IOException, ClassNotFoundException {
		String result = "OK";

		try {
			pca.project(data);
		} catch (StatisticException e) {
			result = e.toString();
		}

		outStream.writeObject(result);

		if (result != "OK") {
			return;
		}

		ClusterSet clusterSet = qt.getClusterSet();

		for (Cluster cluster : clusterSet) {
			List<double[]> samples = new LinkedList<double[]>();

			for (Integer index : cluster) {
				samples.add(
					new double[]{
						pca.get(index, 0),
						pca.get(index, 1),
						pca.get(index, 2)
					}
				);
			}

			outStream.writeObject(samples);
		}

		outStream.writeObject(null);
	}

	/**
	 * Send the projected centroids samples to the client.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 */
	private void sendProjectedCentroids()
		throws IOException, ClassNotFoundException {
		ClusterSet clusterSet = qt.getClusterSet();

		for (Cluster cluster : clusterSet) {
			int index = cluster.getId();

			double[] sample = new double[] {
				pca.get(index, 0),
				pca.get(index, 1),
				pca.get(index, 2)
			};

			outStream.writeObject(sample);
		}

		outStream.writeObject(null);
	}

	/**
	 * Convert a table name and a radius to a filename.
	 * @param tableName The name of the table
	 * @param radius The radius
	 * @return The filename of the cluster set dump
	 */
	private static String clusterSetFileName(String tableName, double radius) {
		return tableName + "_" + radius + ".dmp";
	}
}

