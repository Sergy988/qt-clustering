
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;

import java.net.Socket;
import java.net.InetAddress;
import java.net.SocketException;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.layout.GridPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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

		GridPane connectionGrid = new GridPane();
		connectionGrid.setHgap(8);
		connectionGrid.setVgap(8);

		connectionGrid.add(new Label("Server Connection"), 0, 0);

		connectionGrid.add(new Label("Server ip:"), 0, 1);
		TextField ipTextField = new TextField("localhost");
		ipTextField.setPrefWidth(144);
		connectionGrid.add(ipTextField, 1, 1);

		connectionGrid.add(new Label("Server port:"), 0, 2);
		TextField portTextField = new TextField("8080");
		connectionGrid.add(portTextField, 1, 2);

		Button connectButton = new Button("Connect");
		connectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int port = 0;
				String ip = ipTextField.getText();

				try {
					port = Integer.parseInt(portTextField.getText());

					if (port < 0 || port > 65545) {
						throw new NumberFormatException("Invalid port");
					}
				} catch (NumberFormatException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Invalid port format");
					alert.setContentText(e.getMessage());
					alert.setResizable(true);
					alert.showAndWait();
					return;
				}

				try {
					connect(ip, port);
				} catch (IOException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Connection to the server failed");
					alert.setContentText(e.getMessage());
					alert.setResizable(true);
					alert.showAndWait();
					return;
				}

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Connection to the server enstablished");
				alert.showAndWait();
			}
		});
		connectionGrid.add(connectButton, 0, 3);

		GridPane learningGrid = new GridPane();
		learningGrid.setHgap(8);
		learningGrid.setVgap(8);

		learningGrid.add(new Label("Data mining"), 0, 0);

		learningGrid.add(new Label("Table name:"), 0, 1);
		TextField tableNameTextField = new TextField("table");
		learningGrid.add(tableNameTextField, 1, 1);

		learningGrid.add(new Label("Radius:"), 0, 2);
		TextField radiusTextField = new TextField("1.0");
		learningGrid.add(radiusTextField, 1, 2);

		GridPane resultGrid = new GridPane();
		resultGrid.setHgap(8);
		resultGrid.setVgap(8);

		resultGrid.add(new Label("Result:"), 0, 0);
		TextArea resultTextArea = new TextArea();
		resultTextArea.setPrefColumnCount(48);
		resultTextArea.setPrefRowCount(32);
		resultGrid.add(resultTextArea, 0, 1);

		Button learnFromDataButton = new Button("Learn from database");
		learnFromDataButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!connected) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("You're not connected to the server");
					alert.showAndWait();
					return;
				}

				double radius = 0.0;
				String tableName = tableNameTextField.getText();

				try {
					radius = Double.parseDouble(radiusTextField.getText());
				} catch(NumberFormatException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Invalid radius format");
					alert.setContentText(e.getMessage());
					alert.setResizable(true);
					alert.showAndWait();
					return;
				}

				String clusterSet = "";

				try {
					clusterSet = learnFromData(tableName, radius);
				} catch(IOException
					| ClassNotFoundException 
					| ServerException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Learn from data failed");
					alert.setContentText(e.getMessage());
					alert.setResizable(true);
					alert.showAndWait();
					return;
				}

				resultTextArea.setText(clusterSet);
			}
		});
		learningGrid.add(learnFromDataButton, 0, 3);

		Button learningFromFileButton = new Button("Learn from file");
		learningFromFileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!connected) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("You're not connected to the server");
					alert.showAndWait();
					return;
				}

				double radius = 0.0;
				String tableName = tableNameTextField.getText();

				try {
					radius = Double.parseDouble(radiusTextField.getText());
				} catch(NumberFormatException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Invalid radius format");
					alert.setContentText(e.getMessage());
					alert.setResizable(true);
					alert.showAndWait();
					return;
				}

				String centroids = "";

				try {
					centroids = learnFromFile(tableName, radius);
				} catch(IOException
					| ClassNotFoundException 
					| ServerException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Learn from data failed");
					alert.setContentText(e.getMessage());
					alert.setResizable(true);
					alert.showAndWait();
					return;
				}

				resultTextArea.setText(centroids);
			}
		});
		learningGrid.add(learningFromFileButton, 1, 3);

		GridPane grid = new GridPane();
		grid.setHgap(32);
		grid.setVgap(32);
		grid.setPadding(new Insets(24));
		grid.add(connectionGrid, 0, 0);
		grid.add(learningGrid, 1, 0);
		grid.add(resultGrid, 0, 1);

		Scene scene = new Scene(grid, WIN_WIDTH, WIN_HEIGHT);

		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Connect to the server.
	 * @param ip The ip of the server
	 * @param port The connection port
	 * @throws IOException Thrown when the connection failed
	 */
	private void connect(String ip, int port) throws IOException {
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
	private void disconnect() throws IOException {
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
	 */
	private String learnFromData(String tableName, double radius)
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
	 * @throws SocketException Thrown when a socket error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 * @throws ServerException Thrown when the server result is invalid
	 */
	private void storeTableFromDB(String tableName)
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
	private String learnFromDBTable(double radius)
		throws IOException,
		       ClassNotFoundException,
		       ServerException {
		outStream.writeObject(1);

		outStream.writeObject(radius);

		String result = (String) inStream.readObject();

		if (!result.equals("OK")) {
			throw new ServerException(result);
		}

		// Just for server sync
		inStream.readObject();

		return (String) inStream.readObject();
	}

	/**
	 * Store the cluster in file.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 * @throws ServerException Thrown when the server result is invalid
	 */
	private void storeClusterInFile()
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
	private String learnFromFile(String tableName, double radius)
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

