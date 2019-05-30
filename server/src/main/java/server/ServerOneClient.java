
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;

import java.net.Socket;
import java.net.InetAddress;
import java.net.SocketException;

import java.sql.SQLException;

import data.Data;
import data.EmptyDatasetException;

import mining.QTMiner;
import mining.ClusteringRadiusException;

import database.NoValueException;
import database.EmptySetException;
import database.DatabaseConnectionException;

/**
 * Server one client thread class.
 */
public class ServerOneClient extends Thread {

	/**
	 * The QT miner object.
	 */
	private QTMiner qt;

	/**
	 * The data read from a database table.
	 */
	private Data data;

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
	public ServerOneClient(Socket socket) throws IOException {
		this.socket = socket;

		inStream = new ObjectInputStream(socket.getInputStream());
		outStream = new ObjectOutputStream(socket.getOutputStream());

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
					default:
						break;
				}
			}
		} catch (IOException e) {
			System.err.println(socket + " disconnected");
		} catch (ClassNotFoundException e) {
			System.err.println(e);
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
			result = e.getMessage();
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			result = e.getMessage();
			System.err.println(e.getMessage());
		} catch (NoValueException e) {
			result = e.getMessage();
			System.err.println(e.getMessage());
		} catch (DatabaseConnectionException e) {
			result = e.getMessage();
			System.err.println(e.getMessage());
		} catch (EmptySetException e) {
			result = e.getMessage();
			System.err.println(e.getMessage());
		}

		if (result != null) {
			outStream.writeObject(result);
		} else {
			outStream.writeObject("Generic error");
		}
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

		int numClusters = 0;

		try {
			numClusters = qt.compute(data);
		} catch (ClusteringRadiusException e) {
			result = e.getMessage();
			System.err.println(e.getMessage());
		} catch (EmptyDatasetException e) {
			result = e.getMessage();
			System.err.println(e.getMessage());
		}

		outStream.writeObject(result);

		outStream.writeObject(numClusters);
		outStream.writeObject(qt.getClusterSet().toString(data));
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
			qt.save(data.getTableName() + "_" + qt.getRadius() + ".dmp");
		} catch (IOException e) {
			result = e.getMessage();
			System.err.println(e.getMessage());
		}

		outStream.writeObject(result);
	}

	/**
	 * Retrieve the server file.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 */
	private void learningFromServerFile() {
		// TODO
	}
}

