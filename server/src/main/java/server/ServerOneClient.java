
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
	}

	/**
	 * Run the thread.
	 */
	@Override
	public void run() {
		// TODO
	}
}

