
/**
 * The multithread server main class.
 */
public class MultiServer {

	/**
	 * The server's connection port.
	 */
	private static final int PORT = 8080;

	/**
	 * The server entry point.
	 * @param args The arguments of the program
	 */
	public static void main(String[] args) {
		MultiServer server = new MultiServer(PORT);

		server.run();
	}

	/**
	 * Construct a multithread server.
	 * @param port The connection port
	 */
	public MultiServer(int port) {
		// TODO
	}

	/**
	 * Run the server.
	 */
	public void run() {
		// TODO
	}
}

