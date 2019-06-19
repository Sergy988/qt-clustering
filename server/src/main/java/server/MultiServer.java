
package server;

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
		try {
			new MultiServer(PORT).run();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
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
		try {
			while (true) {
				Socket clientSocket = socket.accept();
				System.out.println("accepting " + clientSocket);

				try {
					new ServerOneClient(clientSocket);
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			close();
		}
	}

	/**
	 * Close the server.
	 */
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}

