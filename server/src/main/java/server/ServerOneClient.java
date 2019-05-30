
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;

import java.net.Socket;
import java.net.InetAddress;
import java.net.SocketException;

import mining.QTMiner;

/**
 * Server one client thread class.
 */
public class ServerOneClient extends Thread {

	/**
	 * The QT miner object.
	 */
	private QTMiner qt;

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
			Integer operation = (Integer) inStream.readObject();

			switch (operation) {
				case 0:
					// storeTableFromDB()
					break;
				case 1:
					// learningFromDBTable()
					break;
				case 2:
					// storeClusterInFile()
					break;
				case 3:
					// learningFromServerFile()
					break;
				default:
					break;
			}
		} catch (IOException e) {
			System.err.println(e);
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
			System.err.println(e);
		}
	}
}

