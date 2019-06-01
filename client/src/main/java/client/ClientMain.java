
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;

import java.net.Socket;
import java.net.InetAddress;
import java.net.SocketException;

import keyboardinput.Keyboard;

/**
 * The main application class.
 */
public class ClientMain {

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
		if (args.length < 2) {
			help();
			return;
		}

		String ip = args[0];
		int port = new Integer(args[1]).intValue();

		ClientMain main = null;

		try {
			main = new ClientMain(ip, port);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return;
		}

		while (true) {
			int menuAnswer = main.menu();

			switch (menuAnswer) {
				case 1:
					main.learningFromFile();
					break;
				case 2:
					main.learningFromData();
					break;
				default:
					System.out.println("Invalid option!");
					break;
			}

			System.out.print(
				"Would you choose another option from the menu? (y/n): "
			);

			if (Character.toLowerCase(Keyboard.readChar()) != 'y') {
				break;
			}
		}

		main.close();
	}

	/**
	 * Print the helpful message.
	 */
	private static void help() {
		System.out.println("usage:");
		System.out.println("qt-client {ip} {port}");
	}

	/**
	 * Construct a ClientMain.
	 * @param ip The ip address
	 * @param port The port number
	 * @throws IOException Thrown when the connection failed
	 */
	public ClientMain(String ip, int port) throws IOException {
		InetAddress addr = InetAddress.getByName(ip);
		System.out.println("Connecting to " + addr + " ...");

		socket = new Socket(addr, port);

		outStream = new ObjectOutputStream(socket.getOutputStream());
		inStream = new ObjectInputStream(socket.getInputStream());
	}

	/**
	 * Close the connection.
	 */
	private void close() {
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Print the menu and ask the user for a choice.
	 * @return The choice (`1` for load clusters from file or
	 *                     `2` for load data)
	 */
	private int menu() {
		int answer;

		do {
			System.out.println("(1) Load Clusters from File");
			System.out.println("(2) Load Data");
			System.out.print("(1/2): ");

			answer = Keyboard.readInt();
		} while (answer < 1 || answer > 2);

		return answer;
	}

	/**
	 * Load a cluster set from file.
	 */
	private void learningFromFile() {
		try {
			String centroids = learningFromServerFile();
			System.out.println(centroids);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (ServerException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Learning from data.
	 */
	private void learningFromData() {
		try {
			storeTableFromDB();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return;
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			return;
		} catch (ServerException e) {
			System.err.println(e.getMessage());
			return;
		}

		char answer = 'y';

		while (Character.toLowerCase(answer) == 'y') {
			try {
				String clusterSet = learningFromDBTable();
				System.out.println(clusterSet);

				storeClusterInFile();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
			} catch (ServerException e) {
				System.err.println(e.getMessage());
			}

			System.out.print("Would you repeat? (y/n): ");
			answer = Keyboard.readChar();
		}
	}

	/**
	 * Store the table from database.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws SocketException Thrown when a socket error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 * @throws ServerException Thrown when the server result is invalid
	 */
	private void storeTableFromDB()
		throws IOException,
		       SocketException,
		       ClassNotFoundException,
		       ServerException {
		outStream.writeObject(0);

		System.out.print("Table name: ");
		String tableName = Keyboard.readString();
		outStream.writeObject(tableName);

		String result = (String) inStream.readObject();

		if (!result.equals("OK")) {
			throw new ServerException(result);
		}
	}

	/**
	 * Learning from the database table.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 * @throws ServerException Thrown when the server result is invalid
	 * @return The cluster set string
	 */
	private String learningFromDBTable()
		throws IOException,
		       ClassNotFoundException,
		       ServerException {
		outStream.writeObject(1);

		double radius = 1.0;

		do {
			System.out.print("Enter radius: ");
			radius = Keyboard.readDouble();
		} while (radius < 1e-12);

		outStream.writeObject(radius);

		String result = (String) inStream.readObject();

		if (!result.equals("OK")) {
			throw new ServerException(result);
		}

		System.out.println("Number of clusters: " + inStream.readObject());

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
	 * Retrieve the server file.
	 * @throws IOException Thrown when an I/O error occurs
	 * @throws ClassNotFoundException Thrown whena a class is not found
	 * @throws ServerException Thrown when the server result is invalid
	 * @return The content of the server file
	 */
	private String learningFromServerFile()
		throws IOException,
		       ClassNotFoundException,
		       ServerException {
		outStream.writeObject(3);

		System.out.print("Table name: ");
		String tableName = Keyboard.readString();
		outStream.writeObject(tableName);

		double radius = 1.0;

		do {
			System.out.print("Enter radius: ");
			radius = Keyboard.readDouble();
		} while (radius < 1e-12);

		outStream.writeObject(radius);

		String result = (String) inStream.readObject();

		if (!result.equals("OK")) {
			throw new ServerException(result);
		}

		return (String) inStream.readObject();
	}
}

