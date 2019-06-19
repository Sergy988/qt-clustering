
package server;

import java.util.List;
import java.util.LinkedList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
						learnFromDBTable();
						break;
					case 2:
						storeClustersInFile();
						break;
					case 3:
						learnFromFile();
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
	 * Learn from the database table.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 */
	private void learnFromDBTable()
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

		if (!result.equals("OK")) {
			return;
		}

		ClusterSet clusterSet = qt.getClusterSet();
		outStream.writeObject(clusterSet.toString(data));

		try {
			pca.project(data);
		} catch (StatisticException e) {
			result = e.toString();
		}

		outStream.writeObject(result);

		if (!result.equals("OK")) {
			return;
		}

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
	 * Store the clusters in file.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 */
	private void storeClustersInFile()
		throws IOException, ClassNotFoundException {
		String result = "OK";

		ClusterSet clusterSet = qt.getClusterSet();

		String fileName = clusterSetFileName(
			data.getTableName(), qt.getRadius()
		);

		try {
			FileOutputStream fileStream = new FileOutputStream(fileName);
			ObjectOutputStream objectStream = new ObjectOutputStream(
				fileStream
			);

			objectStream.writeObject(clusterSet);

			for (Cluster cluster : clusterSet) {
				int index = cluster.getId();

				double[] sample = new double[] {
					pca.get(index, 0),
					pca.get(index, 1),
					pca.get(index, 2)
				};

				objectStream.writeObject(sample);
			}
		} catch (IOException e) {
			result = e.toString();
		}

		outStream.writeObject(result);
	}

	/**
	 * Learn from file.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 */
	private void learnFromFile()
		throws IOException, ClassNotFoundException {
		String result = "OK";
		String tableName = (String) inStream.readObject();
		double radius = (double) inStream.readObject();

		ClusterSet clusterSet = null;
		String fileName = clusterSetFileName(tableName, radius);

		ObjectInputStream objectStream = null;

		try {
			FileInputStream fileStream = new FileInputStream(fileName);
			objectStream = new ObjectInputStream(fileStream);
			clusterSet = (ClusterSet) objectStream.readObject();
		} catch (IOException e) {
			result = e.toString();
		} catch (ClassNotFoundException e) {
			result = e.toString();
		}

		outStream.writeObject(result);

		if (!result.equals("OK")) {
			return;
		}

		outStream.writeObject(clusterSet.toString());

		try {
			for (Cluster cluster : clusterSet) {
				double[] sample = (double[]) objectStream.readObject();
				outStream.writeObject(sample);
			}

			outStream.writeObject(null);
		} catch (IOException e) {
			result = e.toString();
		} catch (ClassNotFoundException e) {
			result = e.toString();
		}

		outStream.writeObject(result);
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

