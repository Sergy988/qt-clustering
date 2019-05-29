
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;

import java.net.Socket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 * The multithread server main class.
 */
public class MultiServer {

	/**
	 * The server's connection port.
	 */
	private static final int PORT = 8080;

	/**
	 * The server socket.
	 */
	private ServerSocket socket;

	/**
	 * The server entry point.
	 * @param args The arguments of the program
	 */
	public static void main(String[] args) {
		MultiServer server = null;

		try {
			server = new MultiServer(PORT);
		} catch (IOException e) {
			System.err.println(e);
			return;
		}

		server.run();
	}

	/**
	 * Construct a multithread server.
	 * @param port The connection port
	 * @throws IOException Thrown when the server socket constructor fails
	 */
	public MultiServer(int port) throws IOException {
		socket = new ServerSocket(port);
	}

	/**
	 * Run the server.
	 */
	public void run() {
		while (true) {
			try {
				Socket clientSocket = socket.accept();
				new ServerOneClient(clientSocket).start();
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
}
