
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;

import java.net.Socket;
import java.net.InetAddress;
import java.net.SocketException;

import javafx.geometry.Insets;

import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import javafx.application.Application;

/**
 * The main application class.
 */
public class ClientMain extends Application {

	/**
	 * The width of the window.
	 */
	private static final int WIN_WIDTH = 1024;

	/**
	 * The height of the window.
	 */
	private static final int WIN_HEIGHT = 768;

	/**
	 * The connected flag.
	 */
	private boolean connected;

	/**
	 * The connection socket.
	 */
	private Socket socket;

	/**
	 * The input object stream to the client.
	 */
	private ObjectInputStream inStream;

	/**
	 * The output object stream to the server.
	 */
	private ObjectOutputStream outStream;

	/**
	 * The entry point.
	 * @param args The arguments of the program
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Start the client application.
	 * @param stage The javafx stage
	 */
	@Override
	public void start(Stage stage) {
		stage.setTitle("QT client");

		GridPane grid = new GridPane();
		grid.setHgap(32);
		grid.setVgap(32);
		grid.setPadding(new Insets(24));

		ResultUI resultUI = new ResultUI(this);
		ConnectionUI connectionUI = new ConnectionUI(this);
		LearningUI learningUI = new LearningUI(this, resultUI);

		grid.add(connectionUI, 0, 0);
		grid.add(learningUI, 1, 0);
		grid.add(resultUI, 0, 1);

		Scene scene = new Scene(grid, WIN_WIDTH, WIN_HEIGHT);

		stage.setOnCloseRequest(event -> {
			try {
				disconnect();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});

		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Get the connection status of the client.
	 * @return true if the connection is enstablished, false otherwise
	 */
	boolean isConnected() {
		return connected;
	}

	/**
	 * Connect to the server.
	 * @param ip The ip of the server
	 * @param port The connection port
	 * @throws IOException Thrown when the connection failed
	 */
	void connect(String ip, int port) throws IOException {
		disconnect();

		InetAddress addr = InetAddress.getByName(ip);

		socket = new Socket(addr, port);

		outStream = new ObjectOutputStream(socket.getOutputStream());
		inStream = new ObjectInputStream(socket.getInputStream());

		connected = true;
	}

	/**
	 * Disconnect from the server.
	 * @throws IOException Thrown when an error occured closing the socket
	 */
	void disconnect() throws IOException {
		if (socket != null) {
			socket.close();
			socket = null;
			connected = false;
		}
	}

	/**
	 * Learn from data.
	 * @param tableName The name of the table
	 * @param radius The radius of the mining
	 * @return The cluster set string
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 * @throws ServerException Thrown when the server result is invalid
	 */
	String learnFromData(String tableName, double radius)
		throws IOException,
		       ClassNotFoundException,
		       ServerException {
		storeTableFromDB(tableName);

		String clusterSet = learnFromDBTable(radius);
		storeClusterInFile();

		return clusterSet;
	}

	/**
	 * Store the table from database.
	 * @param tableName The name of the table
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 * @throws ServerException Thrown when the server result is invalid
	 */
	void storeTableFromDB(String tableName)
		throws IOException,
		       ClassNotFoundException,
		       ServerException {
		outStream.writeObject(0);

		outStream.writeObject(tableName);

		String result = (String) inStream.readObject();

		if (!result.equals("OK")) {
			throw new ServerException(result);
		}
	}

	/**
	 * Learn from the database table.
	 * @param radius The radius of the mining
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 * @throws ServerException Thrown when the server result is invalid
	 * @return The cluster set string
	 */
	String learnFromDBTable(double radius)
		throws IOException,
		       ClassNotFoundException,
		       ServerException {
		outStream.writeObject(1);

		outStream.writeObject(radius);

		String result = (String) inStream.readObject();

		if (!result.equals("OK")) {
			throw new ServerException(result);
		}

		return (String) inStream.readObject();
	}

	/**
	 * Store the cluster in file.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 * @throws ServerException Thrown when the server result is invalid
	 */
	void storeClusterInFile()
		throws IOException,
		       ClassNotFoundException,
		       ServerException {
		outStream.writeObject(2);

		String result = (String) inStream.readObject();

		if (!result.equals("OK")) {
			throw new ServerException(result);
		}
	}

	/**
	 * Load a cluster set from file.
	 * @param tableName The name of the table
	 * @param radius The radius of the mining
	 * @return The centroids string
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 * @throws ServerException Thrown when the server result is invalid
	 */
	String learnFromFile(String tableName, double radius)
		throws IOException,
		       ClassNotFoundException,
		       ServerException {
		String centroids = "";

		outStream.writeObject(3);

		outStream.writeObject(tableName);

		outStream.writeObject(radius);

		String result = (String) inStream.readObject();

		if (!result.equals("OK")) {
			throw new ServerException(result);
		}

		centroids = (String) inStream.readObject();

		return centroids;
	}
}

